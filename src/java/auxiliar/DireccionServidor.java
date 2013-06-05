/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import java.net.URL;
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
        this.puertoServidor = ":8080";
        this.urlServidor = "http://" + ip + puertoServidor;
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
        return urlServidor;
    }
    public void setPuertoServidor(){
        puertoServidor = ":8080";
    }
    public String getPuertoServidor() {
        return puertoServidor;
    }
    
    private static final String WEBINF = "WEB-INF";
 
  public String getWebInfPath(){
 
    String filePath = "";
 
    URL url = DireccionServidor.class.getResource("DireccionServidor.class");
    
    String className = url.getFile();
 
    filePath = className.substring(0,className.indexOf(WEBINF) + WEBINF.length());
 
    return filePath;
 
  }
  public String getImgPath(){
      String dir= getWebInfPath();
 dir = dir.replace("build", "");
    dir=dir+"/img/products/";
    return dir;
 
  }
}
