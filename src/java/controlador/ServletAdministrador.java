package controlador;

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
import persistencia.ClienteFacade;
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
                           "/admin/logout"})
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
            
            request.setAttribute("productoList", productoList);
            request.setAttribute("proximoProductoId", proximoProductoId);
            
        }

        // Si se hace una peticion a verClientes
        if (userPath.equals("/admin/verClientes")) {
            clienteList = clienteFacade.findAll();
            request.setAttribute("clienteList", clienteList);
        }

        // Si se hace una peticion a verOrdenes
        if (userPath.equals("/admin/verOrdenes")) {
            ordenList = ordenFacade.findAll();
            request.setAttribute("ordenList", ordenList);
        }

        // Si se hace una peticion a clienteRecord
        if (userPath.equals("/admin/clienteRecord")) {

            // get customer ID from request
            String customerId = request.getQueryString();

            // get customer details
            cliente = clienteFacade.find(Integer.parseInt(customerId));
            request.setAttribute("customerRecord", cliente);

            // get customer order details
            orden = ordenFacade.findByCustomer(cliente);
            request.setAttribute("orden", orden);
        }
        
        // Si se hace una peticion a ordenRecord
        /*if (userPath.equals("/admin/orderRecord")) {

            // get customer ID from request
            String orderId = request.getQueryString();

            // get order details
            Map orderMap = orderManager.getOrderDetails(Integer.parseInt(orderId));

            // place order details in request scope
            request.setAttribute("customer", orderMap.get("customer"));
            request.setAttribute("products", orderMap.get("products"));
            request.setAttribute("orderRecord", orderMap.get("orderRecord"));
            request.setAttribute("orderedProducts", orderMap.get("orderedProducts"));
        }*/

        // if logout is requested
        if (userPath.equals("/admin/logout")) {
            session = request.getSession();
            session.invalidate();   // terminate session
            response.sendRedirect("/JShoP/admin/");
            return;
        }

        // use RequestDispatcher to forward request internally
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