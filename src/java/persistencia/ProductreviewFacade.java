/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.Productreview;

/**
 *
 * @author oswaldomaestra
 */
@Stateless
public class ProductreviewFacade extends AbstractFacade<Productreview> {
    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductreviewFacade() {
        super(Productreview.class);
    }
    
}
