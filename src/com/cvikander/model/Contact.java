package com.cvikander.model;

import java.util.ArrayList;
import java.util.List;

public class Contact {
	private String firstName;
	private String lastName;
	private int ownerId;
	private List<Phone> phoneNumbers;
	private Address address;
	private List<Email> emails;
	private List<ContactDate> contactDates;
	private int contactId;
	public Contact(int inOwnerId){
		this.firstName = "";
		this.lastName = "";
		this.ownerId = inOwnerId;
		this.phoneNumbers = new ArrayList<Phone>();
		this.address = new Address();
		this.emails = new ArrayList<Email>();
		this.contactDates = new ArrayList<ContactDate>();
	}
	
	public Contact(String inFirstName, String inLastName, int inOwnerId, List<Phone> inPhoneNumbers, 
			Address inAddress, List<Email> inEmails, List<ContactDate> inDates, int inContactId){
		this.firstName = inFirstName;
		this.lastName = inLastName;
		this.ownerId = inOwnerId;
		this.phoneNumbers = inPhoneNumbers;
		this.address = inAddress;
		this.emails = inEmails;
		this.contactDates = inDates;
		this.contactId = inContactId;
	}
	
	public Contact(String inFirstName, String inLastName, int inOwnerId, List<Phone> inPhoneNumbers, 
			Address inAddress, List<Email> inEmails, List<ContactDate> inDates){
		this.firstName = inFirstName;
		this.lastName = inLastName;
		this.ownerId = inOwnerId;
		this.phoneNumbers = inPhoneNumbers;
		this.address = inAddress;
		this.emails = inEmails;
		this.contactDates = inDates;
	}
	
	public Contact(String inFirstName, String inLastName){
		this.firstName = inFirstName;
		this.lastName = inLastName;
		this.address = null;
		this.phoneNumbers = new ArrayList<Phone>();
		this.emails = new ArrayList<Email>();
		this.contactDates = new ArrayList<ContactDate>();
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public void setFirstName(String inFirstName){
		this.firstName = inFirstName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
	public void setLastName(String inLastName){
		this.lastName = inLastName;
	}
	
	public int getOwnerId(){
		return this.ownerId;
	}
	
	public void setOwnerId(int inOwnerId){
		this.ownerId = inOwnerId;
	}
	
	public Address getAddress(){
		return this.address;
	}
	
	public void setAddress(Address inAddress){
		this.address = inAddress;
	}
	
	public List<Phone> getPhoneNumbers(){
		return this.phoneNumbers;
	}
	
	public void setPhoneNumbers(List<Phone> inPhones){
		this.phoneNumbers = inPhones;
	}
	
	public List<Email> getEmails(){
		return this.emails;
	}
	
	public void setEmails(List<Email> inEmails){
		this.emails = inEmails;
	}
	
	public int getContactId(){
		return this.contactId;
	}
	
	public void setContactId(int inContactId){
		this.contactId = inContactId;
	}
	
	public List<ContactDate> getContactDates(){
		return this.contactDates;
	}
	
	public void setContactDates(List<ContactDate> inDates){
		this.contactDates = inDates;
	}
}
