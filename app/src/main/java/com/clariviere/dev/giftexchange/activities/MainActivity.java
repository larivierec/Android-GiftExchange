package com.clariviere.dev.giftexchange.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import com.clariviere.dev.giftexchange.R;
import com.clariviere.dev.giftexchange.controller.SendEmailController;
import com.clariviere.dev.giftexchange.model.Person;
import com.clariviere.dev.giftexchange.model.PersonMapping;

import com.clariviere.dev.giftexchange.controller.GenerateController;
import com.clariviere.dev.giftexchange.controller.LoadStateListener;
import com.clariviere.dev.giftexchange.controller.SaveStateListener;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


public class MainActivity extends Activity {

    private ListView peopleList;
    private ArrayList<String> peopleListViewData = new ArrayList<>();
    private ArrayAdapter<String> peopleListViewAdapter;

    private ArrayList<Person> listOfPeople = new ArrayList<>();
    private LinkedList<Person> listToReturn = new LinkedList<>();
    private PersonMapping finalMap = new PersonMapping(listToReturn);


	private final Context context = this;



    //Used for GMail service

    private final int REQUEST_ACCOUNT = 1000;
    private final int REQUEST_AUTH = 1001;
    private final int REQUEST_GG_SERVICES = 1002;

    private GoogleAccountCredential mCredential;
    private Gmail mService;
    private String mSharedPrefAccName = "";
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();
    private final JsonFactory mJsonFactory = GsonFactory.getDefaultInstance();
    private final String[] mGmailScopes = {GmailScopes.GMAIL_COMPOSE};
    private final String[] accTypes = {"com.google"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(mGmailScopes))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(settings.getString(mSharedPrefAccName, null));

        mService = new Gmail.Builder(mTransport, mJsonFactory, mCredential)
                .setApplicationName("Gift Exchange")
                .build();

        if(mCredential.getSelectedAccount() == null)
            selectAccount();

        peopleList = (ListView)findViewById(R.id.listOfPeople);
        peopleListViewAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, peopleListViewData);
        peopleList.setAdapter(peopleListViewAdapter);
        
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
                    EditText personEmailField = (EditText)personAddPopup.findViewById(R.id.txtPersonEmail);
					@Override
					public void onClick(View v) {
						if(personNameField.getText().toString().trim().length() != 0){
                            Person p = new Person(personNameField.getText().toString(),
                                    personEmailField.getText().toString().toLowerCase());
							peopleListViewData.add(p.getPersonName());
                            listOfPeople.add(p);
							peopleListViewAdapter.notifyDataSetChanged();
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
                if (listToReturn.size() != 0) {
                    final Dialog viewPersonPopup = new Dialog(context);
                    viewPersonPopup.setContentView(R.layout.pickedperson_dialog);
                    viewPersonPopup.setTitle("You chose...");

                    Button btnDismiss = (Button) viewPersonPopup.findViewById(R.id.btnDismiss);
                    TextView labelForName = (TextView) viewPersonPopup.findViewById(R.id.lblPickedPerson);
                    if (listToReturn != null) {
                        Person p = listToReturn.get((int) id);
                        labelForName.setText(p.getPersonPicked().getPersonName());
                    }
                    btnDismiss.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewPersonPopup.dismiss();
                        }
                    });
                    viewPersonPopup.show();
                } else {
                    Toast.makeText(context, "You must generate the list first", Toast.LENGTH_LONG).show();
                }
            }
        });

        final Button buttonGenerate = (Button) findViewById(R.id.btnGenerateList);
        buttonGenerate.setOnClickListener(new GenerateController(this, listOfPeople, this.finalMap, this.listToReturn));

        final Button buttonSendEmail = (Button) findViewById(R.id.btn_sendEmail);
        buttonSendEmail.setOnClickListener(new SendEmailController(this, listToReturn, mService));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_ACCOUNT){
            if(resultCode == RESULT_OK || resultCode == RESULT_FIRST_USER){
                String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                mCredential.setSelectedAccountName(accountName);
                SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(mSharedPrefAccName, accountName);
                editor.apply();
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this,"Login / Selection cancelled", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == REQUEST_AUTH && resultCode != RESULT_OK) {
            selectAccount();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void selectAccount(){
        startActivityForResult(mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT);
    }

    private void requestAuthorization(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_state) {
            new SaveStateListener(this.finalMap, this.listToReturn, context);
        }
        else if(id == R.id.load_state){
        	LoadStateListener load = new LoadStateListener(this.finalMap, this.listToReturn, context);
        	this.listToReturn = load.getListOfPeople();
        	this.finalMap = load.getPersonMapping();
        	
        	for(Person p : listToReturn){
        		this.peopleListViewData.add(p.getPersonName());
        	}
        	peopleListViewAdapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}
