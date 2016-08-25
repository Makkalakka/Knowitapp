package com.example.anton.myknowitapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Anton on 2016-08-23.
 */
public class Activity3 extends AppCompatActivity {

    private TextView textView1;
    String phoneNr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        textView1 = (TextView) findViewById(R.id.phoneNr);

        //Hämta det globala telefon nummret
        final PhoneNumber mApp = (PhoneNumber) getApplicationContext();
        if(mApp.getPhoneNr() != null) {
            //Sätt textviewn med detta nummer om det finns
            textView1.setText(mApp.getPhoneNr());
        }
    }

    public void callNumber(View v) {
        //Hämta numret
        phoneNr = textView1.getText().toString();
        //Ring detta nummer
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNr, null)));
    }
}
