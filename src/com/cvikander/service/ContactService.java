package com.cvikander.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cvikander.helper.SaveHelper;
import com.cvikander.helper.GetHelper;
import com.cvikander.helper.UpdateHelper;
import com.cvikander.model.Address;
import com.cvikander.model.ContactDate;
import com.cvikander.model.Email;
import com.cvikander.model.Phone;
import com.cvikander.model.Contact;
import com.cvikander.model.ContactList;

public class ContactService {
	//SAVE CONTACTS
	public static void saveContact(Contact contact){
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			int numPhone = contact.getPhoneNumbers().size();
			int numEmail = contact.getEmails().size();
			int numDates = contact.getContactDates().size();
			int i = 0;
			int tempId;
			
			//Step 1: Save Address to get Address ID
			int addressId = saveContactAddress(contact.getAddress());
			//INSERT INTO contact (OwnerId, FirstName, LastName, AddressId)
			
			//Step 2: Save Contact record to get new Contact ID
			String contactInsert = SaveHelper.getContactInsertString();;
			ps = conn.prepareStatement(contactInsert);
			ps.setInt(1, contact.getOwnerId());
			ps.setString(2, contact.getFirstName());
			ps.setString(3, contact.getLastName());
			ps.setInt(4, addressId);
			
			ps.executeUpdate();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			if (rs.next()){
				contact.setContactId(rs.getInt(1));
			}
			conn.commit();
			//Step 3: loop through all phones, emails, and dates and save them, then save bridge record
			//         we don't need to save the IDs as they're bridge records
			for (i=0;i<numPhone;i++){
				tempId = saveContactPhone(contact.getPhoneNumbers().get(i));
				saveContactPhoneBridgeRecord(tempId, contact.getContactId());
			}
			for (i=0;i<numEmail;i++){
				tempId = saveContactEmail(contact.getEmails().get(i));
				saveContactEmailBridgeRecord(tempId, contact.getContactId());
			}
			for (i=0;i<numDates;i++){
				saveContactDate(contact.getContactDates().get(i),contact.getContactId());
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
		} finally {
			try {
				if (null != ps) ps.close();
				if (null != conn) conn.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	};
	
	public static void updateContact(Contact contact){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			int numPhone = contact.getPhoneNumbers().size();
			int numEmail = contact.getEmails().size();
			int numDates = contact.getContactDates().size();
			int i = 0;
			int tempId;
			
			//Step 1: Update Address
			updateContactAddress(contact.getAddress());
			
			//Step 2: Update Contact record
			String contactUpdate = UpdateHelper.getContactUpdateString();
			ps = conn.prepareStatement(contactUpdate);
			
			ps.setString(1, contact.getFirstName());
			ps.setString(2, contact.getLastName());
			ps.setInt(3, contact.getContactId());
			
			ps.executeUpdate();
			conn.commit();
			//Step 3: loop through all phones and emails and save them, then save bridge record
			//         we don't need to save the IDs as they're bridge records
			int[] phoneIdList = new int[numPhone];
			int[] emailIdList = new int[numEmail];
			int[] dateIdList = new int[numDates];
			for (i=0;i<numPhone;i++){
				Phone thisPhone = contact.getPhoneNumbers().get(i);
				if (thisPhone.getId() > 0){
					//Existing Phone, update it
					updateContactPhone(thisPhone);
					phoneIdList[i] = thisPhone.getId();
				} else {
					//New Phone, save it
					tempId = saveContactPhone(thisPhone);
					saveContactPhoneBridgeRecord(tempId, contact.getContactId());
					thisPhone.setId(tempId);
					phoneIdList[i] = tempId;
				}
			}
			for (i=0;i<numEmail;i++){
				Email thisEmail = contact.getEmails().get(i);
				if (thisEmail.getId() > 0){
					//Existing Email, update it
					updateContactEmail(thisEmail);
					emailIdList[i] = thisEmail.getId();
				} else {
					//New email, save it
					tempId = saveContactEmail(thisEmail);
					saveContactEmailBridgeRecord(tempId, contact.getContactId());
					thisEmail.setId(tempId);
					emailIdList[i] = tempId;
				}
			}
			for (i=0;i<numDates;i++){
				ContactDate thisDate = contact.getContactDates().get(i);
				if (thisDate.getId() > 0){
					//Existing Email, update it
					updateContactDate(thisDate);
					dateIdList[i] = thisDate.getId();
				} else {
					//New email, save it
					tempId = saveContactDate(thisDate,contact.getContactId());
					thisDate.setId(tempId);
					dateIdList[i] = tempId;
				}
			}
			if (numPhone > 0){
				deactivateContactPhones(phoneIdList, contact.getContactId());
			} else {
				deactivateContactPhones(contact.getContactId());
			}
			if (numEmail > 0){
				deactivateContactEmails(emailIdList, contact.getContactId());
			} else {
				deactivateContactEmails(contact.getContactId());
			}
				
			if (numDates > 0){
				deactivateContactDates(dateIdList, contact.getContactId());
			} else {
				deactivateContactDates(contact.getContactId());
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
		} finally {
			try {
				if (null != ps) ps.close();
				if (null != conn) conn.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	};
	
	private static int saveContactPhone(Phone phone){
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
	
	private static int saveContactEmail(Email email){		
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
	
	private static int saveContactDate(ContactDate date, int contactId){		
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
			
			String dateInsert = SaveHelper.getContactDateInsertString();
			
			ps = conn.prepareStatement(dateInsert);
			ps.setInt(1, contactId);
			ps.setString(2,date.getDate().toString());
			ps.setString(3,date.getType());
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
	
	private static int saveContactAddress(Address contactAddress){
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
			ps.setString(1, contactAddress.getStreet());
			ps.setString(2, contactAddress.getCity());
			ps.setString(3, contactAddress.getState());
			ps.setString(4, contactAddress.getPostalCode());
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
		}
		return newId;
	}
	
	private static void saveContactPhoneBridgeRecord(int phoneId, int contactId){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {		
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String contactPhoneString = SaveHelper.getContactPhoneInsertString();
			
			ps = conn.prepareStatement(contactPhoneString);
			ps.setInt(1, contactId);
			ps.setInt(2, phoneId);
			ps.executeUpdate();
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
		}
	}
	
	private static void saveContactEmailBridgeRecord(int emailId, int contactId){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {			
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String contactPhoneString = SaveHelper.getContactEmailInsertString();
			
			ps = conn.prepareStatement(contactPhoneString);
			ps.setInt(1, contactId);
			ps.setInt(2, emailId);
			ps.executeUpdate();
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
		}
	}
	
	private static void updateContactAddress(Address address){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String addressInsert = UpdateHelper.getAddressUpdateString();
			
			ps = conn.prepareStatement(addressInsert);
			ps.setString(1, address.getStreet());
			ps.setString(2, address.getCity());
			ps.setString(3, address.getState());
			ps.setString(4,  address.getPostalCode());
			ps.setInt(5, address.getId());
			ps.executeUpdate();
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
	
	private static void updateContactPhone(Phone phone){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String phoneUpdate = UpdateHelper.getPhoneUpdateString();

			ps = conn.prepareStatement(phoneUpdate);
			ps.setString(1, phone.getNumber());
			ps.setString(2,phone.getType());
			ps.setInt(3,phone.getId());
			ps.executeUpdate();
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
	
	private static void updateContactEmail(Email email){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String emailInsert = UpdateHelper.getEmailUpdateString();

			ps = conn.prepareStatement(emailInsert);
			ps.setString(1, email.getAddress());
			ps.setString(2,email.getType());
			ps.setInt(3,email.getId());
			ps.executeUpdate();
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
	
	private static void updateContactDate(ContactDate date){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String dateInsert = UpdateHelper.getContactDateUpdateString();

			ps = conn.prepareStatement(dateInsert);
			ps.setString(1, date.getDate().toString());
			ps.setString(2,date.getType());
			ps.setInt(3,date.getId());
			ps.executeUpdate();
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
	
	private static void deactivateContactDates(int[] dateIdList, int contactId){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String idString = SaveHelper.getInStringFromArray(dateIdList);
			String dateUpdate = "";
			dateUpdate += "UPDATE contact_date \n";
			dateUpdate += "SET Active = 0 \n";
			dateUpdate += "WHERE ContactId = ? \n";
			dateUpdate += "AND ContactDateId NOT IN (";
			dateUpdate += idString;
			dateUpdate += ")";
			
			ps = conn.prepareStatement(dateUpdate);
			ps.setInt(1, contactId);
			ps.executeUpdate();
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
	
	private static void deactivateContactDates(int contactId){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String dateUpdate = "";
			dateUpdate += "UPDATE contact_date \n";
			dateUpdate += "SET Active = 0 \n";
			dateUpdate += "WHERE ContactId = ? \n";
			
			ps = conn.prepareStatement(dateUpdate);
			ps.setInt(1, contactId);
			ps.executeUpdate();
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
	
	private static void deactivateContactEmails(int[] emailIdList, int contactId){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String idString = SaveHelper.getInStringFromArray(emailIdList);
			String emailUpdate = "";
			emailUpdate += "UPDATE email_address ea \n";
			emailUpdate += "INNER JOIN contact_email_address cea on cea.EmailAddressId = ea.EmailAddressId \n";
			emailUpdate += "INNER JOIN contact c on c.ContactId = cea.ContactId \n"; 
			emailUpdate += "SET ea.Active = 0 \n";
			emailUpdate += "WHERE c.ContactId = ? \n";
			emailUpdate += "AND ea.EmailAddressId NOT IN (";
			emailUpdate += idString;
			emailUpdate += ")";
			
			ps = conn.prepareStatement(emailUpdate);
			ps.setInt(1, contactId);
			ps.executeUpdate();
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
	
	private static void deactivateContactEmails(int contactId){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String emailUpdate = "";
			emailUpdate += "UPDATE email_address ea \n";
			emailUpdate += "INNER JOIN contact_email_address cea on cea.EmailAddressId = ea.EmailAddressId \n";
			emailUpdate += "INNER JOIN contact c on c.ContactId = cea.ContactId \n"; 
			emailUpdate += "SET ea.Active = 0 \n";
			emailUpdate += "WHERE c.ContactId = ? \n";
			
			ps = conn.prepareStatement(emailUpdate);
			ps.setInt(1, contactId);
			ps.executeUpdate();
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
	
	private static void deactivateContactPhones(int[] phoneIdList, int contactId){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String idString = SaveHelper.getInStringFromArray(phoneIdList);
			String phoneUpdate = "";
			phoneUpdate += "UPDATE phone p \n";
			phoneUpdate += "INNER JOIN contact_phone cph on cph.PhoneId = p.PhoneId \n";
			phoneUpdate += "INNER JOIN contact c on c.ContactId = cph.ContactId \n"; 
			phoneUpdate += "SET p.Active = 0 \n";
			phoneUpdate += "WHERE c.ContactId = ? \n";
			phoneUpdate += "AND p.PhoneId NOT IN (";
			phoneUpdate += idString;
			phoneUpdate += ")";
			
			ps = conn.prepareStatement(phoneUpdate);
			ps.setInt(1, contactId);
			ps.executeUpdate();
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
	
	private static void deactivateContactPhones(int contactId){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String phoneUpdate = "";
			phoneUpdate += "UPDATE phone p \n";
			phoneUpdate += "INNER JOIN contact_phone cph on cph.PhoneId = p.PhoneId \n";
			phoneUpdate += "INNER JOIN contact c on c.ContactId = cph.ContactId \n"; 
			phoneUpdate += "SET p.Active = 0 \n";
			phoneUpdate += "WHERE c.ContactId = ? \n";
			
			ps = conn.prepareStatement(phoneUpdate);
			ps.setInt(1, contactId);
			ps.executeUpdate();
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
	
	//GET CONTACTS
	public static ContactList getContactsForUser(int userId){
		ContactList contacts = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int contactCount = 0;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			String getContactString = GetHelper.getContactsQueryString();
			ps = conn.prepareStatement(getContactString);
			ps.setInt(1, userId);
			ps.executeQuery();
			rs = ps.getResultSet();
			List<Contact> tempList = new ArrayList<Contact>();
			while(rs.next()){
				Contact thisContact = new Contact(userId);
				Address thisAddress = new Address();
				thisContact.setFirstName(rs.getString("FirstName"));
				thisContact.setLastName(rs.getString("LastName"));
				thisContact.setContactId(rs.getInt("ContactId"));
				thisAddress.setStreet(rs.getString("StreetOne"));
				thisAddress.setCity(rs.getString("City"));
				thisAddress.setState(rs.getString("State"));
				thisAddress.setPostalCode(rs.getString("PostalCode"));
				thisContact.setAddress(thisAddress);
				tempList.add(thisContact);
				contactCount++;
			}
			if (contactCount > 0){
				for (Contact contact : tempList){
					List<Email> currentContactEmailList = getEmailsForContact(contact.getContactId());
					List<Phone> currentContactPhoneList = getPhonesForContact(contact.getContactId());
					List<ContactDate> currentContactDateList = getDatesForContact(contact.getContactId());
					contact.setEmails(currentContactEmailList);
					contact.setPhoneNumbers(currentContactPhoneList);
					contact.setContactDates(currentContactDateList);;
				}
				contacts = new ContactList(tempList);
				//contacts.getContacts().addAll(tempList);
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
		return contacts;
	}
	
	public static Contact getContactById(int contactId){
		Contact contact = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			String getContactString = GetHelper.getContactByIdQueryString();
			ps = conn.prepareStatement(getContactString);
			ps.setInt(1, contactId);
			ps.executeQuery();
			rs = ps.getResultSet();
			while(rs.next()){
				contact = new Contact(rs.getInt("OwnerId"));
				contact.setContactId(contactId);
				Address address = new Address();
				contact.setFirstName(rs.getString("FirstName"));
				contact.setLastName(rs.getString("LastName"));
				contact.setContactId(rs.getInt("ContactId"));
				address.setStreet(rs.getString("StreetOne"));
				address.setCity(rs.getString("City"));
				address.setState(rs.getString("State"));
				address.setPostalCode(rs.getString("PostalCode"));
				address.setId(rs.getInt("AddressId"));
				contact.setAddress(address);
			}
			List<Email> currentContactEmailList = getEmailsForContact(contact.getContactId());
			List<Phone> currentContactPhoneList = getPhonesForContact(contact.getContactId());
			List<ContactDate> currentContactDateList = getDatesForContact(contact.getContactId());
			contact.setEmails(currentContactEmailList);
			contact.setPhoneNumbers(currentContactPhoneList);
			contact.setContactDates(currentContactDateList);;
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
		return contact;
	}
	
	public static List<Email> getEmailsForContact(int contactId){
		List<Email> emails = new ArrayList<Email>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
				
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			String getEmailString = GetHelper.getEmailsByContactIdQueryString();
			ps = conn.prepareStatement(getEmailString);
			ps.setInt(1, contactId);
			rs = ps.executeQuery();
			
			while(rs.next()){
				Email thisEmail = new Email();
				thisEmail.setAddress(rs.getString("Address"));
				thisEmail.setType(rs.getString("EmailAddressType"));
				thisEmail.setId(rs.getInt("EmailAddressId"));
				emails.add(thisEmail);
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
		return emails;
	}
	
	public static List<Phone> getPhonesForContact(int contactId){
		List<Phone> phones = new ArrayList<Phone>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
				
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			String getPhonesString = GetHelper.getPhonesByContactIdQueryString();
			ps = conn.prepareStatement(getPhonesString);
			ps.setInt(1, contactId);
			ps.executeQuery();
			rs = ps.getResultSet();
			
			while(rs.next()){
				Phone thisPhone = new Phone();
				thisPhone.setNumber(rs.getString("PhoneNumber"));
				thisPhone.setType(rs.getString("PhoneType"));
				thisPhone.setId(rs.getInt("PhoneId"));
				phones.add(thisPhone);
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
		return phones;
	}
	
	public static List<ContactDate> getDatesForContact(int contactId){
		List<ContactDate> dates = new ArrayList<ContactDate>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
				
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			String getDatesString = GetHelper.getContactDatesByContactIdQueryString();
			ps = conn.prepareStatement(getDatesString);
			ps.setInt(1, contactId);
			ps.executeQuery();
			rs = ps.getResultSet();
			
		    DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
			while(rs.next()){
				ContactDate thisDate = new ContactDate();
				Date tempDateObj = null;
				try {
					tempDateObj = df.parse(rs.getString("ContactDate"));
				} catch (ParseException pe){
					tempDateObj = new Date();
				}
				thisDate.setDate(tempDateObj);
				thisDate.setType(rs.getString("ContactDateType"));
				thisDate.setId(rs.getInt("ContactDateId"));
				dates.add(thisDate);
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
		return dates;
	}
	
	public static void deleteContact(int contactId){
		Connection conn = null;
		PreparedStatement ps = null;
				
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/ics425");
			conn = ds.getConnection();
			String deleteContactString = UpdateHelper.getDeleteContactString();
			ps = conn.prepareStatement(deleteContactString);
			ps.setInt(1, contactId);
			ps.executeUpdate();
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
	}
}
