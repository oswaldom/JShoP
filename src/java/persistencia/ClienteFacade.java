/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import auxiliar.Encriptacion;
import auxiliar.FormatoFecha;
import controlador.ControladorCorreo;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Cliente;
import modelo.Lugar;
import modelo.Orden;
import modelo.OrdenProducto;
import modelo.Producto;
import modelo.Productreview;
import modelo.ProductreviewPK;

/**
 *
 * @author Jesus
 */
@Stateless
@LocalBean
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;
    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private OrdenFacade ordenFacade;
    @EJB
    private OrdenProductoFacade ordenProductoFacade;
    @EJB
    private ProductreviewFacade productReviewFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

    public Cliente buscarPorCorreo(String correoElectronico) {
        Query q = em.createNamedQuery("Cliente.buscarPorCorreoElectronico")
                .setParameter("correoElectronico", correoElectronico);
        Cliente cliente;

        try {
            cliente = (Cliente) q.getSingleResult();
            if (cliente != null) {
                return cliente;
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    public Cliente validarCorreo(String correoElectronico) {
        Query q = em.createNamedQuery("Cliente.buscarPorCorreoElectronico")
                .setParameter("correoElectronico", correoElectronico);
        Cliente cliente = null;
        try {
            cliente = (Cliente) q.getSingleResult();
            return cliente;
        } catch (NoResultException e) {
            return cliente;
        }
    }

    public boolean registrarCliente(String nombreCliente, String apellidoCliente, String fechaNacimiento, int nroCedula, String correoElectronico, String contrasena, String direccionCliente, int codPostal, Lugar fkLugar) {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setIdCliente(sequenceVal());
        nuevoCliente.setNombreCliente(nombreCliente);
        nuevoCliente.setApellidoCliente(apellidoCliente);
        nuevoCliente.setFechaRegistro(new Date());
        nuevoCliente.setFechaNacimiento(FormatoFecha.stringDate("dd/MM/yyyy", fechaNacimiento));
        nuevoCliente.setNroCedula(nroCedula);
        nuevoCliente.setCorreoElectronico(correoElectronico);
        nuevoCliente.setContrasena(contrasena);
        nuevoCliente.setDireccionCliente(direccionCliente);
        nuevoCliente.setCodPostal(codPostal);
        nuevoCliente.setFkLugar(fkLugar);
        nuevoCliente.setStatusCliente("N");
        nuevoCliente.setCodActivacion(Encriptacion.findMD5(correoElectronico));

        create(nuevoCliente);
        try {
            enviarCodActivacion(correoElectronico);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void enviarCodActivacion(String correoElectronico) {
        try {
            ControladorCorreo.getInstance().enviarActivacion(correoElectronico, Encriptacion.findMD5(correoElectronico));
        } catch (MessagingException ex) {
            Logger.getLogger(ClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int activateAccount(String codActivacion) {
        try {
            Query q = em.createNamedQuery("Cliente.buscarPorCodActivacion")
                    .setParameter("codActivacion", codActivacion);
            int idCliente = -1;
            idCliente = Integer.parseInt(q.getSingleResult().toString());

            if (idCliente != -1) {
                Cliente cliente = find(idCliente);
                cliente.setStatusCliente("Activo");
                cliente.setCodActivacion(null);
                edit(cliente);
                return idCliente;
            } else {
                return -1;
            }
        } catch (NoResultException e) {
            return -1;
        }
    }

    
    public int validarLogin(String correoElectronico, String contrasena) {
        try {
            Query q = em.createNamedQuery("Cliente.validarLogin")
                    .setParameter("correoElectronico", correoElectronico)
                    .setParameter("contrasena", contrasena);
            int idCliente = -1;
            idCliente = Integer.parseInt(q.getSingleResult().toString());

            return idCliente;
        } catch (NoResultException e) {
            return -1;
        }
    }
    
    @PermitAll
    public boolean calificarProducto(String correoCliente, int idProducto, int rating, String textoReview) {
        Cliente cliente = buscarPorCorreo(correoCliente);
        Producto producto = productoFacade.find(idProducto);
        if (puedeCalificar(cliente.getIdCliente(), producto.getIdProducto())) {
            ProductreviewPK prPK = new ProductreviewPK();
            int idProductReview;
            idProductReview = productReviewFacade.sequenceVal();
            prPK.setIdProductreview(idProductReview);
            prPK.setPkFkCliente(cliente.getIdCliente());
            prPK.setPkFkProducto(producto.getIdProducto());
            Productreview productReview = new Productreview();
            productReview.setCliente(cliente);
            productReview.setProducto(producto);
            productReview.setProductreviewPK(prPK);
            productReview.setRating(rating);
            productReview.setTextoReview(textoReview);
            productReview.setFechaHora(new Date());
            List<Productreview> productReviewList = producto.getProductreviewList();

            producto.getProductreviewList().add(productReview);
            if (haCalificadoProducto(cliente.getIdCliente(), producto.getIdProducto())) {
                productReviewFacade.create(productReview);
                
            } else {
                //productReviewFacade.edit(productReview);
                productReviewFacade.create(productReview);
            }
            productoFacade.edit(producto);
            return true;
        }
        return false;
    }

    public List<Orden> buscarClienteOrdenProducto(int idCliente, int idProducto) {
        System.out.println("Inicio: buscarClienteOrdenProducto(" + idCliente + "," + idProducto + ")");
        Query q = em.createNativeQuery("select o.id_orden from producto p,orden o, cliente c,orden_producto op "
                + "where op.pk_fk_orden=o.id_orden and o.fk_cliente=" + idCliente + " and c.id_cliente=o.fk_cliente and op.pk_fk_producto=" + idProducto + " and p.id_producto=op.pk_fk_producto");
        List<Orden> ordenList = new ArrayList<Orden>();

        try {
            List<Integer> idList = (List<Integer>) q.getResultList();

            if (!idList.isEmpty()) {
                Iterator i = idList.iterator();
                Orden o;
                int id;
                while (i.hasNext()) {
                    id = Integer.parseInt(i.next().toString());
                    o = ordenFacade.find(id);
                    ordenList.add(o);
                }

            }
            return ordenList;
        } catch (NoResultException e) {
            return ordenList;
        }

    }

    @PermitAll
    public boolean puedeCalificar(int idCliente, int idProducto) {
        boolean haComprado = haComprado(idCliente, idProducto);
        boolean haCompradoProducto = haCompradoProducto(idCliente, idProducto);
        boolean haRecibidoOrdenProducto = haRecibidoOrdenProducto(idCliente, idProducto);
        if (haComprado && haCompradoProducto && haRecibidoOrdenProducto) {
            return true;
        } else {
            return false;
        }
    }

    @PermitAll
    public boolean haComprado(int idCliente, int idProducto) {
        Cliente cliente = find(idCliente);
        if (cliente.getOrdenList().isEmpty()) {
            System.err.println("\nEl cliente no presenta ordenes\n");
            return false;
        } else {
            System.out.println("\nEl cliente presenta ordenes\n");
            return true;
        }
    }

    @PermitAll
    public boolean haCompradoProducto(int idCliente, int idProducto) {
        List<Orden> ordenes = buscarClienteOrdenProducto(idCliente, idProducto);

        if (ordenes.isEmpty()) {
            System.err.println("\nNo ha comprado el producto\n");
            return false;
        } else {
            System.out.println("\nHa comprado el producto\n");
            return true;
        }
    }

    @PermitAll
    public boolean haRecibidoOrdenProducto(int idCliente, int idProducto) {
        if (haCompradoProducto(idCliente, idProducto)) {
            List<Orden> ordenes = buscarClienteOrdenProducto(idCliente, idProducto);
            if (ordenFacade.algunaOrdenEntregada(ordenes)) {
                System.out.println("\nLa orden del producto ha sido entregada\n");
                return true;
            }
        }
        System.err.println("\nLa orden del producto no ha sido entregada\n");
        return false;
    }

    @PermitAll
    public boolean haCalificadoProducto(int idCliente, int idProducto) {
        if (productReviewFacade.haCalificado(idCliente, idProducto)) {
            System.out.println("\nEl cliente ya ha calificado, se prodecera a la modificacion\n");
            return true;
        } else {
            System.out.println("\nEl cliente no ha calificado, se prodecera a la creacion\n");
            return false;
        }
    }
   
}
