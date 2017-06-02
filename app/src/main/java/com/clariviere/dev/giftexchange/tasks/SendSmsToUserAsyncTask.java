package com.clariviere.dev.giftexchange.tasks;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.clariviere.dev.giftexchange.abstract_classes.CommunicationModel;
import com.clariviere.dev.giftexchange.interfaces.ICommunicationModel;
import com.clariviere.dev.giftexchange.model.Person;
import com.clariviere.dev.giftexchange.model.SMSModel;

public class SendSmsToUserAsyncTask extends AsyncTask<Void, Void, Void>{
    private Person       mPerson;
    private SmsManager   mSmsManager;
    private Activity     mActivity;
    private String SENT = "sent";
    public SendSmsToUserAsyncTask(Activity iActivity, Person e){
        this.mPerson = e;
        this.mActivity = iActivity;
        mSmsManager = SmsManager.getDefault();
    }

    @Override
    protected Void doInBackground(Void... params) {
        final ICommunicationModel wModel = new SMSModel(mPerson.getCommunicationAddress(), "You picked: " + mPerson.getPersonPicked().getPersonName());

        try{
            Intent wSentIntent = new Intent(SENT);
            PendingIntent wPendingSentIntent = PendingIntent.getBroadcast(
                    mActivity.getApplicationContext(), 0, wSentIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            mActivity.registerReceiver(new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    String wResult = "";

                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            wResult = "Transmission successful";
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            wResult = "Transmission failed";
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            wResult = "Radio off";
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            wResult = "No PDU defined";
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            wResult = "No service";
                            break;
                    }
                    Log.d("SmsManager result", wResult);
                }
            }, new IntentFilter(SENT));

            mSmsManager.sendTextMessage(wModel.getCommunicationTo(), null
                    , wModel.getCommunicationMessage()
                    , wPendingSentIntent, null);
        }
        catch(IllegalArgumentException illegalArg){
            Log.d("Communication address", illegalArg.getMessage());
        }
        return null;
    }
}
