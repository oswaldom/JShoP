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
import modelo.Producto;

/**
 *
 * @author Jesus
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> {
    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }
    
    public List<Producto> buscarProducto(String q, int categoria){
        Query qu = 
                getEntityManager()
                .createNativeQuery("select p.* from producto p, categoria c, producto_categoria pc "
                + "where p.id_producto=pc.pk_fk_producto and c.id_categoria=pc.pk_fk_categoria and c.id_categoria="+categoria
                + " and (p.descripcion_producto like '"+q+"%' or p.descripcion_producto like '%"+q+"%' or p.descripcion_producto like '%"+q+"%'"
                + "or p.nombre_producto like '"+q+"%' or p.nombre_producto like '%"+q+"%' or p.nombre_producto like '%"+q+"%')");
        List<Producto> productos=null;
        try {
            productos=(List<Producto>)qu.getResultList();
            System.out.println(productos.get(0).getNombreProducto());
        return productos;
        } catch (NoResultException e) {
            return productos;
        }
//     public List<ProductoRest> productoRest(List<Producto> productos){
//         for ()
//     }
    }
}
