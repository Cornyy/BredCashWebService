package Servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import DbConnection.DbConnection;
import Objects.user;


@WebServlet("/TopUpAccount")
public class TopUpAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public TopUpAccount() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nick = request.getParameter("nick");		
		int value =  Integer.parseInt(request.getParameter("value"));
		DbConnection db = new DbConnection();
		user u = db.TopUp(nick, value);
		
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream out = response.getOutputStream();
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(u);			
		out.print(json);
		
		if(u!=null)
		{		
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
			Date date = new Date(System.currentTimeMillis());
			System.out.println(formatter.format(date) + " user " + nick +" has topped up his account to the amount " + value);
		}		
	}
}
