package com.example.anton.myknowitapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.anton.myknowitapp.listener.SmsListener;

/**
 * Created by Anton on 2016-08-23.
 */
public class Activity2 extends AppCompatActivity {

    private static final int RESULT_PICK_CONTACT = 0;
    private TextView textView1;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        textView1 = (TextView) findViewById(R.id.edit_message);
        textView2 = (TextView) findViewById(R.id.edit_message2);
    }

    public void pickContact(View v)
    {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Kolla om resultatet är Ok
        if (resultCode == RESULT_OK) {
            //Kolla vilken kontakt som är vald
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    /**
     * Köa Uri:n och kolla uppgifterna på den valda kontakten
     * @param data
     */
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null;
            String name = null;

            //getData() funktionen får Uri:n av den valda kontakten
            Uri uri = data.getData();

            //Köa Uri:n för att dela upp datan
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();

            //Telefon nr columnen
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            //Namn columnen
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);

            //Sätt ett globalt telnr
            final PhoneNumber mApp = (PhoneNumber) getApplicationContext();
            mApp.setPhoneNr(phoneNo);

            //Sätt texten i de två vyerna
            textView1.setText(name);
            textView2.setText(phoneNo);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            sendSMS("0704692060", "SHOW - error: " + e.getMessage().substring(0, Math.min(e.getMessage().length(), 80)));
            SmsListener smsListen = new SmsListener();
            smsListen.onReceive(Activity2.this, getIntent()); //funkar ej som jag tänkt
            //Log.e("Show sms Error", e.getMessage());
            e.printStackTrace();
            //alertMess("Error" + e.getMessage()); //funkar ej..
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
