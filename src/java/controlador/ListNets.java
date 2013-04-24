package controlador;

import java.io.*;
import java.net.*;
import java.util.*;
import static java.lang.System.out;

public class ListNets {

    public static String getCurrentEnvironmentNetworkIp() {
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
        }

        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                
                InetAddress addr = address.nextElement();
//                ut.printf("InetAddress: %s\n", addr.getHostAddress());
//                out.printf("isLoopbackAddress: %s\n", addr.isLoopbackAddress());
//                out.printf("isSiteLocalAddress: %s\n", addr.isSiteLocalAddress());
//                out.printf("getHostAddress().indexOf(\":\"): %s\n", addr.getHostAddress().indexOf(":"));
//                out.printf("InetAddress: %s\n", addr.getHostAddress());
//                out.printf("isLoopbackAddress: %s\n", addr.isLoopbackAddress());
//                out.printf("isSiteLocalAddress: %s\n", addr.isSiteLocalAddress());
//                out.printf("getHostAddress().indexOf(\":\"): %s\n", addr.getHostAddress().indexOf(":"));
                if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()
                        && (addr.getHostAddress().indexOf(":") == -1)) {
                    return addr.getHostAddress();
                }
            }
        }
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }
}  