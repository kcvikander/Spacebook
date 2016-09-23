package com.cvikander.model;

public class Address {
	private String street;
	private String city;
	private String state;
	private String postalCode;
	private String type;
	private int id;
	
	public Address(){}
	public Address(String inStreet, String inCity, String inState, String inPostalCode){
		this.street = inStreet;
		this.city = inCity;
		this.state = inState;
		this.postalCode = inPostalCode;
		this.id = 0;
	}
	
	public String getStreet(){
		return this.street;
	}
	
	public void setStreet(String inStreet){
		this.street = inStreet;
	}
	
	public String getCity(){
		return this.city;
	}
	
	public void setCity(String inCity){
		this.city = inCity;
	}
	
	public String getState(){
		return this.state;
	}
	
	public void setState(String inState){
		this.state = inState;
	}
	
	public String getPostalCode(){
		return this.postalCode;
	}
	
	public void setPostalCode(String inPostalCode){
		this.postalCode = inPostalCode;
	}
	
	public String getType(){
		return this.type;
	}
	
	public void setType(String inType){
		this.type = inType;
	}
	public int getId(){
		return this.id;
	}
	public void setId(int inId){
		this.id = inId;
	}
}
