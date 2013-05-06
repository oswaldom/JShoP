/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import auxiliar.DireccionServidor;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Categoria;
import modelo.Cliente;
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
@WebServlet(name = "ServletCliente",
        urlPatterns = {"/perfil",
    "/iniciarSesion",
    "/cerrarSesion",
    "/activar",
    "/noRegistrado",
    "/registrar",
    "/registrarme", "/verPerfil", "/verOrden", "/orden", "/anadirTarjeta",
    "/solicitarEnlace"
})
public class ServletCliente extends HttpServlet {

    @EJB
    private OrdenFacade ordenFacade;
    @EJB
    private ClienteFacade clienteFacade;
    @EJB
    private OrdenProductoFacade ordenProductoFacade;
    @EJB
    private TipodepagoFacade tipoDePagoFacade;
    @EJB
    private LugarFacade lugarFacade;

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
        DireccionServidor.getInstance().setPuertoServidor(request.getRequestURL().toString());
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

        if (userPath.equals("/registrarme")) {
            if (sesionIniciada) {
                request.getSession().setAttribute("mensaje", "oopsError");
            } else {
                request.getSession().setAttribute("registrado", null);
                Calendar date = Calendar.getInstance();
                date.setTime(new Date());
                Format f = new SimpleDateFormat("dd/MM/yyyy");
                date.add(Calendar.YEAR, -18);
                request.getSession().setAttribute("fechaPredeterminada", f.format(date.getTime()));
                session.setAttribute("listaPais", lugarFacade.listarPorTipo("Pais"));
                session.setAttribute("listaEstado", lugarFacade.listarPorTipo("Estado"));
                session.setAttribute("listaCiudad", lugarFacade.listarPorTipo("Ciudad"));
            }
        } else if (userPath.equals("/activar")) {
            if (sesionIniciada) {
                request.getSession().setAttribute("mensaje", "oopsError");
            } else {
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
            }
        } else if (userPath.equals("/solicitarEnlace")) {
            System.out.println("solicitarEnlace");
            if (!correoElectronico.equals("")) {
                System.out.println("solicitarEnlace");
                clienteFacade.enviarCodActivacion(correoElectronico);
            }
            userPath = "";
        }

        System.out.println(correoElectronico);
        Cliente clienteEnSesion = clienteFacade.buscarPorCorreo(correoElectronico);
        if (userPath.equals("/verPerfil")) {

            if (sesionIniciada) {
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
            } else {
                session.setAttribute("mensaje", "oopsIniciaSesion");
                userPath = "";
            }
        } else if (userPath.equals("/verOrden")) {
            if (sesionIniciada) {
                String idOrden = request.getQueryString();
                Orden orden = ordenFacade.find(Integer.parseInt(idOrden));
                session.setAttribute("linkOrden", DireccionServidor.getInstance().getUrlServidor() + "/JShoP/qr?qrtext="
                        + DireccionServidor.getInstance().getUrlServidor() + "/JShoP" + userPath + "?" + idOrden);

                session.setAttribute("direccionEntrega", lugarFacade.obtenerUbicacion(orden.getFkLugar()));
                session.setAttribute("listaProductoOrden", orden.getOrdenProductoList());
                session.setAttribute("ordenSeleccionada", orden);

                userPath = "/orden";
            } else {
                session.setAttribute("mensaje", "oopsIniciaSesion");
                userPath = "";
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
            if (!sesionIniciada) {
                session.setAttribute("mensaje", "oopsIniciaSesion");
            } else {
                session.setAttribute("mensaje", "oopsError");
            }
            request.getRequestDispatcher("/index.jsp").forward(request, response);
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
        if (session.getAttribute("loggedIn") == null) {
            sesionIniciada = false;
        } else {
            sesionIniciada = true;
        }
        if (session.getAttribute("correoElectronico") != null) {
            correoElectronico = session.getAttribute("correoElectronico").toString();
        }
        if (userPath.equals("/iniciarSesion")) {
            if (!sesionIniciada) {
                String email = request.getParameter("correo");
                String pss = request.getParameter("pswd");

                int idCliente = clienteFacade.validarLogin(email, pss);
                if (idCliente != -1) {
                    System.out.println("Validado");
                    Cliente cliente = clienteFacade.find(idCliente);
                    if (cliente.getCodActivacion() != null) {
                        session.setAttribute("mensaje", "cuentaNoActivada");
                        session.setAttribute("correoElectronico", cliente.getCorreoElectronico());
                        userPath = "";

                    } else {
                        session.setAttribute("loggedIn", "Valido");
                        session.setAttribute("correoElectronico", cliente.getCorreoElectronico());
                        userPath = "";
                    }
                } else {
                    session.setAttribute("loggedIn", null);
                    session.setAttribute("correoElectronico", null);
                    session.setAttribute("mensaje", "Invalido");
                    userPath = "";
                }
                } else {
//                request.getSession().setAttribute("mensaje", "oopsError");
                userPath = "";
            }
        } else if (userPath.equals("/noRegistrado")) {
            userPath = "/registrarme";
        } else if (userPath.equals("/registrar")) {
            if (sesionIniciada) {
                request.getSession().setAttribute("mensaje", "oopsError");
                userPath = "";
            } else {
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
        }
        if (userPath.equals("/cerrarSesion")) {
            if (!sesionIniciada) {
                request.getSession().setAttribute("mensaje", "oopsError");
                userPath = "";
            } else {
                session.setAttribute("loggedIn", null);
                session.setAttribute("correoElectronico", null);
                session.setAttribute("cliente", null);
                session.invalidate();
                userPath = "";
            }
        } else if (userPath.equals("/verPerfil")) {
            if (sesionIniciada) {
                
                String opcionPerfil = request.getQueryString();
                session.setAttribute("opcionPerfil", "metodosDePago");
                if (opcionPerfil.equals("anadirTarjeta")) {
                    String marcacomercial = (String) request.getParameter("metodoSeleccionado");
                    Long numeroDeTarjeta = Long.parseLong(request.getParameter("numeroDeTarjeta"));
                    String fechaVencimiento = (String) request.getParameter("fechaVencimiento");
                    request.setAttribute("param.numeroDeTarjeta", null);
                    request.setAttribute("param.fecha", null);
                    Cliente clienteAct = clienteFacade.buscarPorCorreo(correoElectronico);
                    System.out.println(clienteAct.getApellidoCliente());
                    int i = tipoDePagoFacade.registrarTipoDePago(numeroDeTarjeta, fechaVencimiento, marcacomercial, clienteAct);

                    clienteAct.getTipodepagoList().add(tipoDePagoFacade.find(i));
                    clienteFacade.edit(clienteAct);
                    session.setAttribute("metodosDePago", clienteAct.getTipodepagoList());
                    session.setAttribute("opcionPerfil", "metodosDePago");
                    userPath = "/perfil";
                }
            } else {
                session.setAttribute("mensaje", "oopsIniciaSesion");
                userPath = "";
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
            if (!sesionIniciada) {
                session.setAttribute("mensaje", "oopsIniciaSesion");
            } else {
                session.setAttribute("mensaje", "oopsError");
            }
            request.getRequestDispatcher("/index.jsp").forward(request, response);
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
