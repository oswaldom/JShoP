/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
import modelo.Producto;
import modelo.ProductoCategoria;
import persistencia.CategoriaFacade;
import persistencia.ProductoCategoriaFacade;
import persistencia.ProductoFacade;

/**
 *
 * @author oswaldomaestra
 */

@WebServlet(name = "ServletControladorDatos",
        loadOnStartup = 1,
        urlPatterns = {"/admin/agregarProducto"})

public class ServletControladorDatos extends HttpServlet {
    
    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private ProductoCategoriaFacade productoCategoriaFacade;
    @EJB
    private CategoriaFacade categoriaFacade;

    private String URL;
    private String userPath; 
    Producto producto = new Producto();
    private List productoList = new ArrayList();

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        userPath = request.getServletPath();
        
        // Si se hace una peticion a verProductos
        if (userPath.equals("/admin/agregarProducto")) {
            
            
            try{
                Integer idProducto = productoFacade.count() + 1;
                String nombreProducto = request.getParameter("nombreProducto");
                Float precioProducto = Float.parseFloat(request.getParameter("precioProducto"));
                String descripcionProducto = request.getParameter("descripcionProducto");
                
                producto.setIdProducto(idProducto);
                producto.setNombreProducto(nombreProducto);
                producto.setPrecioProducto(precioProducto);
                producto.setDescripcionProducto(descripcionProducto);
            
                productoFacade.create(producto);
                
                userPath = "verProductos";
            
            }catch(Exception ex){
                System.out.println(ex);
            }
        }
        
        // use RequestDispatcher to forward request internally
        URL = "/admin/" + userPath;
        try {
            request.getRequestDispatcher(URL).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
