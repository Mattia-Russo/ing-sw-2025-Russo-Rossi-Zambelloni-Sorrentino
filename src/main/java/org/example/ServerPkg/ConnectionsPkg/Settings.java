package org.example.ServerPkg.ConnectionsPkg;

import java.net.InetAddress;

public class Settings {
    public static int TCP_PORT = 3500;
    public static int RMI_PORT = 3600;
    public static String SERVER_NAME;

    static {
        /*
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            SERVER_NAME = localHost.getHostAddress();
        } catch (java.net.UnknownHostException e) {
            SERVER_NAME = "127.0.0.1";
            System.err.println("IP address error; 127.0.0.1 used");
        }
        */
        SERVER_NAME = "192.168.1.10";
    }
}