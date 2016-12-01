package com.clariviere.dev.giftexchange.tasks;

import android.os.AsyncTask;

import com.clariviere.dev.giftexchange.activities.MainActivity;
import com.clariviere.dev.giftexchange.model.EmailModel;
import com.clariviere.dev.giftexchange.model.Person;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.gmail.Gmail;

import java.io.IOException;

public class SendEmailToUserAsyncTask extends AsyncTask<Void, Void, Void>{
    private MainActivity mActivity;
    private Gmail        mService;
    private Person       mPerson;

    public SendEmailToUserAsyncTask(MainActivity activity, Gmail service, Person e){
        this.mActivity = activity;
        this.mService = service;
        this.mPerson = e;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{
            EmailModel localEmailModel = new EmailModel(mPerson.getCommunicationAddress(), mPerson.getPersonPicked().getCommunicationAddress(),
                    "Chosen Person", mPerson.getPersonPicked().getPersonName());
            GmailUtil.sendMessage(mService, "me", localEmailModel);
        } catch(UserRecoverableAuthIOException e){
            e.printStackTrace();
            mActivity.startActivityForResult(e.getIntent(), 1001);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
