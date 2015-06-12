package com.clariviere.dev.giftexchange;

import java.util.ArrayList;
import java.util.LinkedList;

import com.clariviere.dev.giftexchange.model.Person;
import com.clariviere.dev.giftexchange.model.PersonMapping;

import controller.GenerateController;
import controller.LoadStateListener;
import controller.SaveStateListener;
import android.support.v7.app.ActionBarActivity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	ArrayList<String> listPeople = new ArrayList<String>();
	LinkedList<Person> listToReturn = new LinkedList<Person>();
	PersonMapping finalMap = new PersonMapping(listToReturn);
	
	ArrayAdapter<String> adapter;
	final Context context = this;
	ListView peopleList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        peopleList = (ListView)findViewById(R.id.listOfPeople);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listPeople);
        peopleList.setAdapter(adapter);
        
        final Button buttonAdd = (Button) findViewById(R.id.btnAddP);
        buttonAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog personAddPopup = new Dialog(context);
				personAddPopup.setContentView(R.layout.persondialog);
				personAddPopup.setTitle("Add a person");
				
				Button btnAddPersonBtn = (Button)personAddPopup.findViewById(R.id.dialogBtnAdd);
				
				EditText personNameField = (EditText)personAddPopup.findViewById(R.id.txtPersonName);
				personNameField.setOnFocusChangeListener(new OnFocusChangeListener() {
					
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
				            personAddPopup.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				        }
					}
				});
				
				btnAddPersonBtn.setOnClickListener(new OnClickListener() {
					EditText personNameField = (EditText)personAddPopup.findViewById(R.id.txtPersonName);
					@Override
					public void onClick(View v) {
						if(personNameField.getText().toString().trim().length() != 0){
							listPeople.add(personNameField.getText().toString());
							adapter.notifyDataSetChanged();
							personAddPopup.dismiss();
						}
					}
				});
				personAddPopup.show();
			}
		});
        
        
        peopleList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(listToReturn.size() != 0 || listToReturn != null){
					final Dialog viewPersonPopup = new Dialog(context);
					viewPersonPopup.setContentView(R.layout.pickedperson_dialog);
					viewPersonPopup.setTitle("You chose...");
					
					Button btnDismiss = (Button) viewPersonPopup.findViewById(R.id.btnDismiss);
					TextView labelForName = (TextView) viewPersonPopup.findViewById(R.id.lblPickedPerson);
					
					Person p = listToReturn.get((int) id);
					labelForName.setText(p.getPersonPicked().getPersonName());
					
					btnDismiss.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							viewPersonPopup.dismiss();
						}
					});
					viewPersonPopup.show();
				}else{
					Toast.makeText(context, "You must generate the list first", Toast.LENGTH_LONG).show();
				}
			}
		}); 
        
        
        final Button buttonGenerate = (Button) findViewById(R.id.btnGenerateList);
        buttonGenerate.setOnClickListener(new GenerateController(this, listPeople,this.finalMap,this.listToReturn));
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_state) {
            new SaveStateListener(this.finalMap, this.listToReturn,context);
        }
        else if(id == R.id.load_state){
        	LoadStateListener load = new LoadStateListener(this.finalMap, this.listToReturn,context);
        	this.listToReturn = load.getListOfPeeps();
        	this.finalMap = load.getPersonMapping();
        	
        	for(Person p : listToReturn){
        		this.listPeople.add(p.getPersonName());
        	}
        	adapter.notifyDataSetChanged();
        }
        
        return super.onOptionsItemSelected(item);
    }
}
