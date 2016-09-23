package com.cvikander.model;

public class User {
	private String firstName;
	private String lastName;
	private Address address;
	private Phone phone;
	private Email email;
	private String userName;
	private String password;
	private int userId;
	
	public User(){}
	public User(String firstNameIn, String lastNameIn, Address addressIn, Phone phoneIn,
								Email emailIn, String usernameIn, String passwordIn, int userIdIn){
		this.firstName = firstNameIn;
		this.lastName = lastNameIn;
		this.address = addressIn;
		this.phone = phoneIn;
		this.email = emailIn;
		this.userName = usernameIn;
		this.password = passwordIn;
		this.userId = userIdIn;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public void setFirstName(String fn){
		this.firstName = fn;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
	public void setLastName(String ln){
		this.lastName = ln;
	}
	
	public Phone getPhone(){
		return this.phone;
	}
	
	public void setPhone(Phone phoneIn){
		this.phone = phoneIn;
	}
	
	public Email getEmail(){
		return this.email;
	}
	
	public void setEmail(Email em){
		this.email = em;
	}
	
	public Address getAddress(){
		return this.address;
	}
	
	public void setAddress(Address addressIn){
		this.address = addressIn;
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public void setUserName(String un){
		this.userName = un;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String pw){
		this.password = pw;
	}
	public int getUserId(){
		return this.userId;
	}
	
	public void setUserId(int userIdIn){
		this.userId = userIdIn;
	}
}
