/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import auxiliar.DireccionServidor;
import carritocompra.CarritoCompra;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Categoria;
import modelo.Cliente;
import modelo.Lugar;
import modelo.Orden;
import modelo.Producto;
import modelo.ProductoCategoria;
import persistencia.CategoriaFacade;
import persistencia.ClienteFacade;
import persistencia.LugarFacade;
import persistencia.OrdenFacade;
import persistencia.ProductoFacade;
import validate.Validator;

/**
 *
 * @author Jesus
 */
@WebServlet(name = "ServletControlador",
        loadOnStartup = 1,
        urlPatterns = {"/categoria",
    "/producto",
    "/verCarrito",
    "/actualizarCarrito",
    "/anadirAlCarrito",
    "/cerrarCarrito",
    "/realizarPago",
    "/buscar",
    "/agregarAlCarrito",
    "/verCarrito",
    "/actualizarCarrito",
    "/checkout",
    "/realizarCompra",})
public class ServletControlador extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String recargo;
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private ClienteFacade clienteFacade;
    @EJB
    private LugarFacade lugarFacade;
    @EJB
    private ManejadorOrden manejadorOrden;
@EJB
    private OrdenFacade ordenFacade;
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // Inicializar el servlet con informacion ya configurada.
        recargo = servletConfig.getServletContext().getInitParameter("recargoPorEntrega");

        System.out.println("recargo: " + recargo);


        getServletContext().setAttribute("categorias", categoriaFacade.findAll());
        getServletContext().setAttribute("categoriaSeleccionada",categoriaFacade.find(8));
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DireccionServidor.getInstance().setPuertoServidor(request.getRequestURL().toString());
        System.out.println(DireccionServidor.getInstance().getUrlServidor());
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        Categoria categoriaSeleccionada;
        Categoria categoriaSearch;
        List<ProductoCategoria> productosCategoria;
        Producto productoSeleccionado;
        session.setAttribute("mensaje", null);
        boolean sesionIniciada;
        String correoElectronico = "";
        if (session.getAttribute("loggedIn") == null) {
            sesionIniciada = false;
        } else {
            sesionIniciada = true;
        }
        if (session.getAttribute("correoElectronico") != null) {
            correoElectronico = session.getAttribute("correoElectronico").toString();
        }
        if (userPath.equals("/categoria")) {

            String idCategoria = request.getQueryString();

            if (idCategoria != null) {

                categoriaSeleccionada = categoriaFacade.find(Integer.parseInt(idCategoria));

                session.setAttribute("categoriaSeleccionada", categoriaSeleccionada);

            }

        } else if (userPath.equals("/producto")) {
            String idProducto = request.getQueryString();
            if (idProducto != null) {
                productoSeleccionado = productoFacade.find(Integer.parseInt(idProducto));
                String urlActual = request.getRequestURL().toString();
                urlActual = urlActual.replace("localhost:8081", "jshop.com");
                urlActual = urlActual.replace("localhost:8080", "jshop.com");
                System.out.println(urlActual);
                System.out.println(urlActual + "?" + productoSeleccionado.getIdProducto());
                session.setAttribute("urel", urlActual + "?" + productoSeleccionado.getIdProducto());

                categoriaSeleccionada = productoSeleccionado.getProductoCategoriaList().get(0).getCategoria();

                session.setAttribute("categoriaSeleccionada", categoriaSeleccionada);

                session.setAttribute("productoSeleccionado", productoSeleccionado);

            }


        } else if (userPath.equals("/buscar")) {
            String idBusqueda = request.getParameter("categoriaSeleccionada").toString();
            String busqueda = request.getParameter("q");
            String mensaje = null;

            if (!idBusqueda.equals("")) {

                categoriaSearch = categoriaFacade.find(Integer.parseInt(idBusqueda));
                mensaje = "Productos de " + categoriaSearch.getNombreCategoria();
                session.setAttribute("todasLasCategorias", null);
                session.setAttribute("categoriaSearch", categoriaSearch);

            } else {
                categoriaSearch = null;
                session.setAttribute("todasLasCategorias", categoriaFacade.findAll());
                mensaje = "Productos de todas las categorias";
            }
            session.setAttribute("busqueda", busqueda);
            session.setAttribute("mensaje", mensaje);

        } 
        else if (userPath.equals("/verCarrito")) {

            String clear = request.getParameter("clear");

            if ((clear != null) && clear.equals("true")) {

                CarritoCompra carrito = (CarritoCompra) session.getAttribute("carrito");
                carrito.clear();
            }

            userPath = "/carrocompra";



        } else if (userPath.equals("/checkout")) {
            if (!correoElectronico.equals("")) {
                System.out.println(correoElectronico);
                Cliente cliente = clienteFacade.buscarPorCorreo(correoElectronico);
                if (cliente != null) {
                    if (cliente.getTipodepagoList().isEmpty()){
                        session.setAttribute("mensaje", "noTDC");
                        userPath="";
                    }else{
                    CarritoCompra carrito = (CarritoCompra) session.getAttribute("carrito");
                    session.setAttribute("listaPais", lugarFacade.listarPorTipo("Pais"));
                    session.setAttribute("listaEstado", lugarFacade.listarPorTipo("Estado"));
                    session.setAttribute("listaCiudad", lugarFacade.listarPorTipo("Ciudad"));
                    session.setAttribute("metPagos", cliente.getTipodepagoList());
                    carrito.calcularTotal(recargo);
                    }
                }
            }

        }

        String url;
        if (!userPath.equals("")) {
            url = "/WEB-INF/vista" + userPath + ".jsp";
        } else {
            url = "/index.jsp";
        }
        try {
            request.getRequestDispatcher(url).forward(request, response);
            session.setAttribute("actualURL", userPath);

        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("urel", "http://jshop.com");
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        Cliente cliente;
        session.setAttribute("mensaje", null);
        CarritoCompra carrito = (CarritoCompra) session.getAttribute("carrito");
        Validator validator = new Validator();

        boolean sesionIniciada;
        String correoElectronico = "";
        if (session.getAttribute("loggedIn") == null) {
            sesionIniciada = false;
        } else {
            sesionIniciada = true;
        }
        if (session.getAttribute("correoElectronico") != null) {
            correoElectronico = session.getAttribute("correoElectronico").toString();
        }

        if (userPath.equals("/agregarAlCarrito")) {
            if (!sesionIniciada) {
                session.setAttribute("mensaje", "noCarrito");
                userPath = "";
            } else {

                if (carrito == null) {

                    carrito = new CarritoCompra();
                    session.setAttribute("carrito", carrito);
                }

                String idProducto = request.getParameter("idProducto");

                if (!idProducto.isEmpty()) {

                    Producto producto = productoFacade.find(Integer.parseInt(idProducto));
                    carrito.agregarItem(producto);
                }

                System.out.println(carrito.getNumeroDeItems());

                userPath = "/carrocompra";

            }
        } else if (userPath.equals("/actualizarCarrito")) {

            String idProducto = request.getParameter("idProducto");
            String cantidad = request.getParameter("cantidad");
            System.out.println(idProducto);
            System.out.println(cantidad);
            boolean invalidEntry = validator.validateQuantity(idProducto, cantidad);

            if (!invalidEntry) {

                Producto producto = productoFacade.find(Integer.parseInt(idProducto));
                carrito.update(producto, cantidad);
            }

            userPath = "/carrocompra";

        } else if (userPath.equals("/realizarCompra")) {

            if (carrito != null) {

                String direccionEntrega = request.getParameter("direccion");
                String fkLugar = request.getParameter("ciudadSelect");
                Lugar lugar=lugarFacade.find(Integer.parseInt(fkLugar));
                System.out.println(direccionEntrega);
                if (correoElectronico != "") {
                    System.out.println(correoElectronico);
                    cliente = clienteFacade.buscarPorCorreo(correoElectronico);
                    if (cliente != null) {
                        int orderId = manejadorOrden.placeOrder(cliente, carrito,direccionEntrega,lugar);
                        Orden orden=ordenFacade.find(orderId);
                        cliente.getOrdenList().add(ordenFacade.find(orderId));
                        clienteFacade.edit(cliente);
                        
                        System.out.println("Order ID: " + orderId);
                        if (orderId != 0) {

                            carrito = null;
                            session.setAttribute("carrito", null);
                            userPath = "/perfil";

                        }else {
                userPath = "/checkout";
                request.setAttribute("orderFailureFlag", true);
            }
                    }
                }   
            } 
        }
String url;
        if (!userPath.equals("")) {
            url = "/WEB-INF/vista" + userPath + ".jsp";
        } else {
            url = "/index.jsp";
        }
        try {
            request.getRequestDispatcher(url).forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
        public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
