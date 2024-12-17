package model.bo;

import java.sql.SQLException;
import java.util.ArrayList;

import model.bean.user;
import model.dao.UserDAO;

public class UserBO {
	private static UserDAO dao = new UserDAO();
	public boolean isValidUser(String username, String password) throws ClassNotFoundException, SQLException {
		user u = dao.getUser(username);
		if(u!=null) {
			if(u.getPassword().equals(password)) {
				return true;
			}
			return false;
		}
		return false;
	}
}
