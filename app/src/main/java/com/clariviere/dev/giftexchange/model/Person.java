package com.clariviere.dev.giftexchange.model;

import java.io.Serializable;

public class Person implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	private int id;
	private String personName;
	private String communicationAddress;
	private Person personPicked;

	public enum CommunicationMedium{
		eEmail,
		eSMS,
		eNone
	}

	private CommunicationMedium mMedium;

    public Person(String name, String communicationAddress){
        this.personName = name;
        this.communicationAddress = communicationAddress;
    }

	public Person(int id, String name){
		this.id = id;
		this.personName = name;
	}
	
	public Person(int id, String name, String communicationAddress){
		this.id = id;
		this.personName = name;
		this.communicationAddress = communicationAddress;
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

	public void setCommunicationAddress(String communicationAddress){
		this.communicationAddress = communicationAddress;
	}

	public String getCommunicationAddress(){
		return this.communicationAddress;
	}

	public void setPersonID(int personID){
		this.id = personID;
	}

	public Integer getPersonID(){
		return this.id;
	}

	public void setPersonMedium(CommunicationMedium iMedium){ mMedium = iMedium; }

	public CommunicationMedium getPersonMedium(){ return this.mMedium; }
}
