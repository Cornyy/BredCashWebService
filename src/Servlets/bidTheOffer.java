package Servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DbConnection.DbConnection;


@WebServlet("/bidTheOffer")
public class bidTheOffer extends HttpServlet {
	private static final long serialVersionUID = 1L;




	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String auction_id = request.getParameter("auction_id").toString();
		String user = request.getParameter("nick").toString();
		int amount = Integer.parseInt(request.getParameter("amount").toString());
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		
		DbConnection con = new DbConnection();
		try {
			String message = con.bidTheOffer(auction_id, user, amount);
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			ServletOutputStream out = response.getOutputStream();
			
			
			if(message.equals("You succesfully bidded the offer!"))
			{
				out.print("true,"+message);				
				System.out.println(formatter.format(date) + " user " + user + " has bidded offer, id = "+auction_id+"");
			}
			else
			{
				out.print("false,"+message);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		
	}

	

}
