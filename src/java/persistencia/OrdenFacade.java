/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import auxiliar.DireccionServidor;
import controlador.ControladorCorreo;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public List<Orden> buscarPorCliente(Object cliente) {
        Query q = em.createNamedQuery("Orden.buscarPorCliente").setParameter("fkCliente", cliente);
        List<Orden> ordenes = null;

        try {
            ordenes = (List<Orden>) q.getResultList();
            return ordenes;
        } catch (NoResultException e) {
            return ordenes;
        }
    }

    public boolean ordenEntregada(Orden orden) {
        if (orden.getStatusOrden().equals("Entregada"))
            return true;
        else
            return false;
    }
    public boolean algunaOrdenEntregada(List<Orden> ordenes){
          Iterator i = ordenes.iterator();
                Orden o;
                while (i.hasNext()) {
                    o=(Orden)i.next();
                    if (ordenEntregada(o)){
                        return true;
                    }
                }
        return false;
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
