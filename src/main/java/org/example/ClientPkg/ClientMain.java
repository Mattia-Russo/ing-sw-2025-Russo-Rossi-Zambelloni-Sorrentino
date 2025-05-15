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
        if(!(args[2].equalsIgnoreCase("gui") || args[2].equalsIgnoreCase("tui"))){
            System.out.println("Error: connection type not supported. Use 'gui' or 'tui' as third parameter.");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "tcp":
                new TCPClient(Settings.SERVER_NAME, Settings.TCP_PORT, args[1], args[2]);
                break;

            case "rmi":
                new RMIClient("localhost", args[1], args[2]);
                break;

            default:
                System.out.println("Error: connection type not supported. Use 'tcp' or 'rmi'.");
        }
    }
}