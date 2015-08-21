package com.clariviere.dev.giftexchange.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import android.content.Context;

import com.clariviere.dev.giftexchange.abstract_classes.AbstractState;
import com.clariviere.dev.giftexchange.model.Person;
import com.clariviere.dev.giftexchange.model.PersonMapping;

public class SaveStateListener extends AbstractState {

	
	public SaveStateListener(PersonMapping personMapping, LinkedList<Person> p, Context context) {
		super(personMapping,p,context);
		execute();
	}

	@Override
	public void execute() {
		try {
			File file = new File(con.getFilesDir(),textFile);

			if(file.exists()){
				file.delete();
			}
			FileOutputStream fileOutputStream = con.openFileOutput(textFile,Context.MODE_PRIVATE);
			ObjectOutput s = new ObjectOutputStream(fileOutputStream);

			s.writeObject(personMapping);
			s.writeObject(listOfPeeps);
			s.flush();
			s.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
