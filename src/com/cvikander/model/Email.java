package com.cvikander.model;

public class Email {
	private String address;
	private String type;
	private int id;
	
	public Email(){
		this.address = "";
		this.type = "";
	}
	public Email(String inAddress, String inType){
		this.address = inAddress;
		this.type = inType;
	}
	
	public String getAddress(){
		return this.address;
	}
	public void setAddress(String inAddress){
		this.address = inAddress;
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
