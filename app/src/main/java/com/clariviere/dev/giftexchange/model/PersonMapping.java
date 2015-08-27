package com.clariviere.dev.giftexchange.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class PersonMapping implements Serializable{

	private static final long serialVersionUID = 1L;

	private LinkedList<Person> localMappedList = new LinkedList<>();
	
	private Map<Integer,Person> personMapping = new HashMap<>();
	private Map<Integer,Integer> idMapping = new HashMap<>();

	private Random seededObj = new Random();
	
	public PersonMapping(LinkedList<Person> list){
		this.localMappedList = list;
	}
	
	public void generateUniqueMaps(){	
		HashSet<Integer> randomNumbers = new HashSet<Integer>(localMappedList.size() - 1);
		idMapping = new HashMap<>();
		personMapping = new HashMap<>();
		int tempNumber = seededObj.nextInt();
		
		
		for(int i = 0; i < localMappedList.size() ; i++){
			boolean added = false;
			personMapping.put(i, this.localMappedList.get(i));
			while(!added){
				tempNumber = seededObj.nextInt(localMappedList.size());
				if(!idMapping.containsValue(tempNumber)){
					idMapping.put(i, tempNumber);
					added = true;
				}
			}
		}
		verify();
	}
	
	public void verify(){
		Iterator it = idMapping.entrySet().iterator();

		while(it.hasNext()){
			Map.Entry<Integer, Integer> pairs = (Map.Entry<Integer, Integer>)it.next();
			if(pairs.getKey().equals(pairs.getValue())){
				generateUniqueMaps();
			}
		}
	}
	
	public void mapUsers(){
		for(int i = 0; i < personMapping.size() ; i++){
			personMapping.get(i).setPersonPicked(personMapping.get(idMapping.get(i)));
		}
	}
	
	public String toString(){
		String temp = "";
		for(int i = 0; i < personMapping.size() ; i++){
			temp += "Person: " + personMapping.get(i).getPersonName() + " has picked: " + personMapping.get(idMapping.get(i)).getPersonName() + "\n";
		}
		return temp;
	}
	
}
