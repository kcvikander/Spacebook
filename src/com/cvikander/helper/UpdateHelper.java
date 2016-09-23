package com.cvikander.helper;

public class UpdateHelper {
	public static String getDeleteContactString(){
		String returnString = "";
		returnString += "UPDATE contact \n";
		returnString += "SET Active = 0 \n";
		returnString += "WHERE ContactId = ?";
		return returnString;
	};
	public static String getAddressUpdateString(){
		String returnString = "";
		returnString += "UPDATE address \n";
		returnString += "SET StreetOne = ?, \n";
		returnString += " City = ?, \n";
		returnString += " State = ?, \n";
		returnString += " PostalCode = ? \n";
		returnString += "WHERE AddressId = ? ";
		return returnString;
	};
	public static String getContactUpdateString(){
		String returnString = "";
		returnString += "UPDATE contact \n";
		returnString += "SET FirstName = ?, \n";
		returnString += "  LastName = ? \n";
		returnString += "WHERE ContactId = ? ";
		return returnString;
	};
	public static String getEmailUpdateString(){
		String returnString = "";
		returnString += "UPDATE email_address \n";
		returnString += "SET Address = ?, \n";
		returnString += " EmailAddressType = ? \n";
		returnString += "WHERE EmailAddressId = ? ";
		return returnString;
	};
	public static String getPhoneUpdateString(){
		String returnString = "";
		returnString += "UPDATE phone \n";
		returnString += "SET PhoneNumber = ?, \n";
		returnString += " PhoneType = ? \n";
		returnString += "WHERE PhoneId = ? ";
		return returnString;
	};
	public static String getContactDateUpdateString(){
		String returnString = "";
		returnString += "UPDATE contact_date \n";
		returnString += "SET ContactDate = ?, \n";
		returnString += "  ContactDateType = ? \n";
		returnString += "WHERE ContactDateId = ? ";
		return returnString;
	}
}
