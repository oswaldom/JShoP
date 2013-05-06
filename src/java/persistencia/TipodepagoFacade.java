/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import auxiliar.FormatoFecha;
import controlador.ControladorCorreo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
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
       
            Tipodepago nuevoTipoDePago= new Tipodepago();
            nuevoTipoDePago.setIdTipodepago(sequenceVal());
            nuevoTipoDePago.setFechaVencimiento(FormatoFecha.stringDate("MM/yyyy", fechaVencimiento));
            nuevoTipoDePago.setMarcacomercial(marcacomercial);
            nuevoTipoDePago.setNumeroTdc(numeroTdc);
            nuevoTipoDePago.setFkCliente(cliente);
            create(nuevoTipoDePago);
            String mensaje="<p> Saludos, hemos registrado un nuevo método de pago.</p>"
                    + "<p> Ahora, puede proceder a realizar compras. Gracias por comprar en JShoP e-Commerce</p>";
            try {
                ControladorCorreo.getInstance().enviarInfoModificaciones(cliente.getCorreoElectronico(), mensaje);
            } catch (MessagingException ex) {
                Logger.getLogger(TipodepagoFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return nuevoTipoDePago.getIdTipodepago();
        
    }
}
