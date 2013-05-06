/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import auxiliar.Encriptacion;
import auxiliar.FormatoFecha;
import controlador.ControladorCorreo;
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }
    
    public Cliente buscarPorCorreo(String correoElectronico) {
        Query q = em.createNamedQuery("Cliente.buscarPorCorreoElectronico")
                .setParameter("correoElectronico", correoElectronico);
       Cliente cliente;
               
        try {
             cliente= (Cliente) q.getSingleResult();
            if (cliente!=null) {
                return cliente;
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Cliente validarCorreo(String correoElectronico) {
        Query q = em.createNamedQuery("Cliente.buscarPorCorreoElectronico")
                .setParameter("correoElectronico", correoElectronico);
        Cliente cliente=null;
        try {
            cliente=(Cliente) q.getSingleResult();
            return cliente;
        } catch (NoResultException e) {
            return cliente;
        }
    }
    
    public boolean registrarCliente(String nombreCliente, String apellidoCliente, String fechaNacimiento, int nroCedula, String correoElectronico, String contrasena, String direccionCliente, int codPostal, Lugar fkLugar) {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setIdCliente(sequenceVal());
        nuevoCliente.setNombreCliente(nombreCliente);
        nuevoCliente.setApellidoCliente(apellidoCliente);
        nuevoCliente.setFechaRegistro(new Date());
        nuevoCliente.setFechaNacimiento(FormatoFecha.stringDate("dd/MM/yyyy", fechaNacimiento));
        nuevoCliente.setNroCedula(nroCedula);
        nuevoCliente.setCorreoElectronico(correoElectronico);
        nuevoCliente.setContrasena(contrasena);
        nuevoCliente.setDireccionCliente(direccionCliente);
        nuevoCliente.setCodPostal(codPostal);
        nuevoCliente.setFkLugar(fkLugar);
        nuevoCliente.setStatusCliente("N");
        nuevoCliente.setCodActivacion(Encriptacion.findMD5(correoElectronico));

        create(nuevoCliente);
        try {
           enviarCodActivacion(correoElectronico);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public void enviarCodActivacion(String correoElectronico){
        try {
            ControladorCorreo.getInstance().enviarActivacion(correoElectronico, Encriptacion.findMD5(correoElectronico));
        } catch (MessagingException ex) {
            Logger.getLogger(ClienteFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int activateAccount(String codActivacion) {
        try {
            Query q = em.createNamedQuery("Cliente.buscarPorCodActivacion")
                    .setParameter("codActivacion", codActivacion);
            int idCliente = -1;
            idCliente = Integer.parseInt(q.getSingleResult().toString());

            if (idCliente != -1) {
                Cliente cliente = find(idCliente);
                cliente.setStatusCliente("Activo");
                cliente.setCodActivacion(null);
                edit(cliente);
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
            int idCliente=-1;
            idCliente = Integer.parseInt(q.getSingleResult().toString());

            if (idCliente != -1) {
                return idCliente;
            } else {
                return -1;
            }
        } catch (NoResultException e) {
            return -1;
        }
    }

}
