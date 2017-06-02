package com.clariviere.dev.giftexchange.tasks;

import android.os.AsyncTask;

import com.clariviere.dev.giftexchange.activities.MainActivity;
import com.clariviere.dev.giftexchange.interfaces.ICommunicationModel;
import com.clariviere.dev.giftexchange.model.EmailModel;
import com.clariviere.dev.giftexchange.model.Person;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.gmail.Gmail;

import java.io.IOException;

public class SendEmailToUserAsyncTask extends AsyncTask<Void, Void, Void>{
    private MainActivity mActivity;
    private Gmail        mService;
    private Person       mPerson;
    private GmailSender mSender;

    public SendEmailToUserAsyncTask(MainActivity activity, Gmail service, Person e){
        this.mActivity = activity;
        this.mService = service;
        this.mPerson = e;
        this.mSender = new GmailSender();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{
            ICommunicationModel w_CommunicationModel = new EmailModel(mPerson.getCommunicationAddress(), mPerson.getPersonPicked().getCommunicationAddress(),
                    "Chosen Person", mPerson.getPersonPicked().getPersonName());

            mSender.setCommunicationModel(w_CommunicationModel);
            mSender.sendMessage(mService, "me");
        } catch(UserRecoverableAuthIOException e){
            e.printStackTrace();
            mActivity.startActivityForResult(e.getIntent(), 1001);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
