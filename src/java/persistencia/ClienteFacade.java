/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import controlador.ControladorCorreo;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Cliente;
import modelo.Lugar;

/**
 *
 * @author Jesus
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;
    private int sequenceCliente=0;
    private String currval="select currval('seq_cliente')";
    private String nextval="select nextval('seq_cliente')";
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }
    public Cliente buscarPorCorreo(String correoElectronico) {
        Query q = em.createNamedQuery("Cliente.buscarPorCorreo")
                .setParameter("correoElectronico", correoElectronico);
        int idCliente = 0;
        try {
            idCliente = Integer.parseInt(q.getSingleResult().toString());
            if (idCliente != 0) {
                Cliente cliente = find(idCliente);
                return cliente;
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    public int validarCorreo(String correoElectronico) {
        Query q = em.createNamedQuery("Cliente.buscarPorCorreo")
                .setParameter("correoElectronico", correoElectronico);
        System.out.println("validarCorreo");
        int idCliente = -1;
        try {
            idCliente = Integer.parseInt(q.getSingleResult().toString());
            if (idCliente != -1) {
                Cliente cliente = find(idCliente);
                    return idCliente;
            } else {
                return -1;
            }
        } catch (NoResultException e) {
            return -1;
        }
    }

    public boolean registrarCliente(String nombreCliente, String apellidoCliente, String fechaNacimiento, int nroCedula, String correoElectronico, String contrasena, String direccionCliente, int codPostal, Lugar fkLugar) {
        Cliente nuevoCliente = new Cliente();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        nuevoCliente.setIdCliente(sequenceVal());
        nuevoCliente.setNombreCliente(nombreCliente);
        nuevoCliente.setApellidoCliente(apellidoCliente);
        try {
            nuevoCliente.setFechaNacimiento(f.parse(fechaNacimiento));
            nuevoCliente.setFechaRegistro(new Date());
        } catch (ParseException ex) {
            Logger.getLogger(ClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        nuevoCliente.setNroCedula(nroCedula);
        nuevoCliente.setCorreoElectronico(correoElectronico);
        nuevoCliente.setContrasena(contrasena);
        nuevoCliente.setDireccionCliente(direccionCliente);
        nuevoCliente.setCodPostal(codPostal);
        nuevoCliente.setFkLugar(fkLugar);
        nuevoCliente.setStatusCliente("N");
        nuevoCliente.setCodActivacion(findMD5(correoElectronico));

        create(nuevoCliente);
        try {
            ControladorCorreo mail =
                    new ControladorCorreo("smtp.gmail.com", "true", "587", "true", "jshop.ecommerce@gmail.com", "jshop1234");

            mail.enviarActivacion(correoElectronico, findMD5(correoElectronico));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public void enviarCodActivacion(String correoElectronico){
        ControladorCorreo mail =
                    new ControladorCorreo("smtp.gmail.com", "true", "587", "true", "jshop.ecommerce@gmail.com", "jshop1234");
        try {
            mail.enviarActivacion(correoElectronico, findMD5(correoElectronico));
        } catch (MessagingException ex) {
            Logger.getLogger(ClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int activateAccount(String codActivacion) {
        try {
            Query q = em.createNamedQuery("Cliente.activarCuenta")
                    .setParameter("codActivacion", codActivacion);
            int idCliente = -1;
            idCliente = Integer.parseInt(q.getSingleResult().toString());

            if (idCliente != -1) {
                Cliente cliente = find(idCliente);
                cliente.setStatusCliente("Activo");
                cliente.setCodActivacion(null);
                em.persist(cliente);
                return idCliente;
            } else {
                return -1;
            }
        } catch (NoResultException e) {
            return -1;
        }
    }

    public int validarLogin(String correoElectronico, String contrasena) {
        try {
            Query q = em.createNamedQuery("Cliente.validarLogin")
                    .setParameter("correoElectronico", correoElectronico)
                    .setParameter("contrasena", contrasena);
            int idCliente = 0;
            idCliente = Integer.parseInt(q.getSingleResult().toString());

            if (idCliente != 0) {
                return idCliente;
            } else {
                return 0;
            }
        } catch (NoResultException e) {
            return 0;
        }
    }

    private String findMD5(String arg) {

        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        byte[] defaultBytes = arg.getBytes();
        algorithm.reset();
        algorithm.update(defaultBytes);
        byte messageDigest[] = algorithm.digest();
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < messageDigest.length; i++) {
            String hex = Integer.toHexString(0xff & messageDigest[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
