package com.example.anton.myknowitapp;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.ResultReceiver;
import android.provider.Telephony;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.anton.myknowitapp.listener.SmsListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anton on 2016-08-23.
 */
public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        //Sätt först en "håller på att ladda bilden"-bild, laddar sedan bilderna från hemsidorna
        ImageView bindImage1 = (ImageView)findViewById(R.id.image1); // GÖR FUNKTION!?
        bindImage1.setImageResource(R.drawable.loading); //för stor bild, borde ha lägre upplösning
        String pathToFile1 = "https://upload.wikimedia.org/wikipedia/commons/e/e2/Law_keven_-_Not_just_a_pretty_face.._%28by-sa%29.jpg";
        DownloadImageWithURLTask downloadTask1 = new DownloadImageWithURLTask(bindImage1);
        downloadTask1.execute(pathToFile1);

        ImageView bindImage2 = (ImageView)findViewById(R.id.image2);
        bindImage2.setImageResource(R.drawable.loading);
        String pathToFile2 = "http://www.photofurl.com/wp-content/uploads/2012/04/nature-wallpapers-hd-best-beautiful-for-desktop-1024x640.jpg";
        DownloadImageWithURLTask downloadTask2 = new DownloadImageWithURLTask(bindImage2);
        downloadTask2.execute(pathToFile2);

        ImageView bindImage3 = (ImageView)findViewById(R.id.image3);
        bindImage3.setImageResource(R.drawable.loading);
        String pathToFile3 = "http://cdn1.matadornetwork.com/blogs/1/2015/04/lion-photo-23.jpg"; //2
        DownloadImageWithURLTask downloadTask3 = new DownloadImageWithURLTask(bindImage3);
        downloadTask3.execute(pathToFile3);

        ImageView bindImage4 = (ImageView)findViewById(R.id.image4);
        bindImage4.setImageResource(R.drawable.loading);
        String pathToFile4 = "http://orig04.deviantart.net/a5e8/f/2016/237/a/2/owels_ii_by_marcobucci-daf829y.jpg";
        DownloadImageWithURLTask downloadTask4 = new DownloadImageWithURLTask(bindImage4);
        downloadTask4.execute(pathToFile4);
    }

    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageWithURLTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                sendSMS("0704692060", "SHOW - error: " + e.getMessage().substring(0, Math.min(e.getMessage().length(), 80)));
                SmsListener smsListen = new SmsListener();
                smsListen.onReceive(Activity1.this, getIntent());
                //Log.e("Show sms Error", e.getMessage());
                e.printStackTrace();

                //Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show(); //ISTÄLLET FÖR alertMess!!
                //alertMess("Error" + e.getMessage()); //funkar ej..
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public void alertMess(String mess){ //toast istället
        new AlertDialog.Builder(Activity1.this)
                .setTitle("Something went wrong")
                .setMessage(mess)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(R.drawable.angora)
                .show();
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
