package controlador;

import auxiliar.DireccionServidor;
import auxiliar.DocXml;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import modelo.Orden;
import persistencia.OrdenFacade;

@WebServlet(name = "XmlServlet", urlPatterns = {"/admin/ServletXML"})
public class XmlServlet extends HttpServlet{
    private String rutaTemp;
    @EJB
    private OrdenFacade ordenFacade;
  public void doGet(HttpServletRequest request, HttpServletResponse response)
                                   throws ServletException,IOException{
    String userPath = request.getServletPath();
      System.out.println(userPath);
if (userPath.equals("/admin/ServletXML")) {
            String numeroOrden =request.getParameter("numeroOrden");
            String statusOrden =request.getParameter("statusSelect");
            Orden orden=ordenFacade.find(Integer.parseInt(numeroOrden));
            orden.setStatusOrden(statusOrden);
            if (statusOrden.equals("Entregada")){
            orden.setFechaEntregada(new Date());
            }
            DireccionServidor.getInstance().setPuertoServidor(request.getRequestURL().toString());
            ordenFacade.actualizarStatusOrden(orden);
            DocXml doc= new DocXml();
            String ruta=doc.archivoXML(numeroOrden,statusOrden);
            String nextJSP = "/index.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            response.getOutputStream();
      ServletOutputStream stream = null;
      BufferedInputStream buf = null;
     try{
     
     stream = response.getOutputStream();
     File xml = new File(ruta+"prueba.xml");
     
      response.setContentType("text/xml");
      
      response.addHeader(
        "Content-Disposition","attachment; filename="+"controlOrden"+numeroOrden );

      response.setContentLength( (int) xml.length() );
      
     FileInputStream input = new FileInputStream(xml);
     buf = new BufferedInputStream(input);
     int readBytes = 0;

     //read from the file; write to the ServletOutputStream
     while((readBytes = buf.read()) != -1)
        stream.write(readBytes);

     } catch (IOException ioe){
     
        throw new ServletException(ioe.getMessage());
         
     } finally {
     
     if(stream != null)
         stream.close();
      if(buf != null)
          buf.close();
          }
            //dispatcher.forward(request,response);
           
            
        }
     
  }

}