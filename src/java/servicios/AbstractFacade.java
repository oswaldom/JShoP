/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import modelo.Producto;

/**
 *
 * @author vit
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll(int pagina) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        int resultadoPorPagina = 10;
           //Paginación resultados por pagina 
               q.setFirstResult((pagina*resultadoPorPagina)-resultadoPorPagina);
               q.setMaxResults(resultadoPorPagina); 
        
        return q.getResultList();
    }
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        float numeroRegistros = ((Long) q.getSingleResult()).floatValue();
        int numeroPaginas = 0; 
        //System.out.println("numero de paginas " + numeroPaginas);
        if (numeroRegistros%10!=0){
            numeroPaginas = (((Long) q.getSingleResult()).intValue()/10)+1;
        }
        else {
            numeroPaginas = ((Long) q.getSingleResult()).intValue()/10;
        }
        return numeroPaginas;
    }
    
}
