/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import auxiliar.DireccionServidor;
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
        manager.setRealm("http://localhost:8080");
        manager.setReturnTo("http://localhost:8080/JShoP/ServletOpenId");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String op = request.getParameter("op");
        determinarRealmReturnTo(request.getRequestURL().toString());
        System.out.println(op);
        if (op == null) { //op viene en nulo cuando ya has iniciado sesion en google desde esa pc con alguna cuenta
            System.out.println("opnull" + request.getRequestURL());
            System.out.println("entre1");
            checkNonce(request.getParameter("openid.response_nonce"));

            // get authentication:

            byte[] mac_key = (byte[]) request.getSession().getAttribute(ATTR_MAC);
            System.out.println(mac_key);
            String alias = (String) request.getSession().getAttribute(ATTR_ALIAS);
            System.out.println(alias);
            Authentication authentication = manager.getAuthentication(request, mac_key, alias);
            String identity = authentication.getIdentity();
            System.out.println(identity);
            String email = authentication.getEmail();
            Cliente cliente = clienteFacade.validarCorreo(email);
            if (cliente != null) {
                System.out.println(email);
                System.out.println(authentication.getFullname());
                response.sendRedirect("../JShoP/index.jsp");
                if (cliente.getCodActivacion() != null) {
                    request.getSession().setAttribute("mensaje", "cuentaNoActivada");
                    request.getSession().setAttribute("correoElectronico", cliente.getCorreoElectronico());
                    request.getSession().setAttribute("loggedIn", null);
                } else {
                    System.out.println(cliente.getCorreoElectronico());
                    request.getSession().setAttribute("mensaje", "");
                    request.getSession().setAttribute("loggedIn", "Valido");
                    request.getSession().setAttribute("correoElectronico", cliente.getCorreoElectronico());
                }



            } else {
                request.getSession().setAttribute("mensaje", "noRegistrado");
                request.getSession().setAttribute("correoElectronico", email);
                request.getSession().setAttribute("loggedIn", null);
                cliente = new Cliente();
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
            
            System.out.println(url);
            response.sendRedirect(url);
            determinarRealmReturnTo(request.getRequestURL().toString());//Este lleva a la interfaz de google donde se autentica
        } else {
            throw new ServletException("Bad parameter op=" + op);
        }
    }

    protected void determinarRealmReturnTo(String url) {

        if (!url.contains("localhost")) {
            
            manager.setRealm(DireccionServidor.getInstance().getUrlServidor());
            manager.setReturnTo(DireccionServidor.getInstance().getUrlServidor() + "/JShoP/ServletOpenId");
        } else {
            manager.setRealm("http://localhost" + DireccionServidor.getInstance().getPuertoServidor());
            manager.setReturnTo("http://localhost" + DireccionServidor.getInstance().getPuertoServidor() + "/JShoP/ServletOpenId");
        }
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
