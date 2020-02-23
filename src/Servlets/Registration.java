package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import Objects.user;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DbConnection.DbConnection;

@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	user user;
	DbConnection con;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String nick = request.getParameter("nick");
		String password = request.getParameter("password");
		Date date = new Date();
		
		ServletOutputStream out = response.getOutputStream();
		
		user = new user(nick,password,0,0,date);
		con = new DbConnection();
		try 
		{
			out.println(con.registration(user));
			
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) 
		{				
			
		} 
	}

}
