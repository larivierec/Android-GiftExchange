package com.clariviere.dev.giftexchange.controller;


import android.view.View;
import android.widget.Toast;

import com.clariviere.dev.giftexchange.activities.MainActivity;
import com.clariviere.dev.giftexchange.model.Email;
import com.clariviere.dev.giftexchange.model.Person;
import com.clariviere.dev.giftexchange.tasks.SendEmailToUserAsyncTask;
import com.google.api.services.gmail.Gmail;

import java.util.List;

public class SendEmailController implements View.OnClickListener{

    private MainActivity mActivity;
    private List<Person> mPeopleList;
    private Gmail        mService;

    public SendEmailController(MainActivity activity, List<Person> peopleList, Gmail service){
        this.mActivity = activity;
        this.mPeopleList = peopleList;
        this.mService = service;
    }

    @Override
    public void onClick(View v) {
        for(Person e : mPeopleList){
            if(e.getPersonPicked().getEmail() != null)
                new SendEmailToUserAsyncTask(mActivity, mService, e).execute();
        }
        Toast.makeText(mActivity, "Emails have been sent.", Toast.LENGTH_SHORT).show();
    }
}
