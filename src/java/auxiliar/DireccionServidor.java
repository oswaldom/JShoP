/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesus
 */
public class DireccionServidor {

    private DireccionServidor() {
        String ip;
        try {
            ip = java.net.Inet4Address.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException ex) {
            ip = "localhost";
            Logger.getLogger(DireccionServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.urlServidor = "http://" + ip;
        this.urlServidorAdmin = "https://" + ip + ":8181/JShoP/admin";
    }
    private String urlServidorAdmin;
    private String urlServidor;
    private String puertoServidor = "";
    private static DireccionServidor instancia = null;

    public static DireccionServidor getInstance() {
        createInstance();
        return instancia;
    }

    private static void createInstance() {
        synchronized (DireccionServidor.class) {
            if (instancia == null) {
                instancia = new DireccionServidor();
            }
        }
    }

    public String getUrlServidorAdmin() {
        return urlServidorAdmin;
    }

    public String getUrlServidor() {
        return urlServidor+puertoServidor;
    }
    public void setPuertoServidor(String url){
        if (puertoServidor.equals("")) {
            if (url.contains(":8080")) {
                puertoServidor = ":8080";
            } else {
                puertoServidor = ":8081";
            }
        }
    }
    public String getPuertoServidor() {
        return puertoServidor;
    }
}
