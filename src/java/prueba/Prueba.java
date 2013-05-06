/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesus
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(auxiliar.DireccionServidor.getInstance().getUrlServidorAdmin());
        auxiliar.DireccionServidor.getInstance().setPuertoServidor(":8081");
            System.out.println(auxiliar.DireccionServidor.getInstance().getUrlServidor());
    }
}
