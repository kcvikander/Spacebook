package com.cvikander.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cvikander.helper.GetHelper;
import com.cvikander.helper.SaveHelper;
import com.cvikander.model.Address;
import com.cvikander.model.Email;
import com.cvikander.model.Phone;
import com.cvikander.model.User;

public class UserService {
	public static void saveUser(User user){
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String userInsert = SaveHelper.getUserInsertString();
			
			int emailAddressId = saveUserEmail(user.getEmail());
			int phoneNumberId = saveUserPhone(user.getPhone());
			int addressId = saveUserAddress(user.getAddress());
			
			ps = conn.prepareStatement(userInsert);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setInt(3, emailAddressId);
			ps.setInt(4, phoneNumberId);
			ps.setInt(5, addressId);
			ps.setString(6, user.getUserName());
			ps.setString(7, user.getPassword());
			
			ps.executeUpdate();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			if (rs.next()){
				user.setUserId(rs.getInt(1));
			}
			conn.commit();
		} catch (Exception e){
			if (null != conn){
				try {
					conn.rollback();
				} catch (SQLException se){
					se.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			try {
				if (null != ps) ps.close();
				if (null != conn) conn.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	private static int saveUserPhone(Phone phone){
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		ResultSet rs = null;
		int newId = -1;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String phoneInsert = SaveHelper.getPhoneInsertString();
			
			ps = conn.prepareStatement(phoneInsert);
			ps.setString(1, phone.getNumber());
			ps.setString(2, phone.getType());
			ps.executeUpdate();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			if (rs.next()){
				newId = rs.getInt(1);
			}
			conn.commit();
		} catch (Exception e){
			if (null != conn){
				try {
					conn.rollback();
				} catch (SQLException se){
					se.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally {
			try {
				if (null != ps) ps.close();
				if (null != conn) conn.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		return newId;
	}
	
	private static int saveUserEmail(Email email){		
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		ResultSet rs = null;
		int newId = -1;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String emailInsert = SaveHelper.getEmailInsertString();
			
			ps = conn.prepareStatement(emailInsert);
			ps.setString(1, email.getAddress());
			ps.setString(2, email.getType());
			ps.executeUpdate();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			if (rs.next()){
				newId = rs.getInt(1);
			}
			conn.commit();
		} catch (Exception e){
			if (null != conn){
				try {
					conn.rollback();
				} catch (SQLException se){
					se.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			try {
				if (null != ps) ps.close();
				if (null != conn) conn.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		return newId;
	}
	
	private static int saveUserAddress(Address userAddress){
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		ResultSet rs = null;
		int newId = -1;
		
		try {	
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String addressInsert = SaveHelper.getAddressInsertString();
			
			ps = conn.prepareStatement(addressInsert);
			ps.setString(1, userAddress.getStreet());
			ps.setString(2, userAddress.getCity());
			ps.setString(3, userAddress.getState());
			ps.setString(4, userAddress.getPostalCode());

			ps.executeUpdate();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			if (rs.next()){
				newId = rs.getInt(1);
			}
			conn.commit();
		} catch (Exception e){
			if (null != conn){
				try {
					conn.rollback();
				} catch (SQLException se){
					se.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			try {
				if (null != ps) ps.close();
				if (null != conn) conn.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		return newId;
	}
	
	public static User tryLogin(String userName, String password){
		User user = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			String getUserString = GetHelper.getUserQueryString();
			ps = conn.prepareStatement(getUserString);
			ps.setString(1, userName);
			ps.setString(2, password);
			ps.executeQuery();
			rs = ps.getResultSet();
			while(rs.next()){
				user = new User();
				Address address = new Address();
				Phone phone = new Phone();
				Email email = new Email();
				user.setFirstName(rs.getString("FirstName"));
				user.setLastName(rs.getString("LastName"));
				user.setUserId(rs.getInt("SiteUserId"));
				user.setUserName(rs.getString("UserName"));
				
				address.setStreet(rs.getString("StreetOne"));
				address.setCity(rs.getString("City"));
				address.setState(rs.getString("State"));
				address.setPostalCode(rs.getString("PostalCode"));
				user.setAddress(address);
				
				phone.setNumber(rs.getString("PhoneNumber"));
				phone.setType(rs.getString("PhoneType"));
				user.setPhone(phone);
				
				email.setAddress(rs.getString("Address"));
				email.setType(rs.getString("EmailAddressType"));
				user.setEmail(email);
			}
		} catch (Exception e){
			if (null != conn){
				try {
					conn.rollback();
				} catch (SQLException se){
					se.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally {
			try {
				if (null != ps) ps.close();
				if (null != conn) conn.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}	
		return user;
	}
}
