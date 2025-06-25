package org.example.ServerPkg.Model.ForView;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe per gestire il caching locale della GameView sul client
 * e confrontare le differenze nelle shipboard dei giocatori e nei componenti scoperti
 */
public class GameViewCache {

    private GameView cachedGameView;
    private String currentPlayerName;

    /**
     * Risultato del confronto tra GameView
     */
    public static class GameViewDifferences {
        private final List<ComponentsView> newShipboardComponents;
        private final List<ComponentsView> newDiscoveredComponents;
        private final ComponentsView newCurrentTile;
        private final boolean currentTileChanged;

        public GameViewDifferences(List<ComponentsView> newShipboardComponents,
                                   List<ComponentsView> newDiscoveredComponents,
                                   ComponentsView newCurrentTile,
                                   boolean currentTileChanged) {
            this.newShipboardComponents = newShipboardComponents != null ? newShipboardComponents : new ArrayList<>();
            this.newDiscoveredComponents = newDiscoveredComponents != null ? newDiscoveredComponents : new ArrayList<>();
            this.newCurrentTile = newCurrentTile;
            this.currentTileChanged = currentTileChanged;
        }

        public List<ComponentsView> getNewShipboardComponents() {
            return newShipboardComponents;
        }

        public List<ComponentsView> getNewDiscoveredComponents() {
            return newDiscoveredComponents;
        }

        public ComponentsView getNewCurrentTile() {
            return newCurrentTile;
        }

        public boolean isCurrentTileChanged() {
            return currentTileChanged;
        }

        public boolean hasChanges() {
            return !newShipboardComponents.isEmpty() || !newDiscoveredComponents.isEmpty() || currentTileChanged;
        }
    }

    public GameViewCache(String playerName) {
        this.currentPlayerName = playerName;
        this.cachedGameView = null; // Inizialmente null per rilevare la prima GameView
    }

    public GameViewDifferences compareAndUpdate(GameView newGameView) {
        List<ComponentsView> newShipboardComponents = new ArrayList<>();
        List<ComponentsView> newDiscoveredComponents = new ArrayList<>();
        ComponentsView newCurrentTile = null;
        boolean currentTileChanged = false;

        // 1. Confronta la shipboard del giocatore corrente
        PlayerView currentPlayer = findPlayerByName(newGameView, currentPlayerName);
        if (currentPlayer != null) {
            if (cachedGameView == null) {
                // Prima GameView: considera tutti i componenti della shipboard come nuovi
                newShipboardComponents.addAll(getShipboardComponents(currentPlayer.getShipboardView()));
                // Considera anche il currentTile come cambiato se presente
                if (currentPlayer.getCurrentTile() != null) {
                    newCurrentTile = currentPlayer.getCurrentTile();
                    currentTileChanged = true;
                }
            } else {
                // Confronta con la shipboard cached
                PlayerView cachedPlayer = findPlayerByName(cachedGameView, currentPlayerName);
                if (cachedPlayer != null) {
                    List<ComponentsView> cachedShipboardComponents = getShipboardComponents(cachedPlayer.getShipboardView());
                    List<ComponentsView> currentShipboardComponents = getShipboardComponents(currentPlayer.getShipboardView());
                    newShipboardComponents = findComponentDifferences(cachedShipboardComponents, currentShipboardComponents);

                    // Confronta il currentTile
                    ComponentsView cachedCurrentTile = cachedPlayer.getCurrentTile();
                    ComponentsView currentCurrentTile = currentPlayer.getCurrentTile();

                    if (!currentTilesEqual(cachedCurrentTile, currentCurrentTile)) {
                        newCurrentTile = currentCurrentTile;
                        currentTileChanged = true;
                    }
                } else {
                    // Il giocatore non era presente nella cache
                    newShipboardComponents.addAll(getShipboardComponents(currentPlayer.getShipboardView()));
                    if (currentPlayer.getCurrentTile() != null) {
                        newCurrentTile = currentPlayer.getCurrentTile();
                        currentTileChanged = true;
                    }
                }
            }
        }

        // 2. Confronta i componenti scoperti
        if (cachedGameView == null) {
            // Prima GameView: considera tutti i componenti scoperti come nuovi
            newDiscoveredComponents.addAll(newGameView.getComponentsDiscovered());
        } else {
            // Confronta con i componenti scoperti cached
            List<ComponentsView> cachedDiscovered = cachedGameView.getComponentsDiscovered();
            List<ComponentsView> currentDiscovered = newGameView.getComponentsDiscovered();

            // Se le liste hanno dimensioni diverse, significa che sono cambiati
            if (cachedDiscovered.size() != currentDiscovered.size()) {
                newDiscoveredComponents.addAll(currentDiscovered);
            } else {
                // Se hanno la stessa dimensione, controlla le differenze normalmente
                newDiscoveredComponents = findComponentDifferences(cachedDiscovered, currentDiscovered);
            }
        }

        // Aggiorna la cache con la nuova GameView
        if(newGameView.getException()==null){
            cachedGameView = newGameView;
        }

        return new GameViewDifferences(newShipboardComponents, newDiscoveredComponents, newCurrentTile, currentTileChanged);
    }

    private PlayerView findPlayerByName(GameView gameView, String playerName) {
        for (PlayerView player : gameView.getPlayers()) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Estrae tutti i componenti dalla ShipboardView
     */
    private List<ComponentsView> getShipboardComponents(ShipboardView shipboardView) {
        List<ComponentsView> components = new ArrayList<>();

        if (shipboardView == null) {
            return components;
        }

        // Estrae i componenti dalla matrice 5x7
        ComponentsView[][] componentMatrix = shipboardView.getComponentsView();
        if (componentMatrix != null) {
            for (int i = 0; i < componentMatrix.length; i++) {
                for (int j = 0; j < componentMatrix[i].length; j++) {
                    if (componentMatrix[i][j] != null) {
                        components.add(componentMatrix[i][j]);
                    }
                }
            }
        }

        // Estrae anche i componenti prenotati (booked components)
        ComponentsView[] bookedComponents = shipboardView.getBookedComponents();
        if (bookedComponents != null) {
            for (ComponentsView bookedComponent : bookedComponents) {
                if (bookedComponent != null) {
                    components.add(bookedComponent);
                }
            }
        }

        return components;
    }

    /**
     * Trova le differenze tra due liste di componenti
     */
    private List<ComponentsView> findComponentDifferences(List<ComponentsView> oldComponents, List<ComponentsView> newComponents) {
        List<ComponentsView> differences = new ArrayList<>();

        for (ComponentsView newComponent : newComponents) {
            boolean found = false;
            for (ComponentsView oldComponent : oldComponents) {
                if (componentsEqual(oldComponent, newComponent)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                differences.add(newComponent);
            }
        }

        return differences;
    }

    /**
     * Confronta due componenti per vedere se sono uguali
     */
    private boolean componentsEqual(ComponentsView comp1, ComponentsView comp2) {
        return comp1.getId() == comp2.getId() &&
                comp1.getDirection() == comp2.getDirection();
    }

    /**
     * Confronta due currentTile per vedere se sono uguali
     */
    private boolean currentTilesEqual(ComponentsView tile1, ComponentsView tile2) {
        if (tile1 == null && tile2 == null) {
            return true;
        }
        if (tile1 == null || tile2 == null) {
            return false;
        }
        return componentsEqual(tile1, tile2);
    }

    /**
     * Restituisce la GameView attualmente cached
     */
    public GameView getCachedGameView() {
        return cachedGameView;
    }

    /**
     * Controlla se esiste una GameView cached
     */
    public boolean hasCachedGameView() {
        return cachedGameView != null;
    }

    /**
     * Resetta la cache (utile per nuove partite)
     */
    public void resetCache() {
        cachedGameView = null;
    }

    /**
     * Aggiorna il nome del giocatore corrente
     */
    public void setCurrentPlayerName(String playerName) {
        this.currentPlayerName = playerName;
    }

    // Aggiungi questo metodo nella classe GameViewCache se non è già presente
    public String getCurrentPlayerName() {
        return currentPlayerName;
    }
}