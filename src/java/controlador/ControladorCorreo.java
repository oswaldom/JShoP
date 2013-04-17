/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Ejemplo de envio de correo simple con JavaMail
 *
 * @author Luify
 *
  */
public class ControladorCorreo
{
    private Session session;
    private String host;
    private String starttls;
    private String port;
    private String auth;
    private String usuario;
    private String contrasena;

    public ControladorCorreo(String host, String starttls, String port, String auth, String usuario, String contrasena) {
        this.host = host;
        this.starttls = starttls;
        this.port = port;
        this.auth = auth;
        this.usuario = usuario;
        this.contrasena = contrasena;
        try
        {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.starttls.enable", starttls);
            props.setProperty("mail.smtp.port", port);
            props.setProperty("mail.smtp.user", usuario);
            props.setProperty("mail.smtp.auth", auth);

            // Preparamos la sesion
            session = Session.getDefaultInstance(props);
            session.setDebug(true);// Para debugear en caso de que de error

            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getStarttls() {
        return starttls;
    }

    public void setStarttls(String starttls) {
        this.starttls = starttls;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    
    public ControladorCorreo()
    {
        try
        {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "jshop.ecommerce@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            session = Session.getDefaultInstance(props);
            session.setDebug(true);// Para debugear en caso de que de error

            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void enviarActivacion(String email,String codActivacion) throws MessagingException{
        // Construimos el mensaje
        
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(usuario));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress(email));
            message.setSubject("Activación de cuenta JShoP");
            Transport protocolo = session.getTransport("smtp");
            protocolo.connect(usuario, contrasena);
            String mensaje="<p> Por favor active su cuenta haciendo click en el siguiete vinculo</p>"
                    + "<a href=\"http://localhost:8081/JShoP/activar?"
                    + codActivacion+"\">Activar cuenta</a>";
            message.setContent(mensaje, "text/html; charset=utf-8");
            
            protocolo.sendMessage(message, message.getAllRecipients());
            
            // Cierre.
          
            protocolo.close();
    }
}
