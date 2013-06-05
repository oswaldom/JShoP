/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.ArrayList;
import modelo.Producto;
import modelo.ProductoCategoria;
import modelo.ProductoCategoriaPK;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.PathSegment;
import modelo.Categoria;
import servicios.dominio.CategoriaListREST;
import servicios.dominio.CategoriaREST;

/**
 *
 * @author vit
 */
@Stateless
@Path("productocategoria")
public class ProductoCategoriaFacadeREST extends AbstractFacade<ProductoCategoria> {
    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;
    @EJB
    private CategoriaFacadeREST categoriaFacadeREST;
    private ProductoCategoriaPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;idProductoCategoria=idProductoCategoriaValue;pkFkProducto=pkFkProductoValue;pkFkCategoria=pkFkCategoriaValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        modelo.ProductoCategoriaPK key = new modelo.ProductoCategoriaPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> idProductoCategoria = map.get("idProductoCategoria");
        if (idProductoCategoria != null && !idProductoCategoria.isEmpty()) {
            key.setIdProductoCategoria(new java.lang.Integer(idProductoCategoria.get(0)));
        }
        java.util.List<String> pkFkProducto = map.get("pkFkProducto");
        if (pkFkProducto != null && !pkFkProducto.isEmpty()) {
            key.setPkFkProducto(new java.lang.Integer(pkFkProducto.get(0)));
        }
        java.util.List<String> pkFkCategoria = map.get("pkFkCategoria");
        if (pkFkCategoria != null && !pkFkCategoria.isEmpty()) {
            key.setPkFkCategoria(new java.lang.Integer(pkFkCategoria.get(0)));
        }
        return key;
    }

    public ProductoCategoriaFacadeREST() {
        super(ProductoCategoria.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(ProductoCategoria entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(ProductoCategoria entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        modelo.ProductoCategoriaPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public ProductoCategoria find(@PathParam("id") PathSegment id) {
        modelo.ProductoCategoriaPK key = getPrimaryKey(id);
        return super.find(key);
    }
/*
    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<ProductoCategoria> findAll() {
        return super.findAll();
    }
  */  
    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ProductoCategoria> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
    @Path("{id}/producto/")
    @Produces({"application/xml", "application/json"})
    public Producto findPC(@PathParam("id") Integer id) {
         Categoria c=categoriaFacadeREST.find(id);
         System.out.println(c.getNombreCategoria());
         Query q = em.createNamedQuery("ProductoCategoria.buscarPorIdProductoCategoria")
                .setParameter("idProductoCategoria", id);
         ProductoCategoria pc=(ProductoCategoria) q.getSingleResult();
//         c.getProductoCategoriaList().get(0).getCategoria().getNombreCategoria();
         return pc.getProducto();
        
    }
    @GET
    @Path("{id}/producto/all")
    @Produces({"application/xml", "application/json"})
    public List<Producto> findAllPC(@PathParam("id") Integer id) {
         Categoria c=categoriaFacadeREST.find(id);
         System.out.println(c.getNombreCategoria());
         Query q = em.createNamedQuery("ProductoCategoria.buscarPorPkFkCategoria")
                .setParameter("pkFkCategoria", id);
         
         List<ProductoCategoria> pc=(List<ProductoCategoria>) q.getResultList();
         List<Producto> pl=new ArrayList<Producto>();
         if (pc!=null){
         for (int i=0;i< pc.size();i++){
             pl.add(pc.get(i).getProducto());
             System.out.println(pl.get(0).getNombreProducto());
         }
         }
//         c.getProductoCategoriaList().get(0).getCategoria().getNombreCategoria();
         return pl;
        
    }
    
}
