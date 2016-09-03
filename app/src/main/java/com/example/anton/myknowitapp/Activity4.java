package com.example.anton.myknowitapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.anton.myknowitapp.listener.SmsListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Anton on 2016-08-23.
 */
public class Activity4 extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;

    String phoneNr;
    String textMessage;

    FileOutputStream outputStream;
    FileInputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);

        textView1 = (TextView) findViewById(R.id.phoneNr);
        textView2 = (TextView) findViewById(R.id.textMess);

        //Hitta SD kortet
        File sdcard = Environment.getExternalStorageDirectory();

        //Hitta filerna
        File dir = new File (sdcard.getAbsolutePath() + "/myknowitapp");
        dir.mkdirs();
        File filen1 = new File(dir, "phoneNr");
        File filen2 = new File(dir, "textMessage");

        //Läs texten
        StringBuilder text1 = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filen1));
            String line;

            while ((line = br.readLine()) != null) {
                text1.append(line);
                text1.append('\n');
            }
            br.close();
            phoneNr = text1.toString();
        }
        catch (IOException e) {
            Log.e("Error", e.getMessage());
            sendSMS("0704692060", "SHOW - error: " + e.getMessage().substring(0, Math.min(e.getMessage().length(), 80)));
            SmsListener smsListen = new SmsListener();
            smsListen.onReceive(Activity4.this, getIntent()); //funkar ej som jag tänkt
            //Log.e("Show sms Error", e.getMessage());
            e.printStackTrace();
            //alertMess("Error" + e.getMessage()); //funkar ej..
        }

        StringBuilder text2 = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filen2));
            String line;

            while ((line = br.readLine()) != null) {
                text2.append(line);
                text2.append('\n');
            }
            br.close();
            textMessage = text2.toString();
        }
        catch (IOException e) {
            Log.e("Error", e.getMessage());
            sendSMS("0704692060", "SHOW - error: " + e.getMessage().substring(0, Math.min(e.getMessage().length(), 80)));
            SmsListener smsListen = new SmsListener();
            smsListen.onReceive(Activity4.this, getIntent()); //funkar ej som jag tänkt
            //Log.e("Show sms Error", e.getMessage());
            e.printStackTrace();
            //alertMess("Error" + e.getMessage()); //funkar ej..
        }

        textView1.setText(phoneNr);
        textView2.setText(textMessage);

        textView1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    try {

                        File sdCard = Environment.getExternalStorageDirectory();
                        File dir = new File (sdCard.getAbsolutePath() + "/myknowitapp");
                        dir.mkdirs();
                        File filen1 = new File(dir, "phoneNr");

                        FileOutputStream f1 = new FileOutputStream(filen1);
                        f1.write(s.toString().getBytes());
                        f1.close();
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        sendSMS("0704692060", "SHOW - error: " + e.getMessage().substring(0, Math.min(e.getMessage().length(), 80)));
                        SmsListener smsListen = new SmsListener();
                        smsListen.onReceive(Activity4.this, getIntent()); //funkar ej som jag tänkt
                        //Log.e("Show sms Error", e.getMessage());
                        e.printStackTrace();
                        //alertMess("Error" + e.getMessage()); //funkar ej..
                    }
                }
            }
        });

        textView2.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    try {
                        File sdCard = Environment.getExternalStorageDirectory();
                        File dir = new File (sdCard.getAbsolutePath() + "/myknowitapp");
                        dir.mkdirs();
                        File filen2 = new File(dir, "textMessage");

                        FileOutputStream f2 = new FileOutputStream(filen2);
                        f2.write(s.toString().getBytes());
                        f2.close();
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        sendSMS("0704692060", "SHOW - error: " + e.getMessage().substring(0, Math.min(e.getMessage().length(), 80)));
                        SmsListener smsListen = new SmsListener();
                        smsListen.onReceive(Activity4.this, getIntent());
                        //Log.e("Show sms Error", e.getMessage());
                        e.printStackTrace();
                        //alertMess("Error" + e.getMessage()); //funkar ej..
                    }
                }
            }
        });
    }

    public void sendMessage(View v) {
        phoneNr = textView1.getText().toString();
        textMessage = textView2.getText().toString();

        //Skicka meddelandet
        Uri uri = Uri.parse("smsto:" + phoneNr);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", textMessage);
        startActivity(it);
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
