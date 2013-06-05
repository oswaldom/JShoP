package controlador;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author Jesus
 */
@WebServlet(urlPatterns = {"/qr"})
public class ServletQRCodigo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        String urlQR = "";
        System.out.println(System.getProperty("user.dir"));
        if (userPath.equals("/qr")) {
            System.out.println(request.getRequestURL());
            String qrtext = request.getParameter("qrtext");
            ByteArrayOutputStream out = QRCode.from(qrtext).to(
                    ImageType.PNG).stream();
            //ByteArrayOutputStream out = QRCode.from(qrtext).to(
            //      ImageType.PNG).stream();

            response.setContentType("image/png");
            response.setContentLength(out.size());

            OutputStream outStream = response.getOutputStream();

            outStream.write(out.toByteArray());

            outStream.flush();
            outStream.close();


        }

    }
}
