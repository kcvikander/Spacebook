package com.cvikander.model;

import java.util.List;

public class ContactList {
	private List<Contact> contacts;
	
	public ContactList(){}
	public ContactList(List<Contact> inList){
		this.contacts = inList;
	}
	
	public void setContacts (List<Contact> inContacts){
		this.contacts = inContacts;
	}
	
	public List<Contact> getContacts(){
		return this.contacts;
	}
}
