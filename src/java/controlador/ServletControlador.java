/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
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
import modelo.OrdenProducto;
import modelo.Producto;
import modelo.ProductoCategoria;
import persistencia.CategoriaFacade;
import persistencia.ClienteFacade;
import persistencia.LugarFacade;
import persistencia.OrdenFacade;
import persistencia.OrdenProductoFacade;
import persistencia.ProductoFacade;
import persistencia.TipodepagoFacade;

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
    "/iniciarSesion",
    "/cerrarSesion",
    "/activar",
    "/noRegistrado",
    "/registrar", "/solicitarEnlace",
    "/registrarme", "/perfil", "/verPerfil", "/verOrden", "/orden", "/anadirTarjeta"
})
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
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private OrdenFacade ordenFacade;
    @EJB
    private ClienteFacade clienteFacade;
    @EJB
    private OrdenProductoFacade ordenProductoFacade;
    @EJB
    private TipodepagoFacade tipoDePagoFacade;
    ;
    @EJB
    private LugarFacade lugarFacade;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);
        getServletContext().setAttribute("categorias", categoriaFacade.findAll());
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

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        System.out.println(userPath);
        Categoria categoriaSeleccionada;
        List<ProductoCategoria> productosCategoria;
        Producto productoSeleccionado;
        session.setAttribute("registroExitoso", null);
        session.setAttribute("mensaje", null);
        boolean sesionIniciada;
        String correoElectronico = "";
        if (session.getAttribute("loggedIn")==null)
            sesionIniciada = false;
        else
            sesionIniciada=true;
        if (session.getAttribute("correoElectronico") != null)
            correoElectronico = session.getAttribute("correoElectronico").toString();
        if (userPath.equals("/categoria")) {

            String idCategoria = request.getQueryString();

            if (idCategoria != null) {

                categoriaSeleccionada = categoriaFacade.find(Integer.parseInt(idCategoria));

                // place selected category in session scope
                session.setAttribute("categoriaSeleccionada", categoriaSeleccionada);

                // get all products for selected category
                productosCategoria = categoriaSeleccionada.getProductoCategoriaList();
//                // place category products in session scope
                session.setAttribute("productosCategoria", productosCategoria);
            }

        } else if (userPath.equals("/producto")) {
            String idProducto = request.getQueryString();
            if (idProducto != null) {
                productoSeleccionado = productoFacade.find(Integer.parseInt(idProducto));
                String urlActual = request.getRequestURL().toString();
                urlActual = urlActual.replace("localhost:8081", "jshop.com");
                urlActual = urlActual.replace("localhost:8080", "jshop.com");

                categoriaSeleccionada = productoSeleccionado.getProductoCategoriaList().get(0).getCategoria();
                session.setAttribute("urel", urlActual + "?" + productoSeleccionado.getIdProducto());
                session.setAttribute("categoriaSeleccionada", categoriaSeleccionada);
                session.setAttribute("productoSeleccionado", productoSeleccionado);
            }
        }
        if (!sesionIniciada) {
            if (userPath.equals("/registrarme")) {
                request.getSession().setAttribute("registrado", null);
                Calendar date = Calendar.getInstance();
                date.setTime(new Date());
                Format f = new SimpleDateFormat("dd/MM/yyyy");
                date.add(Calendar.YEAR, -18);
                request.getSession().setAttribute("fechaPredeterminada", f.format(date.getTime()));
                session.setAttribute("listaPais", lugarFacade.listarPorTipo("Pais"));
                session.setAttribute("listaEstado", lugarFacade.listarPorTipo("Estado"));
                session.setAttribute("listaCiudad", lugarFacade.listarPorTipo("Ciudad"));
            } else if (userPath.equals("/activar")) {
                String verification_code = request.getQueryString();
                System.out.println(verification_code);
                userPath = "";
                int idCliente = clienteFacade.activateAccount(verification_code);
                if (idCliente != -1) {
                    session.setAttribute("mensaje", "cuentaActivada");
                    session.setAttribute("loggedIn", "Valido");
                    session.setAttribute("correoElectronico", clienteFacade.find(idCliente).getCorreoElectronico());
                } else {
                    //TODO
                    //Pagina de error.
                }
            }else if (userPath.equals("/solicitarEnlace")) {
                if(!correoElectronico.equals("")){
                clienteFacade.enviarCodActivacion(correoElectronico);
                }
                userPath = "";
            }


        } else { //Esta iniciada la sesion;
            Cliente clienteEnSesion = clienteFacade.buscarPorCorreo(correoElectronico);
            if (userPath.equals("/verPerfil")) {
                String opcionPerfil = request.getQueryString();
                if (opcionPerfil.equals("pedidos")) {
                    session.setAttribute("ordenesCliente", clienteEnSesion.getOrdenList());
                    System.out.println("entre orden");
                } else if (opcionPerfil.equals("metodosDePago")) {
                    session.setAttribute("metodosDePago", clienteEnSesion.getTipodepagoList());
                } else if (opcionPerfil.equals("anadirTarjeta")) {
                } else if (opcionPerfil.equals("miCuenta")) {
                }
                session.setAttribute("opcionPerfil", opcionPerfil);
                userPath = "/perfil";
            } else if (userPath.equals("/verOrden")) {
                String idOrden = request.getQueryString();
                Orden orden = ordenFacade.find(Integer.parseInt(idOrden));
                float total = 0;
                for (OrdenProducto op : orden.getOrdenProductoList()) {
                    total += op.getPrecio() * op.getCantidad();
                }
                Format f = new SimpleDateFormat("hh:mm:ss a dd/MM/yyyy ");
                String ipServidor = ListNets.getCurrentEnvironmentNetworkIp();
                session.setAttribute("linkOrden", "http://" + ipServidor + ":8081/JShoP/qr?qrtext="
                        + "http://" + ipServidor + ":8081/JShoP" + userPath + "?" + idOrden);
                session.setAttribute("numeroDeOrden", idOrden);
                session.setAttribute("totalOrden", total);
                session.setAttribute("fechaDePedido", f.format(orden.getFechaCreacionOrden()));
                session.setAttribute("direccionEntrega", orden.getDireccionEntrega());
                session.setAttribute("listaProductoOrden", orden.getOrdenProductoList());
                userPath = "/orden";
            }
        }

        String url;
        // use RequestDispatcher to forward request internally
        if (!userPath.equals("")) {
            url = "/WEB-INF/vista" + userPath + ".jsp";
        } else {
            url = "/index.jsp";
        }
        try {
            request.getRequestDispatcher(url).forward(request, response);

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
        session.setAttribute("mensaje", null);
        boolean sesionIniciada;
        String correoElectronico = "";
        if (session.getAttribute("loggedIn")==null)
            sesionIniciada = false;
        else
            sesionIniciada=true;
        if (session.getAttribute("correoElectronico") != null)
            correoElectronico = session.getAttribute("correoElectronico").toString();
        if (!sesionIniciada) {
            if (userPath.equals("/iniciarSesion")) {
                String email = request.getParameter("correo");
                String pss = request.getParameter("pswd");
                
                int idCliente = clienteFacade.validarLogin(email, pss);
                if (idCliente != 0) {
                    System.out.println("Validado");
                    Cliente cliente = clienteFacade.find(idCliente);
                    if (cliente.getCodActivacion() != null) {
                        session.setAttribute("mensaje", "cuentaNoActivada");
                        session.setAttribute("correoElectronico", cliente.getCorreoElectronico());

                    } else {
                        session.setAttribute("loggedIn", "Valido");
                        session.setAttribute("correoElectronico", cliente.getCorreoElectronico());
                    }
                } else {
                    session.setAttribute("loggedIn", null);
                    session.setAttribute("correoElectronico", null);
                    session.setAttribute("mensaje", "Invalido");

                }
                userPath = "";
            } else if (userPath.equals("/noRegistrado")) {
                System.out.println("holaaanoregistrado");
                userPath = "/registrarme";
            } else if (userPath.equals("/registrar")) {
                int nroCedula = Integer.parseInt(request.getParameter("cedula"));
                int fkLugar = Integer.parseInt(request.getParameter("ciudadSelect").toString());
                String nombreCliente = request.getParameter("nombre");
                String apellidoCliente = request.getParameter("apellido");
                String correo = request.getParameter("correo");
                String contrasena = request.getParameter("contrasena");
                String fechaNacimiento = request.getParameter("fecha");
                String direccionCliente = request.getParameter("direccion");
                int codPostal = Integer.parseInt(request.getParameter("codPostal"));
                System.out.println(nroCedula);
                System.out.println(nombreCliente + apellidoCliente + correo + contrasena + fechaNacimiento + direccionCliente + codPostal);
                boolean registroExitoso = false;
                registroExitoso = clienteFacade.registrarCliente(nombreCliente, apellidoCliente, fechaNacimiento, nroCedula, correo, contrasena, direccionCliente, codPostal, lugarFacade.find(fkLugar));
                if (registroExitoso) {
                    session.setAttribute("mensaje", "registroExitoso");
                }
                userPath = "";

            }
        } else {//Existe sesion iniciada
            if (userPath.equals("/cerrarSesion")) {
                session.setAttribute("loggedIn", null);
                session.setAttribute("correoElectronico", null);
                session.setAttribute("cliente", null);
                session.invalidate();
                userPath = "";
//
            } else if (userPath.equals("/anadirTarjeta")) {
                String marcacomercial = (String) request.getParameter("metodoSeleccionado");
                Long numeroDeTarjeta = Long.parseLong(request.getParameter("numeroDeTarjeta"));
                String fechaVencimiento = (String) request.getParameter("fechaVencimiento");
                System.out.println(marcacomercial);
                System.out.println(numeroDeTarjeta);
                System.out.println(fechaVencimiento);
                Cliente clienteAct = clienteFacade.buscarPorCorreo(correoElectronico);
                int i = tipoDePagoFacade.registrarTipoDePago(numeroDeTarjeta, fechaVencimiento, marcacomercial, clienteAct);

                clienteAct.getTipodepagoList().add(tipoDePagoFacade.find(i));
                clienteFacade.edit(clienteAct);
                session.setAttribute("metodosDePago", clienteAct.getTipodepagoList());
                session.setAttribute("opcionPerfil", "metodosDePago");
                userPath = "/perfil";
            }
        }
        String url;
        // use RequestDispatcher to forward request internally
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
