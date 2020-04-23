package DbConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Objects.auction;
import Objects.user;
public class DbConnection 
{
	String dbDriver = "com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://46.41.149.32:3306/bredcash";
    // Database name to access
    String dbUsername = "corny"; 
    String dbPassword = "!OQ12FDGqw12TY"; 
  //obiekt tworz¹cy po³¹czenie z baz¹ danych.
  	private Connection connection;
  	//obiekt pozwalaj¹cy tworzyæ nowe wyra¿enia SQL
  	private Statement statement;
  	//zapytanie SQL
  	private String query;
	

	public user login(String nick,String password)
	{
	
		user u = null;
		try {
			// po³¹czenie z baz¹			
			Class.forName(dbDriver).newInstance();
			connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			// wykonywanie polecen sql
			query = "SELECT `nick`, `password`, `amount`, `auctions`, `join_date`"
					+ " FROM `Users` WHERE `nick` =\""+nick+"\" and `password`= \"" + password + "\"";
			statement = connection.createStatement();
			statement.execute(query);
			ResultSet result = statement.getResultSet();
			
			// zapisywanie ich i wyœwietlanie
			
				if(result.next())
				{
					
					String userNick = result.getString("nick");
					String userPassword = result.getString("password");
					int amount = result.getInt("amount");
					int auctions = result.getInt("auctions");
					Date joinDate = result.getDate("join_date");
					u = new user(userNick,userPassword,amount,auctions,joinDate);			
				}			
				
			
                        //zwolnienie zasobów i zamkniêcie po³¹czenia
                        statement.close();
                        connection.close();
                        
                        
   
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		return u;
	}
	
	public String registration(user u) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		try {
			Class.forName(dbDriver).newInstance();
			connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			query = "INSERT INTO `Users`(`nick`, `password`, `amount`, `auctions`, `join_date`) VALUES"
					+ " (\""+u.getNick()+"\",\""+u.getPassword()+"\",0,0,NOW())";
			statement = connection.createStatement();
			statement.execute(query);
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
			Date date = new Date(System.currentTimeMillis());
			System.out.println(formatter.format(date) + " user " + u.getNick()+ " has registered successfully");
			  statement.close();
              connection.close();
			return "registration completed successfully";
			
			
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
			Date date = new Date(System.currentTimeMillis());
			System.out.println(formatter.format(date) + " Registration failed, nickname " + u.getNick()+" already exist");
			return "nickname already exist";
		}
	}
	
	public user TopUp(String nick,int value)
	{
		user u = null;
		try {
			// po³¹czenie z baz¹			
			Class.forName(dbDriver).newInstance();
			connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			// wykonywanie polecen sql
			query = "UPDATE `Users` SET `amount` = " + value +  " WHERE `nick` = \"" + nick + "\"";
			String query1 = "SELECT `nick`, `password`, `amount`, `auctions`, `join_date`"
					+ " FROM `Users` WHERE `nick` =\""+nick+"\"";
			statement = connection.createStatement();
			statement.execute(query);
			statement.execute(query1);
			ResultSet result = statement.getResultSet();
			
			// zapisywanie ich i wyœwietlanie
			
				if(result.next())
				{
					
					String userNick = result.getString("nick");
					String userPassword = result.getString("password");
					int amount = result.getInt("amount");
					int auctions = result.getInt("auctions");
					Date joinDate = result.getDate("join_date");
					u = new user(userNick,userPassword,amount,auctions,joinDate);			
				}			
				
			
                        //zwolnienie zasobów i zamkniêcie po³¹czenia
                        statement.close();
                        connection.close();
                        
                        
   
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		
		return u;
	}
	public void addAuction(String title,String desc,int price,String photo_name,String auth_name,String end_date) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Class.forName(dbDriver).newInstance();
		connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
		
		// wyszukanie z bazy id autora
		String queryAuthId = "SELECT `user_id` from `Users` where `nick` = \""+auth_name+"\"";
		statement = connection.createStatement();
		statement.execute(queryAuthId);
		ResultSet result = statement.getResultSet();
		String id = "";
		if(result.next())
		{
			id = result.getString("user_id");
		}	
		// FORMAT DATY : YYYY-MM-DD hh:mm:ss !!!!!!!!
		query = "INSERT INTO `Auctions`(`title`, `description`, `photo`,`current_price`, `start_date`, `end_date`,`auth_id`,`winning_id`) VALUES"
				+ " (\""+title+"\",\""+desc+"\",\""+photo_name+"\",\""+price+"\",NOW(),\""+end_date+"\",\""+id+"\",\"" + id + "\")";
		statement.execute(query);
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		System.out.println(formatter.format(date) + " user " + auth_name + " has added new Auction \""+ title + "\"");
		  statement.close();
          connection.close();
	}
	
	public ArrayList<auction> downloadAuctionsToArray(String type) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Class.forName(dbDriver).newInstance();
		connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
		statement = connection.createStatement();
		auction a = null;
		ArrayList<auction> auctions = new ArrayList<>();
								
						query = "select auction_id,title,description,photo,current_price,start_date,end_date,U.nick \"auth_id\",U2.nick \"win_id\"from Auctions a "
								+ "join Users U on a.auth_id = U.user_id "
								+ "join Users U2 on a.winning_id = U2.user_id";
					
						statement.execute(query);
						ResultSet result = statement.getResultSet();
						while(result.next())
						{			
							String title = result.getString("title");
							String description = result.getString("description");
							String photo = result.getString("photo");
							String current_price = result.getString("current_price");
							String start_date = result.getString("start_date");
							String end_date = result.getString("end_date");
							String auth_id = result.getString("auth_id");
							String id = result.getString("auction_id");
							String win_id = result.getString("win_id");
									
							
							a = new auction(id,title,description,photo,current_price,start_date,end_date,auth_id,win_id);
							if(type.contentEquals("withValidation"))
							{
								if(a.isGoodDate()==true)
								{
									auctions.add(a);
								}
							}
							else
							{
								auctions.add(a);
							}
							
						}
						  statement.close();
	                        connection.close();
						return auctions;						
		}
	
		public String bidTheOffer(String auction_id,String user,int amount) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
		{
			Class.forName(dbDriver).newInstance();
			connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			statement = connection.createStatement();
			
			String auth_id = " ";
			String current_price = " ";
			String message = " ";
			String win_id = " ";
			
			query = "select u.nick \"auth_id\",current_price,winning_id from Auctions a "
					+ "join Users u on a.auth_id = u.user_id"
					+ " where auction_id = " + auction_id;
			
			statement.execute(query);
			ResultSet result = statement.getResultSet();
			while(result.next())
			{			
				current_price = result.getString("current_price");
				auth_id = result.getString("auth_id");
				win_id = result.getString("winning_id");			
			}
			
			query = "select u.nick \"winning_id\" from Auctions a "
					+ "join Users u on a.winning_id = u.user_id"
					+ " where auction_id = " + auction_id;
			
			statement.execute(query);
			ResultSet result2 = statement.getResultSet();
			while(result2.next())
			{			
			
				win_id = result2.getString("winning_id");			
			}
			
			if(amount<=Integer.parseInt(current_price))
			{
				message = "Your offer must be higher than current price!";
			}
			else if(auth_id.equals(user))
			{
				message = "This is your auction!";
			}
			else if(win_id.equals(user))
			{
				message = "you are already winning this auction!";
			}
			else
			{
				message = "You succesfully bidded the offer!";
				query = "UPDATE Auctions SET `winning_id`= (SELECT user_id from Users where nick = \""+user+"\") ,`current_price` = " + amount + " where auction_id = " + auction_id;
				statement.execute(query);
				query = "UPDATE Users SET amount = amount - " + amount + " where nick = \""+user+"\"";
				statement.execute(query);
			}
			  statement.close();
              connection.close();
			return message;
		}
		
	}
 

