/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

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
    
}
