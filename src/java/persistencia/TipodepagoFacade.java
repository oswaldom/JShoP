/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import controlador.ControladorCorreo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.Cliente;
import modelo.Tipodepago;

/**
 *
 * @author Jesus
 */
@Stateless
public class TipodepagoFacade extends AbstractFacade<Tipodepago> {
    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipodepagoFacade() {
        super(Tipodepago.class);
    }
    
    public int registrarTipoDePago(long numeroTdc, String fechaVencimiento, String marcacomercial, Cliente cliente) {
        try {
            int idTipodepago=-1;
            SimpleDateFormat f = new SimpleDateFormat("MM/yyyy");
            Tipodepago nuevoTipoDePago= new Tipodepago();
            nuevoTipoDePago.setIdTipodepago(sequenceVal());
            nuevoTipoDePago.setFechaVencimiento(f.parse(fechaVencimiento));
            nuevoTipoDePago.setMarcacomercial(marcacomercial);
            nuevoTipoDePago.setNumeroTdc(numeroTdc);
            nuevoTipoDePago.setFkCliente(cliente);
            create(nuevoTipoDePago);
            
            try {
            ControladorCorreo mail =
                    new ControladorCorreo("smtp.gmail.com", "true", "587", "true", "jshop.ecommerce@gmail.com", "jshop1234");

            mail.enviarInfoModificaciones(cliente.getCorreoElectronico(), "1");
            return nuevoTipoDePago.getIdTipodepago();
        } catch (Exception e) {
            e.printStackTrace();
        }
            return nuevoTipoDePago.getIdTipodepago();
        } catch (ParseException ex) {
            Logger.getLogger(TipodepagoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        
        
    }
    
}
