package Objects;

import java.util.Date;

public class user 
{
	private String nick;
	private String password;
	private int amount;
	private int auctions;
	private Date join_date;
	
	public user(String nick,String password,int amount,int auctions,Date Join_date)
	{
		this.nick = nick;
		this.password = password;
		this.amount = amount;
		this.auctions = auctions;
		this.join_date = Join_date;
	}
	
	public String getNick()
	{
		return nick;
	}
	public String getPassword()
	{
		return password;
	}
	public int getAmount()
	{
		return amount;
	}
	public int getAuctions()
	{
		return auctions;
	}
	public Date getJoinDate()
	{
		return join_date;
	}

}
