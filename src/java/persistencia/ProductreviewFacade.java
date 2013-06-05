/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Cliente;
import modelo.Productreview;
import modelo.ProductreviewPK;

/**
 *
 * @author Jesus
 */
@Stateless
public class ProductreviewFacade extends AbstractFacade<Productreview> {
    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;

    @EJB
    private ClienteFacade clienteFacade;
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductreviewFacade() {
        super(Productreview.class);
    }

    public boolean haCalificado(int idCliente,int idProducto){
        comentariosPorProducto(idProducto);
        if (!buscarPorPK(idCliente,idProducto).isEmpty())
            
            return true;
        else
            return false;
    }
    
    public List<Productreview> buscarPorPK(int idCliente,int idProducto){
        Query q =em.createNamedQuery("Productreview.buscarPorPkFk")
                .setParameter("pkFkCliente", idCliente)
                .setParameter("pkFkProducto", idProducto);
        List<Productreview> productReviewList = new ArrayList<Productreview>();
        
        try {
            List<Integer> idList = (List<Integer>) q.getResultList();

            if (!idList.isEmpty()) {
                Iterator i = idList.iterator();
                Productreview pr;
                int id;
                while (i.hasNext()) {
                    id = Integer.parseInt(i.next().toString());
                    System.out.println(id);
                    pr = find(new ProductreviewPK(id,idCliente,idProducto));
                    System.out.println(pr);
                    productReviewList.add(pr);
                }
                
            }
         return productReviewList;
        } catch (NoResultException e) {
            return productReviewList;
        }
    }
    public List<Productreview> buscarPorPKFecha(int idCliente,int idProducto){
        Query q =em.createNativeQuery("select pr.id_productreview "
                + "from productreview pr "
                + "where pr.pk_fk_producto="+idProducto
                + " and pr.pk_fk_cliente="+idCliente
                + " order by pr.fecha_hora desc");
        List<Productreview> productReviewList = new ArrayList<Productreview>();
        
        try {
            List<Integer> idList = (List<Integer>) q.getResultList();

            if (!idList.isEmpty()) {
                Iterator i = idList.iterator();
                Productreview pr;
                int id;
                while (i.hasNext()) {
                    id = Integer.parseInt(i.next().toString());
//                    System.out.println(id);
                    pr = find(new ProductreviewPK(id,idCliente,idProducto));
//                    System.out.println(pr);
                    productReviewList.add(pr);
                }
                
            }
         return productReviewList;
        } catch (NoResultException e) {
            return productReviewList;
        }
    }
    private List<Productreview> comentariosPorProducto(int idProducto){
        Query q =em.createNamedQuery("Productreview.buscarPorPkFkProducto")
                .setParameter("pkFkProducto", idProducto);
        List<Productreview> productReviewList = new ArrayList<Productreview>();
        try{
            productReviewList=(List<Productreview>)q.getResultList();
            return productReviewList;
        } catch (NoResultException e) {
            return productReviewList;
        }
    }
    public List<Cliente> comentariosPorCliente(int idProducto){
         List<Productreview> productReviewList=this.comentariosPorProducto(idProducto);
         List<Cliente> clientes=new ArrayList<Cliente>();
         if(!productReviewList.isEmpty()){
             Query q=em.createNativeQuery("select pr.pk_fk_cliente from productreview pr where pr.pk_fk_producto="+idProducto+" " +
            "group by pr.pk_fk_cliente");   
                List<Integer> idList = (List<Integer>) q.getResultList();

            if (!idList.isEmpty()) {
                Iterator i = idList.iterator();
                Cliente c;
                int id;
                while (i.hasNext()) {
                    id = Integer.parseInt(i.next().toString());
                    System.out.println(id);
                    c = clienteFacade.find(id);
                    c.setProductreviewList(null);
                    c.setProductreviewList(buscarPorPKFecha(id, idProducto));
                    clientes.add(c);
                }
                
            }
         }
         return clientes;
    }
}
