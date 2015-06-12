package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import android.content.Context;

import com.clariviere.dev.giftexchange.model.Person;
import com.clariviere.dev.giftexchange.model.PersonMapping;

public class SaveStateListener{

	private Context con;
	private String textFile = "";
	private PersonMapping personMapping;
	private LinkedList<Person> listOfPeeps;
	
	public SaveStateListener(PersonMapping personMapping, LinkedList<Person> p, Context context) {
		this.personMapping = personMapping;
		this.listOfPeeps = p;
		textFile = "giftExchangeSaveState.st";
		this.con = context;
		executeSave();
	}
	
	public void executeSave(){
		try {			
		    File file = new File(con.getFilesDir(),textFile);
			
		    if(file.exists()){
		    	file.delete();
		    }
			FileOutputStream fstream = con.openFileOutput(textFile,Context.MODE_PRIVATE);
			ObjectOutput s = new ObjectOutputStream(fstream);
			 
			  s.writeObject(personMapping);
			  s.writeObject(listOfPeeps);
			  s.flush(); 
			  s.close();
			  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
