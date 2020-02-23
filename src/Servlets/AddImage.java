package Servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@WebServlet("/AddImage")
@MultipartConfig(fileSizeThreshold=1024*1024, 
maxFileSize=20000000, maxRequestSize=1024*1024*5*5)
public class AddImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "uploadFiles";
	private String fileName;
       

    public AddImage() {
        super();
       
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
    
        	
		//String savePath = new File("").getAbsolutePath() + "/uploadfiles";
		String savePath = System.getProperty( "catalina.base" ) +  "/uploadfiles" ;
        System.out.println(savePath);
         
        // Tworzenie folderu gdyby nie istnia³
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
         
        for (Part part : request.getParts()) {
            fileName = extractFileName(part);
            // Poprawa nazwy pliku
            fileName = new File(fileName).getName();
            part.write(savePath + File.separator + fileName);
        }
	}
      
    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}

