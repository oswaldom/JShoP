/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.ProductoCategoria;

/**
 *
 * @author Jesus
 */
@Stateless
public class ProductoCategoriaFacade extends AbstractFacade<ProductoCategoria> {
    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoCategoriaFacade() {
        super(ProductoCategoria.class);
    }
    
}
