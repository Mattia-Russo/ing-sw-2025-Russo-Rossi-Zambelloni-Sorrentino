module Galaxy.Trucker {
    requires org.json;
    opens org.example.ServerPkg.Model to org.json;
    opens org.example.ServerPkg.Model.CardPkg to org.json;
    opens org.example.ServerPkg.ControllerPkg to org.json;
    opens org.example.ServerPkg.Model.ComponentsPkg to org.json;
    opens org.example.ServerPkg.ControllerPkg.PlayerStates to org.json;


    requires java.rmi;
    exports org.example.ClientPkg to java.rmi;
    exports org.example.ServerPkg.ConnectionsPkg.RMIPkg to java.rmi;

    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.media;

    exports org.example.UIPkg;
    exports org.example.MessagePkg;
    exports org.example.ServerPkg.Model.ForView;
    exports org.example.ServerPkg.ControllerPkg;
    exports org.example.ServerPkg.Model;
    exports org.example.ServerPkg.Model.CardPkg;
    exports org.example.UIPkg.GUIPkg;
}