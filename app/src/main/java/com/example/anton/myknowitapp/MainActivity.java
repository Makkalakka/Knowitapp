package com.example.anton.myknowitapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the first task button */
    public void ActivityOne(View view) {
        startActivity(new Intent(MainActivity.this, Activity1.class));
    }

    /** Called when the user clicks the second task button */
    public void ActivityTwo(View view) {
        startActivity(new Intent(MainActivity.this, Activity2.class));
    }

    /** Called when the user clicks the third task button */
    public void ActivityThree(View view) {
        startActivity(new Intent(MainActivity.this, Activity3.class));
    }

    /** Called when the user clicks the first task button */
    public void ActivityFour(View view) {
        startActivity(new Intent(MainActivity.this, Activity4.class));
    }
}
