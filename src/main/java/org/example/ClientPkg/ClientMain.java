// cd target/classes
// java org.example.ClientPkg.ClientMain tcp name

package org.example.ClientPkg;

import org.example.ServerPkg.ConnectionsPkg.Settings;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        // Verifica che siano stati passati argomenti
        if (args.length < 4) {
            System.out.println("Error: specify 'tcp' or 'rmi' as first parameter, 'gui' or 'tui' as second parameter and Ip address and port as third and fourth parameter.");
            return;
        }
        Settings.SERVER_NAME = args[2];

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
                Settings.TCP_PORT = Integer.parseInt(args[3]);
                new TCPClient(Settings.SERVER_NAME, Settings.TCP_PORT, userInterface);
                break;

            case "rmi":
                Settings.RMI_PORT = Integer.parseInt(args[3]);
                new RMIClient(Settings.SERVER_NAME, userInterface);
                break;

            default:
                System.out.println("Error: connection type not supported. Use 'tcp' or 'rmi'.");
        }
    }
}