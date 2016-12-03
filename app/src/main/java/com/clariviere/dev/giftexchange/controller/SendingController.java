package com.clariviere.dev.giftexchange.controller;


import android.view.View;
import android.widget.Toast;

import com.clariviere.dev.giftexchange.activities.MainActivity;
import com.clariviere.dev.giftexchange.model.Person;
import com.clariviere.dev.giftexchange.model.PersonEvaluator;
import com.clariviere.dev.giftexchange.tasks.SendEmailToUserAsyncTask;
import com.clariviere.dev.giftexchange.tasks.SendSmsToUserAsyncTask;
import com.google.api.services.gmail.Gmail;

import java.util.List;

public class SendingController implements View.OnClickListener {

    private MainActivity mActivity;
    private List<Person> mPeopleList;
    private Gmail        mService;

    public SendingController(MainActivity activity, List<Person> peopleList, Gmail service){
        this.mActivity = activity;
        this.mPeopleList = peopleList;
        this.mService = service;
    }

    @Override
    public void onClick(View v) {
        for(Person wPerson : mPeopleList){
            if(wPerson.getPersonPicked().getCommunicationAddress() != null) {
                new PersonEvaluator(wPerson);
                if(Person.CommunicationMedium.eEmail == wPerson.getPersonMedium()){
                    new SendEmailToUserAsyncTask(mActivity, mService, wPerson).execute();
                }
                else if(Person.CommunicationMedium.eSMS == wPerson.getPersonMedium()){
                    new SendSmsToUserAsyncTask(mActivity, wPerson).execute();
                }

            }
        }
        Toast.makeText(mActivity, "Communications have been sent.", Toast.LENGTH_SHORT).show();
    }
}
