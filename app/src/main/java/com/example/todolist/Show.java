package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Show extends AppCompatActivity {
    final int delay_time = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        // Delayed transition to main activity
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Show.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish this activity to prevent going back to it on back press
            }
        }, delay_time);
    }
}