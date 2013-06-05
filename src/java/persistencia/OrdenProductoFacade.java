/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.OrdenProducto;

/**
 *
 * @author Jesus
 */
@Stateless
public class OrdenProductoFacade extends AbstractFacade<OrdenProducto> {
    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdenProductoFacade() {
        super(OrdenProducto.class);
    }
    
    public List<OrdenProducto> buscarPorFkOrden(Object id) {
        return em.createNamedQuery("OrdenProducto.buscarPorPkFkOrden").setParameter("pkFkOrden", id).getResultList();
    }
    
     public List<OrdenProducto> buscarPorFkProducto(int id) {
        return em.createNamedQuery("OrdenProducto.buscarPorPkFkProducto").setParameter("pkFkProducto", id).getResultList();
    }
    
}
