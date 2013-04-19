/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Lugar;

/**
 *
 * @author Jesus
 */
@Stateless
public class LugarFacade extends AbstractFacade<Lugar> {

    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LugarFacade() {
        super(Lugar.class);
    }

    public List<Lugar> listarPaises() {
        Query q = em.createNamedQuery("Lugar.buscarPorTipoLugar")
                .setParameter("tipoLugar", "Pais");
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
