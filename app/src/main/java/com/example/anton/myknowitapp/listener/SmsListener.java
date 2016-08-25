package com.example.anton.myknowitapp.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

/**
 * Created by Anton on 2016-08-25.
 */
public class SmsListener extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
            }
        }
    }

    /*@Override
    public void onReceive(Context context, Intent intent, String messageBody) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                messageBody = smsMessage.getMessageBody();
            }
        }
    }

    public String getString(Context context, Intent intent){
        String messageBody = "";
        onReceive(context, intent, messageBody);
        return messageBody;
    }*/

}