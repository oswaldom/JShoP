package controlador;

import auxiliar.DireccionServidor;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * 
 *
 * @author Luify
 *
 */
public class ControladorCorreo {

    private Session session;
    private String usuario;
    private String contrasena;
    private static ControladorCorreo instancia = null;

    public static ControladorCorreo getInstance() {
        createInstance();
        return instancia;
    }

    private static void createInstance() {
        synchronized (ControladorCorreo.class) {
            if (instancia == null) {
                instancia = new ControladorCorreo();
            }
        }
    }
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private ControladorCorreo() {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            usuario = "jshop.ecommerce@gmail.com";
            Session session1 = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("jshop.ecommerce@gmail.com", "jshop1234");
				}
			});
            session=session1;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarActivacion(String email, String codActivacion) throws MessagingException {
        
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Activación de cuenta JShoP");
            String mensaje = "<p> Por favor active su cuenta haciendo click en el siguiete vinculo</p>"
                    + "<a href=\"" + DireccionServidor.getInstance().getUrlServidor() + "/JShoP/activar?"
                    + codActivacion + "\">Activar cuenta</a>";
            message.setContent(mensaje, "text/html; charset=utf-8");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void enviarInfoModificaciones(String email, String mensaje) throws MessagingException {
       try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Modificaciones en su cuenta");
            message.setContent(mensaje, "text/html; charset=utf-8");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
