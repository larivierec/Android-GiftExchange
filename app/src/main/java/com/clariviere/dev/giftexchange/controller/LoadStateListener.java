package com.clariviere.dev.giftexchange.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import android.content.Context;

import com.clariviere.dev.giftexchange.abstract_classes.AbstractState;
import com.clariviere.dev.giftexchange.model.Person;
import com.clariviere.dev.giftexchange.model.PersonMapping;

public class LoadStateListener extends AbstractState{

	public LoadStateListener(PersonMapping personMapping, LinkedList<Person> p, Context context) {
		super(personMapping,p,context);
		execute();
	}

	@Override
	public void execute() {
		try {
			File file = new File(con.getFilesDir(),textFile);

			if(file.exists()){

				FileInputStream fstream = con.openFileInput(textFile);
				ObjectInput s = new ObjectInputStream(fstream);
				try{
					this.setPersonMapping((PersonMapping)s.readObject());
					this.setListOfPeeps((LinkedList<Person>)s.readObject());
					s.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public LinkedList<Person> getListOfPeople() {
		return listOfPeople;
	}

	public void setListOfPeeps(LinkedList<Person> listOfPeeps) {
		this.listOfPeople = listOfPeeps;
	}

	public PersonMapping getPersonMapping() {
		return personMapping;
	}

	public void setPersonMapping(PersonMapping personMapping) {
		this.personMapping = personMapping;
	}
}
