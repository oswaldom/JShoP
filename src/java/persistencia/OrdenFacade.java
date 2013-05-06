/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import auxiliar.DireccionServidor;
import controlador.ControladorCorreo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.Orden;

/**
 *
 * @author Jesus
 */
@Stateless
public class OrdenFacade extends AbstractFacade<Orden> {

    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdenFacade() {
        super(Orden.class);
    }

    public Orden buscarPorCliente(Object cliente) {
        return (Orden) em.createNamedQuery("Orden.buscarPorCliente").setParameter("fkCliente", cliente).getSingleResult();
    }
    
    public void actualizarStatusOrden(Orden orden) {

        String link = DireccionServidor.getInstance().getUrlServidor() + "/JShoP/verOrden?" + orden.getIdOrden();
        String mensaje = "<p> Saludos, le informamos que su orden ha sido despachada.</p>"
                + "<p> Esperamos que disfrute su pedido, puede ver la informacion de su compra en el siguiente enlace</p>"
                + "<p> <a href=" + link + ">Ver pedido!</a></p>";
        try {
            ControladorCorreo.getInstance().enviarInfoModificaciones(orden.getFkCliente().getCorreoElectronico(), mensaje);
        } catch (MessagingException ex) {
            Logger.getLogger(OrdenFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        edit(orden);
    }
}
