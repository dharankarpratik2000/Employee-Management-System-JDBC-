package main;

import java.sql.Connection;

import util.DBConnection;

public class TestConnection 
{
	public static void main(String args[])
	{
		Connection cn = DBConnection.getConnection();
		if(cn != null)
		{
			System.out.println("Connection Test Successfully");
		}
		else
		{
			System.out.println("Connection Failed");
		}
	}
}
