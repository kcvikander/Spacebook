package com.cvikander.helper;

public class GetHelper {
	public static String getUserQueryString(){
		String returnString = "";
		returnString += "SELECT su.SiteUserId, su.FirstName, su.LastName, a.StreetOne, a.City, a.State, a.PostalCode, \n";
		returnString += "       p.PhoneNumber, p.PhoneType, e.Address, e.EmailAddressType, \n";
		returnString += "       su.AddressId, su.UserName ";
		returnString += "FROM site_user su \n";
		returnString += "INNER JOIN email_address e on e.EmailAddressId = su.EmailAddressId \n";
		returnString += "	AND e.Active = 1 \n";
		returnString += "INNER JOIN phone p on p.PhoneId = su.PhoneId \n";
		returnString += "	AND p.Active = 1 \n";
		returnString += "INNER JOIN address a on a.AddressId = su.AddressId \n";
		returnString += "	AND a.Active = 1 \n";
		returnString += "WHERE su.UserName = ? \n";
		returnString += "    AND su.Password = ? \n";
		returnString += "    AND su.Active = 1 \n";
		return returnString;
	}
	public static String getPhoneByIdString(){
		String returnString = "";
		returnString += "SELECT PhoneNumber, PhoneType, PhoneId \n";
		returnString += "FROM phone \n";
		returnString += "WHERE Active = 1 \n";
		returnString += "	AND PhoneId = ? ";
		return returnString;
	}
	public static String getEmailByIdString(){
		String returnString = "";
		returnString += "SELECT EmailAddress, EmailAddressType, EmailAddressId \n";
		returnString += "FROM email_address \n";
		returnString += "WHERE Active = 1 \n";
		returnString += "	AND EmailAddressId = ? ";
		return returnString;
	}
	public static String getContactsQueryString(){
		String returnString = "";
		returnString += "SELECT c.ContactId, c.FirstName, c.LastName, a.StreetOne, a.City, a.State, a.PostalCode \n";
		returnString += " FROM contact c \n";
		returnString += " INNER JOIN address a on c.AddressId = a.AddressId \n";
		returnString += "     AND a.Active = 1 \n";
		returnString += " WHERE c.Active = 1 \n";
		returnString += "      AND c.OwnerId = ?";
		return returnString;		
	};
	
	public static String getContactByIdQueryString(){
		String returnString = "";
		returnString += "SELECT c.ContactId, c.FirstName, c.LastName, c.OwnerId, ad.StreetOne, ad.City, ad.State, ad.PostalCode, ad.AddressId \n";
		returnString += " FROM contact c \n";
		returnString += " INNER JOIN address ad on c.AddressId = ad.AddressId \n";
		returnString += "     AND ad.Active = 1 \n";
		returnString += " WHERE c.Active = 1 \n";
		returnString += "      AND c.ContactId = ?";
		return returnString;
	};
	
	public static String getEmailsByContactIdQueryString(){
		String returnString = "";
		returnString += "SELECT e.Address, e.EmailAddressType, e.EmailAddressId ";
		returnString += "FROM email_address e ";
		returnString += "INNER JOIN contact_email_address cea on e.EmailAddressId = cea.EmailAddressId ";
		returnString += "WHERE e.Active = 1 ";
		returnString += "AND cea.ContactId = ?";
		return returnString;	
	};
	public static String getPhonesByContactIdQueryString(){
		String returnString = "";
		returnString += "SELECT p.PhoneNumber, p.PhoneType, p.PhoneId ";
		returnString += "FROM phone p ";
		returnString += "INNER JOIN contact_phone conp on p.PhoneId = conp.PhoneId ";
		returnString += "WHERE p.Active = 1 ";
		returnString += "AND conp.ContactId = ? ";
		return returnString;	
	};
	public static String getContactDatesByContactIdQueryString(){
		String returnString = "";
		returnString += "SELECT ContactDate, ContactDateType, ContactDateId \n";
		returnString += "FROM contact_date \n";
		returnString += "WHERE Active = 1 \n";
		returnString += "AND ContactId = ? \n";
		return returnString;	
	};
}
