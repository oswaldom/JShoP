/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.ejb.embeddable.EJBContainer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mario
 */
public class ClienteFacadeTest {
    
    EJBContainer container;
    ClienteFacade instance;
    public ClienteFacadeTest() {
    }
    
    
   @Before
    public void setUp() throws Exception {
        System.out.println("=====Iniciando pruebas de la clase ClienteFacade=====");
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        instance = (ClienteFacade)container.getContext().lookup("java:global/classes/ClienteFacade");
    }
    

    /**
     * Test del metodo validarLogin
     */
    @Test
    public void testValidarLogin() {
        System.out.println("validarLogin");
        String correoElectronico = "omsxx11@gmail.com";
        String contrasena = "5565";
        int expResult = 1;
        int result = instance.validarLogin(correoElectronico, contrasena);
        assertEquals(expResult, result);
        container.close();
    }

    /**
     * Test del metodo calificarProducto
     */
    

    /**
     * Test del metodo puedeCalificar
     */
    @Test
    public void testPuedeCalificar(){
        System.out.println("puedeCalificar");
        int idCliente = 1;
        int idProducto = 1;
        boolean expResult = true;
        boolean result = instance.puedeCalificar(idCliente, idProducto);
        assertEquals(expResult, result);
        container.close();
    }

    /**
     * Test del metodo haComprado
     */
    @Test
    public void testHaComprado(){
        System.out.println("haComprado");
        int idCliente = 1;
        int idProducto = 1;
        boolean expResult = true;
        boolean result = instance.haComprado(idCliente, idProducto);
        assertEquals(expResult, result);
        container.close();
    }

    /**
     * Test del metodo haCompradoProducto
     */
    @Test
    public void testHaCompradoProducto(){
        System.out.println("haCompradoProducto");
        int idCliente = 1;
        int idProducto = 1;
        boolean expResult = true;
        boolean result = instance.haCompradoProducto(idCliente, idProducto);
        assertEquals(expResult, result);
        container.close();
    }

    /**
     * Test del metodo haRecibidoOrdenProducto
     */
    @Test
    public void testHaRecibidoOrdenProducto(){
        System.out.println("haRecibidoOrdenProducto");
        int idCliente = 1;
        int idProducto = 1;
        boolean expResult = true;
        boolean result = instance.haRecibidoOrdenProducto(idCliente, idProducto);
        assertEquals(expResult, result);
        container.close();
    }

    /**
     * Test del metodo haCalificadoProducto
     */
    @Test
    public void testHaCalificadoProducto(){
        System.out.println("haCalificadoProducto");
        int idCliente = 1;
        int idProducto = 1;
        boolean expResult = true;
        boolean result = instance.haCalificadoProducto(idCliente, idProducto);
        assertEquals(expResult, result);
        container.close();
    }
    
    @Test
    public void testCalificarProducto(){
        System.out.println("calificarProducto");
        String correoCliente = "omsxx11@gmail.com";
        int idProducto = 1;
        int rating = 5;
        String textoReview = "prueba";
        boolean expResult = true;
        boolean result = instance.calificarProducto(correoCliente, idProducto, rating, textoReview);
        assertEquals(expResult, result);
        container.close();
    }
}