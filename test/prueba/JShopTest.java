package prueba;
import junit.framework.Assert;
import org.junit.Test;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;
import modelo.Categoria;
import modelo.Producto;
import org.junit.After;
import org.junit.Before;
 
/**
testing rest web services 
 @author vit*/

public class JShopTest {
  
   private static WebResource webResProducto;
   private static WebResource webResCategoria;
   private static String result;  
   private static String pagina;
   private static int valorEsperado;
   private static WebResource.Builder clientReqBuilder;

   @Before
    public void inicializacion() {
   
      webResProducto = Client.create().resource("http://localhost:8080/JShoP/ApiJShoP/producto");
      webResCategoria = Client.create().resource("http://localhost:8080/JShoP/ApiJShoP/categoria");
      result="";
      
    }
   
 
@Test
    public void testCountPaginasProductos() {
        
        result = webResProducto.path("/count").get(String.class);
        valorEsperado = 16;
        try{
        Assert.assertEquals(Integer.parseInt(result), valorEsperado);
        System.out.println("Cantidad de paginas de productos: " + result);
        }
        catch (Throwable businessException){
            Assert.fail("el resultado esperado es "+valorEsperado + " y el resultado obtenido fue "+result);
        }
        
    }   
@Test
    public void testCountPaginasCategorias() {
      
        result = webResCategoria.path("/count").get(String.class);
        valorEsperado = 2;
        
        try{ 
        Assert.assertEquals(Integer.parseInt(result), valorEsperado);
        System.out.println("Cantidad de paginas de categorias: " + result);
        }
        catch (Throwable businessException){
            Assert.fail("el resultado esperado es "+valorEsperado + " y el resultado obtenido fue "+result);
        }
}
@Test
public void testCountPaginasProductosPorCategorias() {

        result = webResCategoria.path("/nombre/Camisa/producto/all/count").get(String.class);
        valorEsperado = 2;
        try{
       
            Assert.assertEquals("prueba exitosaaaa",Integer.parseInt(result), valorEsperado);
            System.out.println("Cantidad de paginas de producto de la categoria Camisa es: " + result);
        
        }catch (Throwable businessException){
            Assert.fail("el resultado esperado es "+valorEsperado + " y el resultado obtenido fue "+result);
        }
}

@Test
public void testBuscarProductoPorCategoria() {
        
        try {
           pagina = "1";
           String nombre ="Camisa";
           clientReqBuilder = webResCategoria.path("nombre/"+nombre+"/producto/all/"+pagina).
                   accept(MediaType.APPLICATION_XML);
           
           result = clientReqBuilder.get(String.class);
           Assert.assertNotNull(result);
           System.out.println("resultado de la prueba Buscar Producto por Categoria es "+result);
        
        }catch (Throwable businessException) {
            
            Assert.fail(businessException.getMessage());
        }
}


@Test
    public void testBuscarProductos() {
 
          try {
            pagina ="1";
            clientReqBuilder = webResProducto.path("/todos/"+pagina).accept(MediaType.APPLICATION_XML);
            result = clientReqBuilder.get(String.class);
            Assert.assertNotNull(result);
            System.out.println("el resultado de la prueba buscar productos " + result);
            
          } catch (Throwable businessException) { 
            
              Assert.fail(businessException.getMessage());
           }
}

@Test 
    public void testBuscarProductoPorNombre() {
        
        try {
           pagina = "1";
           String nombreProducto ="ToBeInStyle Womens Summer Fashion Lace Trim Camisole";
           clientReqBuilder = webResProducto.path("/nombre/"+nombreProducto+"/"+pagina).
                   accept(MediaType.APPLICATION_XML);
           
           result = clientReqBuilder.get(String.class);
           
           Assert.assertNotNull(result);
           System.out.println("resultado de la prueba Buscar Producto por nombre es "+result);
        
        }catch (Throwable businessException ) {
      
            Assert.fail(businessException.getMessage());
        }
}


@Test 
    public void testBuscarCategoriaPorNombre() {
        
        try {
           pagina = "1";
           String nombreCategoria ="Camisa";
           clientReqBuilder = webResCategoria.path("/nombre/"+nombreCategoria+"/"+pagina).
                   accept(MediaType.APPLICATION_XML);
           
           result = clientReqBuilder.get(String.class);
           
           Assert.assertNotNull(result);
           System.out.println("resultado de la prueba Buscar Categoria por nombre es "+result);
        
        }catch (Throwable businessException ) {
      
            Assert.fail(businessException.getMessage());
        }
}
@Test
    public void testBuscarProductoPorNombreYcategoria() {
        
        try {
           pagina = "1";
           String nombreCategoria ="Camisa";
           String nombreProducto = "Nautica";
           clientReqBuilder = webResCategoria.path("/nombre/"+nombreCategoria+"/producto/nombre/"+nombreProducto).
                   accept(MediaType.APPLICATION_XML);
           
           result = clientReqBuilder.get(String.class);
           
           Assert.assertNotNull(result);
           System.out.println("resultado de la prueba Buscar producto por nombre y categoria es "+result);
        
        }catch (Throwable businessException ) {
      
            Assert.fail(businessException.getMessage());
        }
}


@Test
    public void tesBuscarProductoPorID() {
        try {
           Producto producto = webResProducto.path("/1").get(Producto.class);
           Assert.assertNotNull("Producto devuelto", producto);
           System.out.println("Resultado de la prueba Buscar Producto por Id es el producto "+producto.getNombreProducto());
        } catch(Throwable businessException){
           Assert.fail("id de producto no valido");
        }
}
@Test
    public void tesBuscarCategoriaPorID() {
        try {
           Categoria categoria = webResCategoria.path("/4").get(Categoria.class);
           Assert.assertNotNull("Producto devuelto", categoria);
           System.out.println("Resultado de la prueba Buscar Categoria por Id es la categoria "+ categoria.getNombreCategoria());
        } catch(Throwable businessException){
           Assert.fail("id de Categoria no valido");
        }
}


@After
    public void tearDown() 
    {
      webResProducto = null;
      webResCategoria = null;
      result = "";
      valorEsperado = 0;
      pagina = null;
     }


}