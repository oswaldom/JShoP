/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Cliente;
import org.expressme.openid.Association;
import org.expressme.openid.Authentication;
import org.expressme.openid.Endpoint;
import org.expressme.openid.OpenIdException;
import org.expressme.openid.OpenIdManager;
import persistencia.ClienteFacade;

/**
 *
 * @author Jesus
 */
@WebServlet(name = "ServletOpenId",
        loadOnStartup = 1,
        urlPatterns = {"/ServletOpenId"})
public class ServletOpenId extends HttpServlet {

    static final long ONE_HOUR = 3600000L;
    static final long TWO_HOUR = ONE_HOUR * 2L;
    static final String ATTR_MAC = "openid_mac";
    static final String ATTR_ALIAS = "openid_alias";
    OpenIdManager manager;
    @EJB
    ClienteFacade clienteFacade;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        manager = new OpenIdManager();
        manager.setRealm("http://localhost:8081");
        manager.setReturnTo("http://localhost:8081/JShoP/ServletOpenId");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String op = request.getParameter("op");
        System.out.println("doget"+request.getRequestURL());
        if (!request.getRequestURL().toString().contains("localhost")) {
                String ip = ListNets.getCurrentEnvironmentNetworkIp();
                String puerto = "8081";
                String direccion = "http://" + ip + ":" + puerto;
                System.out.println(direccion);
                manager.setRealm(direccion);
                manager.setReturnTo(direccion + "/JShoP/ServletOpenId");
            }else{
            manager.setRealm("http://localhost:8081");
        manager.setReturnTo("http://localhost:8081/JShoP/ServletOpenId");
        }
        if (op == null) { //op viene en nulo cuando ya has iniciado sesion en google desde esa pc con alguna cuenta
            System.out.println("opnull"+request.getRequestURL());
            System.out.println("entre1");
            checkNonce(request.getParameter("openid.response_nonce"));

            // get authentication:

            byte[] mac_key = (byte[]) request.getSession().getAttribute(ATTR_MAC);
            System.out.println(mac_key);
            String alias = (String) request.getSession().getAttribute(ATTR_ALIAS);
            System.out.println(alias);
            //Aki podemos obtener identidad, correo, etc de la persona que se autentico, si lo queremos mostrar en la pagina siguiente
            Authentication authentication = manager.getAuthentication(request, mac_key, alias);
            System.out.println("entre3");
            String identity = authentication.getIdentity();
            System.out.println(identity);
            String email = authentication.getEmail();
            int idCliente = clienteFacade.validarCorreo(email);
            if (idCliente != 0) {
                Cliente cliente=clienteFacade.find(idCliente);
                System.out.println(email);
                System.out.println(authentication.getFullname());
                response.sendRedirect("../JShoP/index.jsp");
                if (cliente.getCodActivacion()!=null){
                        request.getSession().setAttribute("mensaje", "cuentaNoActivada");
                        request.getSession().setAttribute("correoElectronico", cliente.getCorreoElectronico());
                        request.getSession().setAttribute("loggedIn", null);
                    } else{
                         System.out.println(cliente.getCorreoElectronico());
                         
                    request.getSession().setAttribute("loggedIn", "Valido");
                    request.getSession().setAttribute("correoElectronico", cliente.getCorreoElectronico());
                    }
                


            } else {
                request.getSession().setAttribute("mensaje", "noRegistrado");
                request.getSession().setAttribute("correoElectronico", email);
                request.getSession().setAttribute("loggedIn", null);
                Cliente cliente = new Cliente();
                String nombreCliente = authentication.getFirstname();
                String apellidoCliente = authentication.getLastname();
                String correoElectronico = authentication.getEmail();

                cliente.setApellidoCliente(apellidoCliente);
                cliente.setNombreCliente(nombreCliente);
                cliente.setCorreoElectronico(correoElectronico);
                request.getSession().setAttribute("clienteOpenId", cliente);
                request.getSession().setAttribute("param.nombre", nombreCliente);
                response.sendRedirect("../JShoP/index.jsp");
            }
            //Aki se redirecciona mano
        } else if ("Google".equals(op)) {
            // redirect to Google sign on page:

            Endpoint endpoint = manager.lookupEndpoint("Google");
            Association association = manager.lookupAssociation(endpoint);
            request.getSession().setAttribute(ATTR_MAC, association.getRawMacKey());
            request.getSession().setAttribute(ATTR_ALIAS, endpoint.getAlias());
            String url = manager.getAuthenticationUrl(endpoint, association);
            response.sendRedirect(url); //Este lleva a la interfaz de google donde se autentica
            System.out.println("Googleop"+request.getRequestURL());
            if (!request.getRequestURL().toString().contains("localhost")) {
                String ip = ListNets.getCurrentEnvironmentNetworkIp();
                String puerto = "8081";
                String direccion = "http://" + ip + ":" + puerto;
                System.out.println(direccion);
                manager.setRealm(direccion);
                manager.setReturnTo(direccion + "/JShoP/ServletOpenId");
            }else{
                manager.setRealm("http://localhost:8081");
        manager.setReturnTo("http://localhost:8081/JShoP/ServletOpenId");
        
            }
        } else {
            throw new ServletException("Bad parameter op=" + op);
        }
    }

    void showAuthentication(PrintWriter pw, String identity, String email) {
        pw.print("<html><body><h1>Identity</h1><p>");
        pw.print(identity);
        pw.print("</p><h1>Email</h1><p>");
        pw.print(email == null ? "(null)" : email);
        pw.print("</p></body></html>");
        pw.flush();
    }

    void checkNonce(String nonce) {
        // check response_nonce to prevent replay-attack:
        if (nonce == null || nonce.length() < 20) {
            throw new OpenIdException("Verify failed.");
        }
        long nonceTime = getNonceTime(nonce);
        long diff = System.currentTimeMillis() - nonceTime;
        if (diff < 0) {
            diff = (-diff);
        }
        if (diff > ONE_HOUR) {
            throw new OpenIdException("Bad nonce time.");
        }
        if (isNonceExist(nonce)) {
            throw new OpenIdException("Verify nonce failed.");
        }
        storeNonce(nonce, nonceTime + TWO_HOUR);
    }

    
    boolean isNonceExist(String nonce) {
        // TODO: check if nonce is exist in database:
        return false;
    }

    void storeNonce(String nonce, long expires) {
        // TODO: store nonce in database:
    }

    long getNonceTime(String nonce) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .parse(nonce.substring(0, 19) + "+0000")
                    .getTime();
        } catch (ParseException e) {
            throw new OpenIdException("Bad nonce time.");
        }
    }
}
