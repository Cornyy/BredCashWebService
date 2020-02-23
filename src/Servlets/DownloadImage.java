package Servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/DownloadImage")
public class DownloadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DownloadImage() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String appPath = request.getServletContext().getRealPath("");
		String savePath = System.getProperty( "catalina.base" ) +  "/uploadfiles" ;
		String SAVE_DIR = "uploadFiles";
		String imageName = request.getParameter("imageName");
		String filePath =  savePath +File.separator+ imageName;
		System.out.println(filePath);
		
		
		response.setContentType("image/png");
		File f = new File(filePath);
		BufferedImage bi = ImageIO.read(f);
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
	
	}

}
