package com.cvikander.model;

public class Phone {
	private String number;
	private String type;
	private int id;
	
	public Phone(){
		this.number = "0000000000";
		this.type = "";
	}
	public Phone(String inNumber, String inType){
		this.number = inNumber;
		this.type = inType;
	}
	
	public String getNumber(){
		return this.number;
	}
	public void setNumber(String inNumber){
		this.number = inNumber;
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
