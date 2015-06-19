package com.clariviere.dev.giftexchange.controller;

import java.util.ArrayList;
import java.util.LinkedList;

import com.clariviere.dev.giftexchange.model.Person;
import com.clariviere.dev.giftexchange.model.PersonMapping;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class GenerateController implements OnClickListener{
	
	private Context context;
	private ArrayList<String> list;
	private LinkedList<Person> peopleToReturn;
	private PersonMapping finalMap;
	
	
	public GenerateController(Context context, ArrayList<String> listPeople, PersonMapping p, LinkedList<Person> peopleToReturn) {
		this.context = context;
		this.list = listPeople;
		this.finalMap = p;
		this.peopleToReturn = peopleToReturn;
	}
	@Override
	public void onClick(View v) {
		if(list != null || list.size() != 0){			
			for(int i = 0; i < list.size(); i++){
				peopleToReturn.add(new Person(i, list.get(i)));
			}
			
			finalMap = new PersonMapping(peopleToReturn);
			finalMap.generateUniqueMaps();
			finalMap.mapUsers();
		}
	}

}
