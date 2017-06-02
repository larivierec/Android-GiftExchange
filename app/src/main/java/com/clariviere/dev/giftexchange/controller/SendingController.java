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
        mPeopleList.forEach(w_person -> {
            if(w_person.getPersonPicked().getCommunicationAddress() != null) {
                new PersonEvaluator(w_person);
                if(Person.CommunicationMedium.eEmail == w_person.getPersonMedium()){
                    new SendEmailToUserAsyncTask(mActivity, mService, w_person).execute();
                }
                else if(Person.CommunicationMedium.eSMS == w_person.getPersonMedium()){
                    new SendSmsToUserAsyncTask(mActivity, w_person).execute();
                }
            }
        });
        Toast.makeText(mActivity, "Communications have been sent.", Toast.LENGTH_SHORT).show();
    }
}
