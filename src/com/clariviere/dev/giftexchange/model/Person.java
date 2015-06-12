package com.clariviere.dev.giftexchange.model;

import java.io.Serializable;

public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	
	private int id;
	private String personName;
	private String email;
	
	private Person personPicked;
	
	private Person(){}
	
	public Person(String name){
		this.personName = name;
	}
	
	public Person(int id, String name){
		this.id = id;
		this.personName = name;
	}
	
	public Person(String name,String email){
		this.personName = name;
		this.email = email;
	}
	
	public void setPersonPicked(Person p){
		this.personPicked = p;
	}
	
	public Person getPersonPicked(){
		return this.personPicked;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public String getPersonName(){
		return this.personName;
	}
}
