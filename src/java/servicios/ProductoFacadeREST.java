/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import auxiliar.DireccionServidor;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import modelo.Producto;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
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
import javax.ws.rs.core.Response;

/**
 *
 * @author vit
 */
@Stateless
@Path("producto")
public class ProductoFacadeREST extends AbstractFacade<Producto> {
    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;

    @EJB
    private ProductoFacadeREST productoFacade;
    public ProductoFacadeREST() {
        super(Producto.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Producto entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Producto entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
/*
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Producto find(@PathParam("id") Integer id) {
        return super.find(id);
    }*/

    @GET
    @Path("{pagina}")
    @Produces({"application/xml", "application/json"})
    public List<Producto> findAll(@PathParam("pagina") Integer pagina) {
        
        return super.findAll(pagina);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Producto> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
   
    @GET
    @Path("/nombre/{nombreProducto}/{pagina}")
    @Produces({"application/xml", "application/json"})
    public List<Producto> findByName( @PathParam("nombreProducto") String nombreProducto, @PathParam("pagina") Integer pagina) {
            Query q = em.createNativeQuery(
                    "select p.id_producto from producto p "
                    + "where (p.nombre_producto like '%" + nombreProducto + "%') "
                    + " order by p.nombre_producto");
            int resultadoPorPagina = 10;
            q.setFirstResult((pagina*resultadoPorPagina)-resultadoPorPagina);
            q.setMaxResults(resultadoPorPagina); 
            List<Integer> idPList = (List<Integer>) q.getResultList();
            List<Producto> pl = new ArrayList<Producto>();
                for (int i = 0; i < idPList.size(); i++) {
                    pl.add(productoFacade.find(idPList.get(i)));
                }
            
           //Paginación resultados por pagina 
            
       return pl;
    }
    
    @GET
    @Path("/id/{idProducto}/imagen")
    @Produces({"image/jpeg"})
    public Response obtenerImagen( @PathParam("idProducto") int idProducto) {
            Query q = em.createNamedQuery("Producto.buscarPorIdProducto")
                  .setParameter("idProducto", idProducto);
            Producto p=(Producto)q.getSingleResult();
            String path=DireccionServidor.getInstance().getUrlServidor();
            path=path+"/JShoP/imagenProducto?id=" + p.getIdProducto();
                URL pathUrl = null;
                try {
                    pathUrl = new URL(path);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ProductoFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
                }
                BufferedImage image = null;
        try {
            image = ImageIO.read(pathUrl);
        } catch (IOException ex) {
            Logger.getLogger(ProductoFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", baos);
        } catch (IOException ex) {
            Logger.getLogger(ProductoFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
                byte[] imgBytes=baos.toByteArray();
                 return  Response.ok(new ByteArrayInputStream(imgBytes)).build();
            
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
