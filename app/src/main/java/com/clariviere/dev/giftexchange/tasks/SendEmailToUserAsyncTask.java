package com.clariviere.dev.giftexchange.tasks;

import android.os.AsyncTask;

import com.clariviere.dev.giftexchange.activities.MainActivity;
import com.clariviere.dev.giftexchange.model.Email;
import com.clariviere.dev.giftexchange.model.Person;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.services.gmail.Gmail;

import java.io.IOException;

import javax.mail.MessagingException;

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
            /*GmailUtil.sendMessage(mService, "me", GmailUtil.
                    createEmail(mPerson.getPersonPicked().getEmail(),
                            mPerson.getEmail(),
                            "Chosen person",
                            mPerson.getPersonPicked().getPersonName()));*/
            Email localEmail = new Email(mPerson.getEmail(), mPerson.getPersonPicked().getEmail(),
                    "Chosen Person", mPerson.getPersonPicked().getPersonName());
            GmailUtil.sendMessage(mService, "me", localEmail);
        } catch(UserRecoverableAuthIOException e){
            e.printStackTrace();
            mActivity.startActivityForResult(e.getIntent(), 1001);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
