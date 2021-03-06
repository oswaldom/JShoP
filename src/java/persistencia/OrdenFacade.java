/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
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
    
    // in this implementation, there is only one order per customer
    // the data model however allows for multiple orders per customer
    @RolesAllowed("jshopAdmin")
    public Orden findByCustomer(Object cliente) {
        return (Orden) em.createNamedQuery("Orden.findByCustomer").setParameter("cliente", cliente).getSingleResult();
    }
}
