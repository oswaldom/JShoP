/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
import modelo.Producto;
import modelo.ProductoCategoria;
import persistencia.CategoriaFacade;
import persistencia.ClienteFacade;
import persistencia.LugarFacade;
import persistencia.ProductoFacade;

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
    "/registrar",
    "/registrarme"
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
    private ClienteFacade clienteFacade;
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
        
        if (userPath.equals("/categoria")) {

            String idCategoria = request.getQueryString();

            if (idCategoria != null) {

                categoriaSeleccionada = categoriaFacade.find(Integer.parseInt(idCategoria));

                // place selected category in session scope
                session.setAttribute("categoriaSeleccionada", categoriaSeleccionada);

                if (!categoriaSeleccionada.getCategoriaList().isEmpty()){ 
                    
                    session.setAttribute("subCategorias", categoriaSeleccionada.getCategoriaList());
                    
                }

                else{
                    // get all products for selected category
                    productosCategoria = categoriaSeleccionada.getProductoCategoriaList();

                    // place category products in session scope
                    session.setAttribute("productosCategoria", productosCategoria);
                  
                }
            }

} else if (userPath.equals("/producto")) {
            String idProducto = request.getQueryString();
            if (idProducto != null) {
                // get selected category
                productoSeleccionado = productoFacade.find(Integer.parseInt(idProducto));
                String urlActual = request.getRequestURL().toString();
            urlActual = urlActual.replace("localhost:8081", "jshop.com");
            urlActual = urlActual.replace("localhost:8080", "jshop.com");
            System.out.println(urlActual);
            System.out.println(urlActual+"?"+productoSeleccionado.getIdProducto());
            session.setAttribute("urel", urlActual+"?"+productoSeleccionado.getIdProducto());
            
             categoriaSeleccionada = productoSeleccionado.getProductoCategoriaList().get(0).getCategoria();

                // place selected category in session scope
                session.setAttribute("categoriaSeleccionada", categoriaSeleccionada);

                // get all products for selected category
//                Collection<Product> relatedProducts = selectedCategory.getProductCollection();
                // place selected category in session scope
               
                session.setAttribute("productoSeleccionado", productoSeleccionado);
//                session.setAttribute("relatedProducts", relatedProducts);

            }
        }
            // if cart page is requested
//        } else if (userPath.equals("/verCarrito")) {
//
//            String clear = request.getParameter("clear");
//
//            if ((clear != null) && clear.equals("true")) {
//
//                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
//                cart.clear();
//            }
//
//            userPath = "/cart";
//
//
//            // if checkout page is requested
//
//        } else if (userPath.equals("/checkout")) {
//
//            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
//
//            // calculate total
//            cart.calculateTotal(surcharge);
//
//            // forward to checkout page and switch to a secure channel
//
//
//            // if user switches language
//        } else if (userPath.equals("/product")) {
//            String productId = request.getQueryString();
//            if (productId != null) {
//                // get selected category
//                selectedProduct = productFacade.find(Integer.parseInt(productId));
//                String urlActual = request.getRequestURL().toString();
//            urlActual = urlActual.replace("localhost:8081", "jshop.com");
//            System.out.println(urlActual);
//            System.out.println(urlActual+"?"+selectedProduct.getId());
//            session.setAttribute("urel", urlActual+"?"+selectedProduct.getId());
//            
//             selectedCategory = selectedProduct.getCategory();
//
//                // place selected category in session scope
//                session.setAttribute("selectedCategory", selectedCategory);
//
//                // get all products for selected category
//                Collection<Product> relatedProducts = selectedCategory.getProductCollection();
//                // place selected category in session scope
//               
//                session.setAttribute("selectedProduct", selectedProduct);
//                session.setAttribute("relatedProducts", relatedProducts);
//
//            }
//        } else if (userPath.equals("/logout")) {
//            session.setAttribute("loggedIn", null);
//            userPath = "";
//        }
//        else if (userPath.equals("/activar")){
//            String verification_code = request.getQueryString();
//            System.out.println(verification_code);
//            userPath="";
//            if(customerFacade.activateAccount(verification_code)!=0){
//                System.out.println("activado");
//                session.setAttribute("messageBox", true);
//            }else{
//                session.setAttribute("messageBox", false);
//                }
//        }
         else if (userPath.equals("/noRegistrado")) {
            request.getSession().setAttribute("registrado", null);
            Calendar date = Calendar.getInstance();
            date.setTime(new Date());
            Format f = new SimpleDateFormat("dd/MM/yyyy");
            date.add(Calendar.YEAR, -18);
            request.getSession().setAttribute("fechaPredeterminada", f.format(date.getTime()));
//              String fechaPredeterminada= new S();

            userPath = "/registrarme";
        } else if (userPath.equals("/registrarme")){
            List<Lugar> listaPais=lugarFacade.listarPaises();
            List<Lugar> listaCiudad=null;
            for (Lugar l:listaPais){
                listaCiudad=l.getLugarList();
            }
           session.setAttribute("listaPais", listaPais);
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
        Cliente cliente = (Cliente) session.getAttribute("cliente");
//        if (userPath.equals("/anadirAlCarrito")) {
//
//            userPath = "/categoria";
//
//        } else if (userPath.equals("/actualizarCarrito")) {
//
//            userPath = "/categoria";
//        } else if (userPath.equals("/realizarPago")) {
//            userPath = "/categoria";
        if (userPath.equals("/iniciarSesion")) {
            if (cliente == null) {

                String email = request.getParameter("correo");
                String pss = request.getParameter("pswd");
                int idCliente = clienteFacade.validarLogin(email, pss);
                if (idCliente != 0) {
                    System.out.println("Validado");
                    cliente = clienteFacade.find(idCliente);
                    System.out.println(cliente.getCorreoElectronico());

                    userPath = "";
                    session.setAttribute("loggedIn", "Yes");
                    session.setAttribute("nombre", cliente.getCorreoElectronico());
                    session.setAttribute("idCliente", cliente.getIdCliente());
                } else {
                    userPath = "/confirmation";
                }
            }
        } else if (userPath.equals("/cerrarSesion")) {
            session.setAttribute("loggedIn", null);
            userPath = "";
//
        } else if (userPath.equals("/noRegistrado")) {
            System.out.println("holaaanoregistrado");
            userPath = "/registrarme";
        } //else if (userPath.equals("/registrar")) {
//            String name = request.getParameter("name");
//                String email = request.getParameter("email");
//                String phone = request.getParameter("phone");
//                String address = request.getParameter("address");
//                String password=request.getParameter("password");
//                String cityRegion = request.getParameter("cityRegion");
//                String ccNumber = request.getParameter("creditcard");
//                customerFacade.registrar(name, phone, address, cityRegion, ccNumber, password, email);
//            userPath = "";
//
//        }
//
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
