// cd target/classes
// java org.example.ClientPkg.ClientMain tcp name

package org.example.ClientPkg;

import org.example.ServerPkg.ConnectionsPkg.Settings;
import org.example.ServerPkg.ConnectionsPkg.RMIPkg.RMIClientInterface;
import org.example.UIPkg.GUI;
import org.example.UIPkg.TUI;
import org.example.UIPkg.UI;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        // Verifica che siano stati passati argomenti
        if (args.length < 3) {
            System.out.println("Error: specify 'tcp' or 'rmi' as first parameter, player name as second parameter and 'gui' or 'tui' as third parameter.");
            return;
        }
        // CONTROLLO SE GUI E TUI
        // Recupero del tipo di connessione desiderata
        String connectionType = args[0].toLowerCase(); // "tcp" o "rmi"

        switch (connectionType) {
            case "tcp":
                UI TCPUserInterface;
                if(args[2].toLowerCase().equals("tui")) {
                    TCPUserInterface = new TUI();
                }else{
                    TCPUserInterface = new GUI();
                }
                new TCPClient(Settings.SERVER_NAME, Settings.TCP_PORT, TCPUserInterface, args[1], args[2]);
                break;

            case "rmi":
                UI RMIUserInterface;
                if(args[2].toLowerCase().equals("tui")) {
                    RMIUserInterface = new TUI();
                }else{
                    RMIUserInterface = new GUI();
                }
                new RMIClient("localhost", args[1]);
                break;

            default:
                System.out.println("Error: connection type not supported. Use 'tcp' or 'rmi'.");
        }
    }
}