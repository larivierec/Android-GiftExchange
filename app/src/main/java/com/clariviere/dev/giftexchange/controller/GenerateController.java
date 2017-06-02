package com.clariviere.dev.giftexchange.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import com.clariviere.dev.giftexchange.model.Person;
import com.clariviere.dev.giftexchange.model.PersonMapping;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class GenerateController implements OnClickListener{
	
	private Context context;
	private ArrayList<Person> list;
	private LinkedList<Person> peopleToReturn;
	private PersonMapping finalMap;
	
	
	public GenerateController(Context context, ArrayList<Person> listPeople, PersonMapping p, LinkedList<Person> peopleToReturn) {
		this.context = context;
		this.list = listPeople;
		this.finalMap = p;
		this.peopleToReturn = peopleToReturn;
	}
	@Override
	public void onClick(View v) {
		if(list != null || !list.isEmpty()){
			AtomicInteger i = new AtomicInteger(0);
			list.forEach((Person p) -> {
				p.setPersonID(i.getAndIncrement());
				peopleToReturn.add(p);
			});
			finalMap = new PersonMapping(peopleToReturn);
			finalMap.generateUniqueMaps();
			finalMap.mapUsers();
		}
	}

}
