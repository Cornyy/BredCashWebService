package Servlets;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import DbConnection.DbConnection;


@WebServlet("/AddAuction")
public class AddAuction extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddAuction() {
        super();

    }
    
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
			String title = request.getParameter("title");
     		String description = request.getParameter("description");
     		String auth_name = request.getParameter("auth_name");
     		String end_date = request.getParameter("end_date"); // FORMAT DATY : YYYY-MM-DD
     		String image_name = request.getParameter("image_name");
     		String price = request.getParameter("price");
             // Wrzucanie Aukcji do bazy
             DbConnection Con = new DbConnection();
             try 
             {
     			Con.addAuction(title, description,Integer.parseInt(price), image_name, auth_name,end_date);
     		} 
             catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) 
             {
     			e.printStackTrace();
     		}
    }

    
		
}

