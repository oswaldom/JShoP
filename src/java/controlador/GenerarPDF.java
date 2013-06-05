/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import auxiliar.DireccionServidor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Orden;
import modelo.OrdenProducto;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import persistencia.LugarFacade;
import persistencia.OrdenFacade;

/**
 *
 * @author Jesus
 */
@WebServlet(name = "GenerarPDF", urlPatterns = {"/factura"})
public class GenerarPDF extends HttpServlet {
    
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font subFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static Font textBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static Font textFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    @EJB
    private LugarFacade lugarFacade;
    @EJB
    private OrdenFacade ordenFacade;
    private String urlQR="";
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = request.getServletPath();
        System.out.println(userPath);
        if (userPath.equals("/factura")) {
            String idOrden = request.getQueryString();
            Orden orden = null;
            if (idOrden != null) {
                orden = ordenFacade.find(Integer.parseInt(idOrden));
            }
            response.setContentType("application/pdf"); // Code 1
            Document documento = new Document();
                urlQR=DireccionServidor.getInstance().getUrlServidor()+"/JShoP/verOrden?"+idOrden;
            
            try {
                PdfWriter.getInstance(documento,
                        response.getOutputStream()); // Code 2
                documento.open();
                Paragraph ParrafoHoja = new Paragraph();
                
                crearTabla(ParrafoHoja, orden);
                documento.add(ParrafoHoja);
                documento.addCreator("JShoP e-Commerce C.A.");
                documento.addAuthor("JShoP e-Commerce C.A.");
                documento.addTitle("Factura de pedido #");
                documento.addSubject("Copia de factura");
                documento.addCreationDate();
                documento.close();


                // Code 4
                
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition",
                        " attachment; filename=\"example.pdf\"");
            } catch (DocumentException e) {
            }
        }
        
    }
    
    private void crearTabla(Paragraph subCatPart,Orden orden ) throws BadElementException, IOException {
        List<OrdenProducto> listaProductos = orden.getOrdenProductoList();
        PdfPTable tabla = new PdfPTable(3);		//Numero de columnas
        
        Image img = null;
        try {
            img = Image.getInstance("http://localhost"+DireccionServidor.getInstance().getPuertoServidor()+"/JShoP/img/logopdf.png");
        } catch (BadElementException ex) {
            Logger.getLogger(GenerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(GenerarPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        double total = 0;
        tabla.setWidthPercentage(100); // Porcentaje de la pagina que ocupa
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);//Alineacion horizontal
        PdfPCell cell = new PdfPCell();
        cell = new PdfPCell();
        cell.addElement(img);
        cell.setBorder(0);
        tabla.addCell(cell);
        PdfPCell textCell = new PdfPCell(new Phrase("JShop e-Commerce C.A.\n RIF: J-931002214", subFont));        
        
        textCell.setHorizontalAlignment(1);
        textCell.setBorder(0);
        textCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        textCell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        textCell.setColspan(1);
        tabla.addCell(textCell);
        
        File out = QRCode.from(urlQR).to(
                ImageType.PNG).withSize(150, 150).file();
        System.out.println(out.getAbsoluteFile());
        img = Image.getInstance(out.getAbsoluteFile().toString());
        cell = new PdfPCell();
        cell.setBorder(0);
        cell.setFixedHeight(100);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.addElement(img);
        
        tabla.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(0);
        
        
        datosCliente(tabla, orden);
        cell = new PdfPCell(new Paragraph("Sumario de compra", catFont));
        cell.setColspan(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingTop(20);
        cell.setPaddingBottom(20);
        cell.setBorder(0);
        tabla.addCell(cell);
        PdfPCell cellProducto = new PdfPCell(new Paragraph("Producto", subFont));
        cellProducto.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cellCantidad = new PdfPCell(new Paragraph("Cantidad", subFont));
        cellCantidad.hasFixedHeight();
        cellCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cellPrecio = new PdfPCell(new Paragraph("Precio", subFont));
        cellPrecio.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cellTotal = new PdfPCell(new Paragraph("Total:", subFont));
        cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cellProducto);
        tabla.addCell(cellCantidad);
        tabla.addCell(cellPrecio);        
        
        for (int i = 0; i < listaProductos.size(); i++) {
            PdfPCell nombreProducto = new PdfPCell(new Paragraph(listaProductos.get(i).getProducto().getNombreProducto()));
            double precio = listaProductos.get(i).getPrecio();
            nombreProducto.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cantidadProducto = new PdfPCell(new Paragraph("" + listaProductos.get(i).getCantidad()));            
            cantidadProducto.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell precioProducto = new PdfPCell(new Paragraph("" + precio));            
            precioProducto.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tabla.addCell(nombreProducto);
            tabla.addCell(cantidadProducto);
            tabla.addCell(precioProducto);
            total = total + listaProductos.get(i).getCantidad() * listaProductos.get(i).getPrecio();
        }
        
        PdfPCell cellNumberTotal = new PdfPCell(new Paragraph("" + total));
        cellNumberTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell = new PdfPCell();
        cell.setBorder(0);
        tabla.addCell(cell);
        tabla.addCell(cellTotal);
        tabla.addCell(cellNumberTotal);
        subCatPart.add(tabla);
    }

    private void datosCliente(PdfPTable tabla, Orden orden) {
        anadirDatos(tabla, true, "Cliente:");
        anadirDatos(tabla, false, orden.getFkCliente().getNombreCliente() + " " + orden.getFkCliente().getApellidoCliente());
        anadirDatos(tabla, true, "Documento de identidad:");
        anadirDatos(tabla, false, orden.getFkCliente().getNroCedula() + "");
        anadirDatos(tabla, true, "Dirección de residencia:");
        anadirDatos(tabla, false, orden.getDireccionEntrega() + "\n" + lugarFacade.obtenerUbicacion(orden.getFkLugar()));
        anadirDatos(tabla, true, "Número de confirmación:");
        anadirDatos(tabla, false, orden.getNroConfirmacion() + "");
        anadirDatos(tabla, true, "Fecha de pedido:");
        anadirDatos(tabla, false, orden.getFechaCreacionOrden().toString());
        anadirDatos(tabla, true, "Dirección de entrega:");
        anadirDatos(tabla, false, orden.getFkCliente().getDireccionCliente() + "\n" + lugarFacade.obtenerUbicacion(orden.getFkCliente().getFkLugar()));
    }

    private void anadirDatos(PdfPTable tabla, boolean titulo, String contenido) {
        PdfPCell textCell;
        if (titulo) {
            textCell = new PdfPCell(new Phrase(contenido, textBold));            
            textCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            textCell.setColspan(1);
        } else {
            
            textCell = new PdfPCell(new Phrase(contenido, textFont));
            textCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            textCell.setColspan(2);
        }
        
        textCell.setBorder(0);
        tabla.addCell(textCell);
        
    }
}
