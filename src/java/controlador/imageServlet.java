/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import auxiliar.DireccionServidor;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mario
 */
@WebServlet(name = "imageServlet", urlPatterns = {"/imagenProducto"})
public class imageServlet extends HttpServlet {

    String image_name = "";
    ResourceBundle props = null;
    String filePath = "";
    private static final int BUFSIZE = 100;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        String dir = DireccionServidor.getInstance().getImgPath();
        if (userPath.equals("/imagenProducto")) {
            String id = request.getParameter("id");
            this.filePath = dir + id + ".jpg";
            sendImage(getServletContext(), request, response);

        }

    }

    private void doDownload(String filePath, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        File fileName = new File(filePath);
        int length = 0;
        ServletOutputStream outputStream = response.getOutputStream();
        ServletContext context = getServletConfig().getServletContext();
//        ServletContext context = getServletContext();
        String mimetype = context.getMimeType(filePath);
        response.setContentType((mimetype != null) ? mimetype
                : "application/octet-stream");
        response.setContentLength((int) fileName.length());
        response.setHeader("Content-Disposition", "attachment; filename=\""
                + image_name + "\"");
        byte[] bbuf = new byte[BUFSIZE];
        DataInputStream in = new DataInputStream(new FileInputStream(fileName));
        while ((in != null) && ((length = in.read(bbuf)) != -1)) {
            outputStream.write(bbuf, 0, length);
        }
        in.close();
        outputStream.flush();
        outputStream.close();
    }

    public void sendImage(ServletContext servletContext,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reqUrl = request.getRequestURL().toString();
        StringTokenizer tokens = new StringTokenizer(reqUrl, "/");
        int noOfTokens = tokens.countTokens();
        String tokensString[] = new String[noOfTokens];
        int count = 0;
        while (tokens.hasMoreElements()) {
            tokensString[count++] = (String) tokens.nextToken();
        }

        String fullFilePath = filePath;
//System.out.println("FULL PATH :: "+fullFilePath);
        doShowImageOnPage(fullFilePath, request, response);
//doDownload(fullFilePath, request, response);
    }

    private void doShowImageOnPage(String fullFilePath,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "inline");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "0");
        response.setContentType("image/jpg");
        byte[] image = getImage(fullFilePath);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(image);
        outputStream.close();
    }

    private byte[] getImage(String filename) {
        byte[] result = null;
        String fileLocation = filename;
        File f = new File(fileLocation);
        result = new byte[(int) f.length()];
        try {
            FileInputStream in = new FileInputStream(fileLocation);
            in.read(result);
        } catch (Exception ex) {
            System.out.println("GET IMAGE PROBLEM :: " + ex);
            ex.printStackTrace();
        }
        return result;
    }
}
