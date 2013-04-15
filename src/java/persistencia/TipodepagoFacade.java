/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.Tipodepago;

/**
 *
 * @author oswaldomaestra
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
    
}
