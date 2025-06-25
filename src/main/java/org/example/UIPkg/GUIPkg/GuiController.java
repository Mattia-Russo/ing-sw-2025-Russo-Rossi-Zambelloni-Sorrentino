package org.example.UIPkg.GUIPkg;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.example.ServerPkg.Model.ForView.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GuiController {
    private GUI guiRoot;
    
    // Mappa statica per mantenere le immagini tra le scene
    private static Map<String, List<ComponentImageInfo>> persistentShipboardImages = new HashMap<>();
    
    // Riferimenti ai container delle immagini (da impostare nelle sottoclassi)
    protected Pane shipboardContainer;
    protected ImageView shipboardImageView;

    public void setGui(GUI guiRoot){
        this.guiRoot=guiRoot;
    }

    public GUI getGuiRoot(){
        return guiRoot;
    }

    // Metodo per salvare lo stato corrente delle immagini prima di cambiare scena
    protected void saveCurrentShipboardState() {
        if (guiRoot != null && guiRoot.getGameCache() != null && 
            guiRoot.getGameCache().getCurrentPlayerName() != null && shipboardContainer != null) {
            
            String playerName = guiRoot.getGameCache().getCurrentPlayerName();
            List<ComponentImageInfo> imageInfos = new ArrayList<>();
            
            for (Node node : shipboardContainer.getChildren()) {
                if (node instanceof ImageView && node != shipboardImageView) {
                    ImageView img = (ImageView) node;
                    // Salva solo se l'immagine ha un URL valido
                    if (img.getImage() != null && img.getImage().getUrl() != null) {
                        ComponentImageInfo info = new ComponentImageInfo(
                            img.getImage().getUrl(),
                            img.getLayoutX(),
                            img.getLayoutY(),
                            img.getRotate(),
                            img.getFitWidth(),
                            img.getFitHeight()
                        );
                        imageInfos.add(info);
                    }
                }
            }
            
            persistentShipboardImages.put(playerName, imageInfos);
            System.out.println("Salvate " + imageInfos.size() + " immagini per il giocatore: " + playerName);
        }
    }

    // Metodo per ripristinare le immagini quando si entra in una nuova scena
    protected void restoreShipboardState() {
        if (guiRoot != null && guiRoot.getGameCache() != null && 
            guiRoot.getGameCache().getCurrentPlayerName() != null && shipboardContainer != null) {
            
            String playerName = guiRoot.getGameCache().getCurrentPlayerName();
            
            if (persistentShipboardImages.containsKey(playerName)) {
                List<ComponentImageInfo> imageInfos = persistentShipboardImages.get(playerName);
                
                // Pulisci le immagini esistenti (tranne il background)
                clearComponentImages();
                
                // Ricarica le immagini salvate
                for (ComponentImageInfo info : imageInfos) {
                    restoreImageOnShipboard(info);
                }
                
                System.out.println("Ripristinate " + imageInfos.size() + " immagini per il giocatore: " + playerName);
            }
        }
    }

    // Pulisce solo le immagini dei componenti, non l'immagine di background
    protected void clearComponentImages() {
        if (shipboardContainer != null) {
            List<Node> toRemove = new ArrayList<>();
            for (Node node : shipboardContainer.getChildren()) {
                if (node instanceof ImageView && node != shipboardImageView) {
                    toRemove.add(node);
                }
            }
            shipboardContainer.getChildren().removeAll(toRemove);
        }
    }

    // Ripristina una singola immagine sulla shipboard
    private void restoreImageOnShipboard(ComponentImageInfo info) {
        try {
            Image image = new Image(info.getImageUrl());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(info.getFitWidth());
            imageView.setFitHeight(info.getFitHeight());
            imageView.setPreserveRatio(true);
            imageView.setLayoutX(info.getX());
            imageView.setLayoutY(info.getY());
            imageView.setRotate(info.getRotation());
            
            shipboardContainer.getChildren().add(imageView);
        } catch (Exception e) {
            System.err.println("Errore nel ripristino dell'immagine: " + e.getMessage());
        }
    }

    // Metodo helper per trovare un giocatore per nome
    protected PlayerView findPlayerByName(GameView gameView, String playerName) {
        if (gameView == null || playerName == null) return null;
        
        for (PlayerView player : gameView.getPlayers()) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    // Metodo per pulire la cache quando necessario (cambio giocatore, nuova partita, etc.)
    public static void clearPersistentImageCache() {
        persistentShipboardImages.clear();
    }

    // Classe helper per memorizzare le informazioni delle immagini
    private static class ComponentImageInfo {
        private final String imageUrl;
        private final double x, y, rotation, fitWidth, fitHeight;
        
        public ComponentImageInfo(String imageUrl, double x, double y, double rotation, double fitWidth, double fitHeight) {
            this.imageUrl = imageUrl;
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            this.fitWidth = fitWidth;
            this.fitHeight = fitHeight;
        }
        
        public String getImageUrl() { return imageUrl; }
        public double getX() { return x; }
        public double getY() { return y; }
        public double getRotation() { return rotation; }
        public double getFitWidth() { return fitWidth; }
        public double getFitHeight() { return fitHeight; }
    }

    // Metodi astratti esistenti
    public void setMaxPlayers(int numPlayers){}
    public void setShipboardLevel(int shipboardLevel){}
    public void setGameMode(int gameMode) {}
    public void updatePlayersList(List<String> playersList){}
    public void printNameInvalid(){}
    public void onNameAccepted(){}
    public void onLobbyCreated(){}
    public void setLobbyCreator(boolean lobbyCreator){}
    public void onCreateLobbyAccepted(){}
    public void onGameStarted(){}
    public void updateGui(GameView game){}
    public void loadShipboardImage() {}
    public void loadFlightBoardImage() {}
    public void updatePlayerShipboardButtons(GameView game) {}
    public void setUp(GameView game){}

    public List<ComponentsView> getShipboardComponents(ShipboardView shipboardView) {
        List<ComponentsView> components = new ArrayList<>();

        if (shipboardView == null) {
            return components;
        }

        ComponentsView[][] componentMatrix = shipboardView.getComponentsView();
        if (componentMatrix != null) {
            for (ComponentsView[] matrix : componentMatrix) {
                for (ComponentsView componentsView : matrix) {
                    if (componentsView != null) {
                        components.add(componentsView);
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
}