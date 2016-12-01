package com.clariviere.dev.giftexchange.tasks;

import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.util.Log;

import com.clariviere.dev.giftexchange.activities.MainActivity;
import com.clariviere.dev.giftexchange.model.EmailModel;
import com.clariviere.dev.giftexchange.model.Person;
import com.clariviere.dev.giftexchange.model.SMSModel;

public class SendSmsToUserAsyncTask extends AsyncTask<Void, Void, Void>{
    private Person       mPerson;
    private SmsManager   mSmsManager;

    public SendSmsToUserAsyncTask(Person e){
        this.mPerson = e;
        mSmsManager = SmsManager.getDefault();
    }

    @Override
    protected Void doInBackground(Void... params) {
        SMSModel wModel = new SMSModel(mPerson.getCommunicationAddress(), "You picked: " + mPerson.getPersonPicked().getPersonName());

        try{
            mSmsManager.sendTextMessage(wModel.getCommunicationTo(), null
                    , wModel.getCommunicationMessage()
                    , null, null);
        }
        catch(IllegalArgumentException illegalArg){
            Log.d("Communication address", illegalArg.getMessage());
        }
        return null;
    }
}
