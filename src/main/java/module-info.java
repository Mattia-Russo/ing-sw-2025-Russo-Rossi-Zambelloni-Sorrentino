module Galaxy.Trucker {

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    requires java.rmi;
    exports org.example.ClientPkg to java.rmi;
    exports org.example.ServerPkg.ConnectionsPkg.RMIPkg to java.rmi;


    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.media;

    exports org.example.UIPkg;

}