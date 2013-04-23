/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Orden;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import persistencia.OrdenFacade;

/**
 *
 * @author Jesus
 */
@WebServlet(name = "GenerarPDF", urlPatterns = {"/factura"})
public class GenerarPDF extends HttpServlet {

    @EJB
    OrdenFacade ordenFacade;

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
            if (idOrden!=null){
            Orden orden = ordenFacade.find(Integer.parseInt(idOrden));
            }
            response.setContentType("application/pdf"); // Code 1
            Document document = new Document();
            File fichero = new File((System.getProperty("user.dir")+"/web/img/logo.png"));
		String formato = "png";
                Image img = null;
            try {
                img = Image.getInstance("http://localhost:8081/JShoP/img/logo.png");
            } catch (BadElementException ex) {
                Logger.getLogger(GenerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GenerarPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            try {
                PdfWriter.getInstance(document,
                        response.getOutputStream()); // Code 2
                document.open();
                PdfPCell cell = new PdfPCell();
                cell.setBorder(0);
                cell.setFixedHeight(50);
                cell.addElement(img);
                File out = QRCode.from("hola").to(
				ImageType.PNG).withSize(150, 150).file();
                System.out.println(out.getAbsoluteFile());
                img = Image.getInstance(out.getAbsoluteFile().toString());
//                document.add(img);
                // Code 3
                PdfPTable table = new PdfPTable(3);
                table.addCell(cell);
                 cell = new PdfPCell();
                cell.setBorder(0);
                cell.setFixedHeight(100);
                cell.addElement(img);
                PdfPCell textCell = new PdfPCell(); 
                textCell.setHorizontalAlignment(1);
                textCell.setBorder(0);
                textCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(textCell);
                table.addCell("JShop e-Commerce C.A.\n RIF: J-931002214");
                table.addCell("3");
                table.addCell("4");
                table.addCell("5");
                table.addCell("6");
//                PdfPCell addCell = table.addCell(cell);
                table.addCell("7");
                table.addCell(cell);
                
                // Code 4
                document.add(table);
                document.add(table);
                document.addCreator("JShoP e-Commerce C.A.");
                document.addAuthor("JShoP e-Commerce C.A.");
                document.addTitle("Factura de pedido #");
                document.addSubject("Copia de factura");
                document.addCreationDate();
                document.close();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition",
                        " attachment; filename=\"example.pdf\"");
            } catch (DocumentException e) {
            }
        }

    }
}
