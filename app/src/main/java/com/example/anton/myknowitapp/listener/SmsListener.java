package com.example.anton.myknowitapp.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Anton on 2016-08-25.
 */
public class SmsListener extends BroadcastReceiver{

    private static final String TAG = SmsListener.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e(TAG, "Checking Sms");
        Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                    if (message.contains("SHOW")) { //message.subString(0,4) == "SHOW" borde funka också
                        Toast.makeText(context, message.substring(7, message.length()), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception");
            e.printStackTrace();
        }
    }
}

/*public class SmsReceiver extends BroadcastReceiver {

public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
            }
        }
    }

}*/