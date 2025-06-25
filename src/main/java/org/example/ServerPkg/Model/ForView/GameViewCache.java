package org.example.ServerPkg.Model.ForView;
import java.util.List;
import java.util.ArrayList;


public class GameViewCache {

    private GameView cachedGameView;
    private String currentPlayerName;

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
        this.cachedGameView = null;
    }

    public GameViewDifferences compareAndUpdate(GameView newGameView) {
        List<ComponentsView> newShipboardComponents = new ArrayList<>();
        List<ComponentsView> newDiscoveredComponents = new ArrayList<>();
        ComponentsView newCurrentTile = null;
        boolean currentTileChanged = false;

        PlayerView currentPlayer = findPlayerByName(newGameView, currentPlayerName);
        if (currentPlayer != null) {
            if (cachedGameView == null) {
                newShipboardComponents.addAll(getShipboardComponents(currentPlayer.getShipboardView()));

                if (currentPlayer.getCurrentTile() != null) {
                    newCurrentTile = currentPlayer.getCurrentTile();
                    currentTileChanged = true;
                }
            } else {
                PlayerView cachedPlayer = findPlayerByName(cachedGameView, currentPlayerName);
                if (cachedPlayer != null) {
                    List<ComponentsView> cachedShipboardComponents = getShipboardComponents(cachedPlayer.getShipboardView());
                    List<ComponentsView> currentShipboardComponents = getShipboardComponents(currentPlayer.getShipboardView());
                    newShipboardComponents = findComponentDifferences(cachedShipboardComponents, currentShipboardComponents);

                    ComponentsView cachedCurrentTile = cachedPlayer.getCurrentTile();
                    ComponentsView currentCurrentTile = currentPlayer.getCurrentTile();

                    if (!currentTilesEqual(cachedCurrentTile, currentCurrentTile)) {
                        newCurrentTile = currentCurrentTile;
                        currentTileChanged = true;
                    }
                } else {
                    newShipboardComponents.addAll(getShipboardComponents(currentPlayer.getShipboardView()));
                    if (currentPlayer.getCurrentTile() != null) {
                        newCurrentTile = currentPlayer.getCurrentTile();
                        currentTileChanged = true;
                    }
                }
            }
        }

        if (cachedGameView == null) {
            newDiscoveredComponents.addAll(newGameView.getComponentsDiscovered());
        } else {
            List<ComponentsView> cachedDiscovered = cachedGameView.getComponentsDiscovered();
            List<ComponentsView> currentDiscovered = newGameView.getComponentsDiscovered();

            if (cachedDiscovered.size() != currentDiscovered.size()) {
                newDiscoveredComponents.addAll(currentDiscovered);
            } else {
                newDiscoveredComponents = findComponentDifferences(cachedDiscovered, currentDiscovered);
            }
        }

        cachedGameView = newGameView;

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

    private List<ComponentsView> getShipboardComponents(ShipboardView shipboardView) {
        List<ComponentsView> components = new ArrayList<>();

        if (shipboardView == null) {
            return components;
        }

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

    private boolean componentsEqual(ComponentsView comp1, ComponentsView comp2) {
        return comp1.getId() == comp2.getId() &&
                comp1.getDirection() == comp2.getDirection();
    }

    private boolean currentTilesEqual(ComponentsView tile1, ComponentsView tile2) {
        if (tile1 == null && tile2 == null) {
            return true;
        }
        if (tile1 == null || tile2 == null) {
            return false;
        }
        return componentsEqual(tile1, tile2);
    }

    public GameView getCachedGameView() {
        return cachedGameView;
    }

    public boolean hasCachedGameView() {
        return cachedGameView != null;
    }

    public void resetCache() {
        cachedGameView = null;
    }

    public void setCurrentPlayerName(String playerName) {
        this.currentPlayerName = playerName;
    }
}