module Galaxy.Trucker {
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens org.example.ServerPkg.Model to com.fasterxml.jackson.databind;
    opens org.example.ServerPkg.Model.CardPkg to com.fasterxml.jackson.databind;
    opens org.example.ServerPkg.ControllerPkg to com.fasterxml.jackson.databind;
    opens org.example.ServerPkg.Model.ComponentsPkg to com.fasterxml.jackson.databind;
    opens org.example.ServerPkg.ControllerPkg.PlayerStates to com.fasterxml.jackson.databind;


    requires java.rmi;

    exports org.example.ClientPkg to java.rmi;
    exports org.example.ServerPkg.ConnectionsPkg.RMIPkg to java.rmi;

    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.media;
    requires javafx.fxml;

    opens org.example.UIPkg.GUIPkg to javafx.fxml;
    opens org.example.FxmlPkg to javafx.fxml;

    exports org.example.UIPkg;
    exports org.example.MessagePkg;
    exports org.example.ServerPkg.Model.ForView;
    exports org.example.ServerPkg.ControllerPkg;
    exports org.example.ServerPkg.Model;
    exports org.example.ServerPkg.Model.CardPkg;
    exports org.example.UIPkg.GUIPkg;
}