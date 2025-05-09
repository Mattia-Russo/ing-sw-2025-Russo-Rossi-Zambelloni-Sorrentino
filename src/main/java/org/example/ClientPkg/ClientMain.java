// cd target/classes
// java org.example.ClientPkg.ClientMain tcp name

package org.example.ClientPkg;

import org.example.ServerPkg.ConnectionsPkg.Settings;
import org.example.UIPkg.TUI;
import org.example.UIPkg.UI;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        // Verifica che siano stati passati argomenti
        if (args.length < 2) {
            System.out.println("Error: specify 'tcp' or 'rmi' as first parameter and player name as second parameter.");
            return;
        }

        // Recupero del tipo di connessione desiderata
        String connectionType = args[0].toLowerCase(); // "tcp" o "rmi"

        switch (connectionType) {
            case "tcp":
                UI TCPUserInterface = new TUI();
                TCPClient TCPClient = new TCPClient(Settings.SERVER_NAME, Settings.TCP_PORT, TCPUserInterface);
                System.out.println("Setting player name");
                TCPClient.registerName(args[1]);
                break;

            case "rmi":
                RMIClient RMIClient = new RMIClient("localhost", args[1]);
                break;

            default:
                System.out.println("Error: connection type not supported. Use 'tcp' or 'rmi'.");
        }
    }
}