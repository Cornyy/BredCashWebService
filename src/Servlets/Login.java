package Servlets;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import DbConnection.DbConnection;
import Objects.user;

  



@WebServlet("/Login")
public class Login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// Pobieranie parametrów
		String nick = request.getParameter("nick");
		String password = request.getParameter("password");
		String savePath = new File("").getAbsolutePath() + "/uploadfiles";
		System.out.println(savePath);
		DbConnection db = new DbConnection();
		user u = db.login(nick, password);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(u);			
		out.print(json);
							
		
		if(u!=null)
		{
			// logowanie zakonczone sukcesem
			
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
			Date date = new Date(System.currentTimeMillis());
			System.out.println(formatter.format(date) + " user " + u.getNick() + " have logged in!");
		}
		else
		{
			// logowanie zakonczone niepowodzeniem
			
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
			Date date = new Date(System.currentTimeMillis());
			System.out.println(formatter.format(date) + " login failed with nick " + nick + " and password "
					+ password );
			
		}
		
	}
}
