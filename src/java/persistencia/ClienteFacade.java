/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import controlador.ControladorCorreo;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Cliente;

/**
 *
 * @author Jesus
 */
@Stateless
public class ClienteFacade extends AbstractFacade<Cliente> {
    @PersistenceContext(unitName = "JShoPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }
    public int validarCorreo(String correoElectronico){
        Query q=em.createNamedQuery("Cliente.buscarPorCorreo")
                                      .setParameter("correoElectronico", correoElectronico);
        System.out.println("validarCorreo");
        int idCliente = 0;
        try{
                idCliente =Integer.parseInt(q.getSingleResult().toString());
                if (idCliente!=0){
                    Cliente cliente= find(idCliente);
                    if (cliente.getStatusCliente().equals("Activo")){
                    return idCliente;
                    } else {
                       return 2; 
                    }
                }else{
                    return 0;
                }
                } catch (NoResultException e) {
            return 0;
        }
    }
    public boolean registrarCliente(String nombreCliente, String apellidoCliente, Date fechaNacimiento, int nroCedula, String correoElectronico, String contrasena, String direccionCliente, int codPostal) {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombreCliente(nombreCliente);
        nuevoCliente.setApellidoCliente(apellidoCliente);
        nuevoCliente.setFechaNacimiento(fechaNacimiento);
        nuevoCliente.setNroCedula(nroCedula);
        nuevoCliente.setCorreoElectronico(correoElectronico);
        nuevoCliente.setContrasena(contrasena);
        nuevoCliente.setDireccionCliente(direccionCliente);
        nuevoCliente.setCodPostal(codPostal);
        nuevoCliente.setStatusCliente("N");
        nuevoCliente.setCodActivacion(findMD5(correoElectronico));
        try {
            ControladorCorreo mail=
                    new ControladorCorreo("smtp.gmail.com","true","587","true","jshop.ecommerce@gmail.com","jshop1234");
            
            mail.enviarActivacion(correoElectronico, findMD5(correoElectronico));
            
        } catch(Exception e) {            
            e.printStackTrace();
        }
        em.persist(nuevoCliente);
        return true;
    }
    public int activateAccount(String codActivacion){
        try {
                 Query q=em.createNamedQuery("Cliente.activarCuenta")
                                      .setParameter("codActivacion", codActivacion);
                 int idCliente=0;
                 idCliente=Integer.parseInt(q.getSingleResult().toString());
             
             if (idCliente!=0){
                 Cliente cliente= find(idCliente);
                 cliente.setStatusCliente("Y");
                 cliente.setCodActivacion(null);
                 em.persist(cliente);
                return idCliente;
             }
             else {
                 return 0;
             }
        } catch (NoResultException e) {
            return 0;
        }
    }
    public int validarLogin(String correoElectronico, String contrasena) {
        try {
                 Query q=em.createNamedQuery("Cliente.validarLogin")
                                      .setParameter("correoElectronico", correoElectronico)
                                      .setParameter("contrasena", contrasena);
                 int customerID=0;
                 customerID=Integer.parseInt(q.getSingleResult().toString());
             
             if (customerID!=0){
                return customerID;
             }
             else {
                 return 0;
             }
        } catch (NoResultException e) {
            return 0;
        }
    }
        private  String findMD5(String arg) {

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
