/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import carritocompra.CarritoCompra;
import carritocompra.CarritoCompraItem;
import modelo.Cliente;
import modelo.Orden;
import modelo.OrdenProducto;
import modelo.OrdenProductoPK;
import modelo.Producto;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.Lugar;
import persistencia.LugarFacade;
import persistencia.OrdenFacade;
import persistencia.OrdenProductoFacade;
import persistencia.ProductoFacade;

/**
 *
 * @author oswaldomaestra
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManejadorOrden {

    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;
    @Resource
    private SessionContext context;
    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private OrdenFacade ordenFacade;
    @EJB
    private OrdenProductoFacade ordenProductoFacade;
    @EJB
    private LugarFacade lugarFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int placeOrder(Cliente cliente, CarritoCompra carrito) {

        try {
            Orden orden = agregarOrden(cliente, carrito);
            addOrderedItems(orden, carrito);
            return orden.getIdOrden();
        } catch (Exception e) {
            context.setRollbackOnly();
            return 0;
        }
    }

    private Orden agregarOrden(Cliente cliente, CarritoCompra carrito) {

        // Se crea el pedido de un cliente.
        Orden orden = new Orden();
        Date date = new Date();
        
        Lugar lugar = lugarFacade.find(1);
        
        
        
        orden.setIdOrden(ordenFacade.sequenceVal());
        orden.setFkCliente(cliente);
        orden.setMontoTotal((carrito.getTotal())); 
        orden.setFechaCreacionOrden(date);
        orden.setFechaEntregada(null);
        orden.setStatusOrden("En proceso");
        orden.setFkLugar(lugar);

        // create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);
        orden.setNroConfirmacion(i);
        

        ordenFacade.create(orden);
        return orden;
    }

    private void addOrderedItems(Orden orden, CarritoCompra carrito) {

        List<CarritoCompraItem> items = carrito.getItems();

        // iterate through shopping cart and create OrderedProducts
        for (CarritoCompraItem ccItem : items) {

            int idProducto = ccItem.getProducto().getIdProducto();

            // set up primary key object
            OrdenProductoPK ordenProductoPK = new OrdenProductoPK();
            
            ordenProductoPK.setIdOrdenProducto(ordenProductoFacade.sequenceVal());
            ordenProductoPK.setPkFkProducto(idProducto);
            ordenProductoPK.setPkFkOrden(orden.getIdOrden());

            // create ordered item using PK object
            OrdenProducto itemOrdenado = new OrdenProducto(ordenProductoPK);
            itemOrdenado.setCantidad(ccItem.getCantidad());
            itemOrdenado.setPrecio(ccItem.getTotal());
            itemOrdenado.setProducto(ccItem.getProducto());

            // set quantity
            itemOrdenado.setCantidad(ccItem.getCantidad());

            ordenProductoFacade.create(itemOrdenado);
        }
    }

    public Map getOrderDetails(int idOrden) {

        Map orderMap = new HashMap();

        // get order
        Orden orden = ordenFacade.find(idOrden);

        // get customer
        Cliente cliente = orden.getFkCliente();

        // get all ordered products
        List<OrdenProducto> productosOrdenados = ordenProductoFacade.findByIdOrden(idOrden);

        // get product details for ordered items
        List<Producto> productos = new ArrayList<Producto>();

        for (OrdenProducto op : productosOrdenados) {

            Producto p = (Producto) productoFacade.find(op.getOrdenProductoPK().getPkFkProducto());
            productos.add(p);
        }

        // add each item to orderMap
        orderMap.put("orderRecord", orden);
        orderMap.put("customer", cliente);
        orderMap.put("orderedProducts", productosOrdenados);
        orderMap.put("products", productos);

        return orderMap;
    }

}

