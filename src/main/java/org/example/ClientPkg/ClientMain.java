// cd target/classes
// java org.example.ClientPkg.ClientMain tcp name

package org.example.ClientPkg;

import org.example.ServerPkg.ConnectionsPkg.Settings;
import org.example.UIPkg.GUI;
import org.example.UIPkg.TUI;
import org.example.UIPkg.UI;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        // Verifica che siano stati passati argomenti
        if (args.length < 3) {
            System.out.println("Error: specify 'tcp' or 'rmi' as first parameter and 'gui' or 'tui' as second parameter.");
            return;
        }
        // CONTROLLO SE GUI E TUI
        UI userInterface = null;
        switch (args[2].toLowerCase()){
            case "tui":
                userInterface = new TUI();
                break;
            case "gui":
                userInterface = new GUI();
                GUI.main(args);
                break;
            default:
                System.out.println("Error: connection type not supported. Use 'gui' or 'tui' as third parameter.");
                return;
        }

        switch (args[0].toLowerCase()) {
            case "tcp":
                new TCPClient(Settings.SERVER_NAME, Settings.TCP_PORT, args[1], userInterface);
                break;

            case "rmi":
                new RMIClient("localhost", args[1], userInterface);
                break;

            default:
                System.out.println("Error: connection type not supported. Use 'tcp' or 'rmi'.");
        }
    }
}