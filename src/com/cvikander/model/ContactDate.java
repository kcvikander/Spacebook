package com.cvikander.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContactDate {
	private Date date;
	private String type;
	private int id;
	DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	DateFormat htmlFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public ContactDate(){
		this.date = new Date();
		this.type = "";
	}
	public ContactDate(Date inDate, String inLabel){
		this.date = inDate;
		this.type = inLabel;
	}
	public Date getDate(){
		return this.date;
	}
	public void setDate(Date inDate){
		this.date = inDate;
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
	public String getDateString(){
		return formatter.format(this.date);
	}
	public String getHtmlString(){
		return htmlFormatter.format(this.date);
	}
}
