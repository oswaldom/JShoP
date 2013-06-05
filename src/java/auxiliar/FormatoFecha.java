/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesus
 */
public class FormatoFecha {


    public static Date stringDate(String formato, String fecha) {
        Date fechaR;
        SimpleDateFormat f = new SimpleDateFormat(formato);
        try {
            fechaR = f.parse(fecha);
        } catch (ParseException ex) {
            fechaR = null;
            Logger.getLogger(FormatoFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaR;
    }
}
