/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.util.ArrayList;
import modelo.Categoria;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
import modelo.Producto;
import modelo.ProductoCategoria;
import servicios.dominio.CategoriaREST;
import servicios.dominio.CategoriaListREST;

/**
 *
 * @author vit
 */
@Stateless
@Path("categoria")
public class CategoriaFacadeREST extends AbstractFacade<Categoria> {

    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;
    @EJB
    private ProductoFacadeREST productoFacade;

    public CategoriaFacadeREST() {
        super(Categoria.class);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Categoria find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Categoria> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("todo/{pag}")
    @Produces({"application/xml", "application/json"})
    public CategoriaListREST findAll(@PathParam("pag") Integer pag) {
        List<CategoriaREST> categorias = new ArrayList<CategoriaREST>();
        List<Categoria> all = super.findAll(pag);
        if (all != null) {
            for (int i = 0; i < all.size(); i++) {
                categorias.add(new CategoriaREST(
                        all.get(i).getIdCategoria(),
                        all.get(i).getNombreCategoria(),
                        null));
            }
        }
        CategoriaListREST p = new CategoriaListREST(categorias);
        return p;
    }

    @GET
    @Path("nombre/{nombre}/{pag}")
    @Produces({"application/xml", "application/json"})
    public CategoriaListREST categoriaPorNombre(@PathParam("nombre") String nombre, @PathParam("pag") int pag) {
        Query q = em.createNativeQuery("select c.id_categoria from categoria c where c.nombre_categoria like '%" + nombre + "%';");

        List<CategoriaREST> categorias = new ArrayList<CategoriaREST>();
        int resultadoPorPagina = 10;
        //Paginación resultados por pagina 
        q.setFirstResult((pag * resultadoPorPagina) - resultadoPorPagina);
        q.setMaxResults(resultadoPorPagina);
        List<Integer> idList = (List<Integer>) q.getResultList();
        for (int j = 0; j < idList.size(); j++) {
            System.out.println(idList.get(j));
            Categoria c = super.find(idList.get(j));

            CategoriaREST cr = new CategoriaREST(c.getIdCategoria(), c.getNombreCategoria(), null);
            categorias.add(cr);
            System.out.println("prueba");
            System.out.println(cr.getNombreCategoria());
        }


        CategoriaListREST p = new CategoriaListREST(categorias);
//         c.getProductoCategoriaList().get(0).getCategoria().getNombreCategoria();
        return p;
    }

    @GET
    @Path("nombre/{nombre}/producto/all/{pag}")
    @Produces({"application/xml", "application/json"})
    public CategoriaListREST productosDeCategoria(@PathParam("nombre") String nombre, @PathParam("pag") int pag) {
        Query q = em.createNativeQuery("select c.id_categoria from categoria c where c.nombre_categoria like '%" + nombre + "%' order by c.nombre_categoria;");
        List<Integer> idList = (List<Integer>) q.getResultList();
        List<CategoriaREST> categorias = new ArrayList<CategoriaREST>();
        for (int j = 0; j < idList.size(); j++) {
            System.out.println(idList.get(j));
            Categoria c = super.find(idList.get(j));

            System.out.println(c.getNombreCategoria());
            q = em.createNamedQuery("ProductoCategoria.buscarPorPkFkCategoria")
                    .setParameter("pkFkCategoria", idList.get(j));
            int resultadoPorPagina = 10;
            //Paginación resultados por pagina 
            q.setFirstResult((pag * resultadoPorPagina) - resultadoPorPagina);
            q.setMaxResults(resultadoPorPagina);
            List<ProductoCategoria> pc = (List<ProductoCategoria>) q.getResultList();
            List<Producto> pl = new ArrayList<Producto>();
            if (!pc.isEmpty()) {
                for (int i = 0; i < pc.size(); i++) {
                    pl.add(pc.get(i).getProducto());
                }
                CategoriaREST cr = new CategoriaREST(c.getIdCategoria(), c.getNombreCategoria(), pl);
                categorias.add(cr);
                System.out.println("prueba");
                System.out.println(cr.getNombreCategoria());
            }


        }
        CategoriaListREST p = new CategoriaListREST(categorias);
//         c.getProductoCategoriaList().get(0).getCategoria().getNombreCategoria();
        return p;
    }

    @GET
    @Path("nombre/{nombre}/producto/nombre/{nombreP}")
    @Produces({"application/xml", "application/json"})
    public CategoriaListREST productosDeCategoria(@PathParam("nombre") String nombre, @PathParam("nombreP") String nombreP) {
        Query q = em.createNativeQuery(
                "select c.id_categoria from categoria c, producto_categoria pc, producto p "
                + "where (p.id_producto=pc.pk_fk_producto and p.nombre_producto like '%" + nombreP + "%') "
                + "and (c.id_categoria=pc.pk_fk_categoria and c.nombre_categoria like '%" + nombre + "%')"
                + "group by c.id_categoria"
                + " order by c.nombre_categoria");
        List<Integer> idList = (List<Integer>) q.getResultList();
        List<CategoriaREST> categorias = new ArrayList<CategoriaREST>();
        for (int j = 0; j < idList.size(); j++) {
            System.out.println(idList.get(j));
            Categoria c = super.find(idList.get(j));

            System.out.println(c.getNombreCategoria());
            q = em.createNativeQuery(
                    "select p.id_producto from categoria c, producto_categoria pc, producto p "
                    + "where (p.id_producto=pc.pk_fk_producto and p.nombre_producto like '%" + nombreP + "%') "
                    + "and (c.id_categoria=pc.pk_fk_categoria and c.id_categoria=" + c.getIdCategoria() + ")"
                    + "order by p.nombre_producto;");
            List<Integer> idPList = (List<Integer>) q.getResultList();
            List<Producto> pl = new ArrayList<Producto>();
            if (!idPList.isEmpty()) {
                for (int i = 0; i < idPList.size(); i++) {
                    pl.add(productoFacade.find(idPList.get(i)));
                }
                CategoriaREST cr = new CategoriaREST(c.getIdCategoria(), c.getNombreCategoria(), pl);
                categorias.add(cr);
                System.out.println("prueba");
                System.out.println(cr.getNombreCategoria());
            }


        }
        CategoriaListREST p = new CategoriaListREST(categorias);
//         c.getProductoCategoriaList().get(0).getCategoria().getNombreCategoria();
        return p;
    }

    @GET
    @Path("{id}/producto/")
    @Produces({"application/xml", "application/json"})
    public List<ProductoCategoria> findPC(@PathParam("id") Integer id) {
        Categoria c = super.find(id);
        return c.getProductoCategoriaList();

    }
}
