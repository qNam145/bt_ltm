package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.user;

public class UserDAO {
	public ArrayList<user> getAllUser() throws ClassNotFoundException, SQLException {
		ArrayList<user> list = new ArrayList<user>();
		Connection con = connectdb.getConnection();
		Statement stm =con.createStatement();
		String Query="Select * from user";
		ResultSet rs = stm.executeQuery(Query);
		while(rs.next()) {
			String username=rs.getString("username");
			String password=rs.getString("password");
			user u =new user(username,password);
			list.add(u);
		}
		return list;
	}
	public user getUser(String username) throws ClassNotFoundException, SQLException {
		Connection con = connectdb.getConnection();
		Statement stm =con.createStatement();
		String Query="Select * from user Where username='"+username+"'";
		ResultSet rs = stm.executeQuery(Query);
		while(rs.next()) {
			String password=rs.getString("password");
			user u =new user(username,password);
			return u;
		}
		return null;
	}
}
