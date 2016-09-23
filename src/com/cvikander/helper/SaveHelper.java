package com.cvikander.helper;
public class SaveHelper {
	public static String getUserInsertString(){
		String returnString = "";
		returnString += "INSERT INTO site_user (FirstName, LastName, EmailAddressId, PhoneId, AddressId, UserName, Password) \n";
		returnString += "VALUES (?, ?, ?, ?, ?, ?, ?)";
		return returnString;
	};
	public static String getAddressInsertString(){
		String returnString = "";
		returnString += "INSERT INTO address (StreetOne, City, State, PostalCode) \n";
		returnString += "VALUES (?, ?, ?, ?)";
		return returnString;
	};
	public static String getPhoneInsertString(){
		String returnString = "";
		returnString += "INSERT INTO phone (PhoneNumber, PhoneType) \n";
		returnString += "VALUES (?, ?)";
		return returnString;
	};
	
	public static String getEmailInsertString(){
		String returnString = "";
		returnString += "INSERT INTO email_address (Address, EmailAddressType) \n";
		returnString += "VALUES (?, ?)";
		return returnString;
	};
	public static String getContactDateInsertString(){
		String returnString = "";
		returnString += "INSERT INTO contact_date (ContactId, ContactDate, ContactDateType) \n";
		returnString += "VALUES (?, ?, ?)";
		return returnString;
	};
	public static String getContactInsertString(){
		String returnString = "";
		returnString += "INSERT INTO contact (OwnerId, FirstName, LastName, AddressId) \n";
		returnString += "VALUES (?, ?, ?, ?)";
		return returnString;
	};
	public static String getContactUpdateString(){
		String returnString = "";
		returnString += "UPDATE contact";
		returnString += "SET FirstName = ? \n";
		returnString += "     LastName = ? \n";
		returnString += "     AddressId = ? \n";
		returnString += "WHERE ContactId = ?";
		return returnString;
	};
	public static String getContactEmailInsertString(){
		String returnString = "";
		returnString += "INSERT INTO contact_email_address (ContactId, EmailAddressId) \n";
		returnString += "VALUES (?, ?)";
		return returnString;
	};
	public static String getContactPhoneInsertString(){
		String returnString = "";
		returnString += "INSERT INTO contact_phone (ContactId, PhoneId) \n";
		returnString += "VALUES (?, ?)";
		return returnString;
	};
	public static String getContactContactDateInsertString(){
		String returnString = "";
		returnString += "INSERT INTO contact_contact_date (ContactId, ContactDateId) \n";
		returnString += "VALUES (?, ?)";
		return returnString;
	};
	public static String getInStringFromArray(int[] inArray){
		String returnString = "";
		int i;
		int len = inArray.length;
		for (i=0;i<len;i++){
			returnString += "'" + Integer.toString(inArray[i]) + "'";
			if (i + 1 != len){
				returnString += ",";
			}
		}
		return returnString;
	};
	public static String getErrorLogInsertString(){
		String returnString = "";
		returnString += "INSERT INTO error_log (StackTrace) \n";
		returnString += "VALUES (?)";
		return returnString;
	};
}
