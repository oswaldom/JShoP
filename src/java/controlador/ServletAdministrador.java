package controlador;

import auxiliar.DireccionServidor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;
import modelo.Cliente;
import modelo.Orden;
import persistencia.CategoriaFacade;
import persistencia.ClienteFacade;
import persistencia.LugarFacade;
import persistencia.OrdenFacade;
import persistencia.ProductoFacade;

/**
 *
 * @author oswaldomaestra
 */
@WebServlet(name = "ServletAdministrador",
            urlPatterns = {"/admin/",
                           "/admin/verProductos",
                           "/admin/verOrdenes",
                           "/admin/verClientes",
                           "/admin/clienteRecord",
                           "/admin/ordenRecord",
                           "/admin/logout",
                           "/admin/actualizarOrden"
            })
@ServletSecurity(
    @HttpConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL,
                    rolesAllowed = {"jshopAdmin"})
)
public class ServletAdministrador extends HttpServlet {
    
    @EJB
    private OrdenFacade ordenFacade;
    @EJB
    private ClienteFacade clienteFacade;
    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private LugarFacade lugarFacade;

    private String userPath;
    private Cliente cliente;
    private Orden orden;
    private List ordenList = new ArrayList();
    private List clienteList = new ArrayList();
    private List productoList = new ArrayList();
    private Integer proximoProductoId;


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        userPath = request.getServletPath();
        // Si se hace una peticion a verProductos
        if (userPath.equals("/admin/verProductos")) {
            
            productoList = productoFacade.findAll();
            proximoProductoId = productoFacade.count() + 1;
            request.setAttribute("categorias", categoriaFacade.findAll());
            request.setAttribute("productoList", productoList);
            request.setAttribute("proximoProductoId", proximoProductoId);
            
        }


        if (userPath.equals("/admin/logout")) {
            session = request.getSession();
            session.invalidate();
            response.sendRedirect("/JShoP/admin/");
            return;
        }
        if (userPath.equals("/admin/actualizarOrden")) { 
            String numeroOrden=request.getQueryString();
            if (!numeroOrden.equals("")){
                
            Orden ordenSeleccionada=ordenFacade.find(Integer.parseInt(numeroOrden));
            
            response.sendRedirect("/JShoP/admin/actualizarOrden.jsp");
            session.setAttribute("linkOrden", DireccionServidor.getInstance().getUrlServidor() + "/JShoP/qr?qrtext="
                        + DireccionServidor.getInstance().getUrlServidorAdmin()+"/actualizarOrden?" + numeroOrden);
            session.setAttribute("direccionEntrega", lugarFacade.obtenerUbicacion(ordenSeleccionada.getFkLugar()));
            session.setAttribute("ordenSeleccionada", ordenSeleccionada);
            return;
            }
            
        }

        userPath = "/admin/index.jsp";
        try {
            request.getRequestDispatcher(userPath).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}