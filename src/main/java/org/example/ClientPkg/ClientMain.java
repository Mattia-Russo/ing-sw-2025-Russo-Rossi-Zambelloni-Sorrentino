// cd target/classes
// java org.example.ClientPkg.ClientMain tcp name

package org.example.ClientPkg;

import org.example.ServerPkg.ConnectionsPkg.Settings;
import org.example.UIPkg.GUIPkg.GUI;
import org.example.UIPkg.GUIPkg.GUIMain;
import org.example.UIPkg.TUI;
import org.example.UIPkg.UI;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        // Verifica che siano stati passati argomenti
        if (args.length < 2) {
            System.out.println("Error: specify 'tcp' or 'rmi' as first parameter and 'gui' or 'tui' as second parameter.");
            return;
        }
        // CONTROLLO SE GUI E TUI
        String userInterface = null;
        switch (args[1].toLowerCase()){
            case "tui":
                userInterface = "tui";
                break;
            case "gui":
                userInterface = "gui";
                break;
            default:
                System.out.println("Error: connection type not supported. Use 'gui' or 'tui' as third parameter.");
                return;
        }

        switch (args[0].toLowerCase()) {
            case "tcp":
                new TCPClient(Settings.SERVER_NAME, Settings.TCP_PORT, userInterface);
                break;

            case "rmi":
                new RMIClient(Settings.SERVER_NAME, userInterface);
                break;

            default:
                System.out.println("Error: connection type not supported. Use 'tcp' or 'rmi'.");
        }
    }
}