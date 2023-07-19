package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

    private TextView stopwatchTextView;
    private Button startButton, stopButton, holdButton;
    private final Handler handler = new Handler();
    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updateTime = 0L;
    private final Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = System.currentTimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updateTime/1000);
            int mins = secs / 60;
            secs %= 60;
            int milliseconds = (int) (updateTime % 1000);
            stopwatchTextView.setText(String.format("%02d:%02d:%02d", mins, secs, milliseconds));
            handler.postDelayed(this, 0);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopwatchTextView = findViewById(R.id.stopwatchTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        holdButton = findViewById(R.id.holdButton);

//        startButton.setOnClickListener(this);
//        stopButton.setOnClickListener(this);
//        holdButton.setOnClickListener(this);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartClicked();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStopClicked();
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHoldClicked();
            }
        });
    }




    private void onStartClicked(){
        startTime = System.currentTimeMillis();
        handler.postDelayed(updateTimeRunnable,0);
    }

    private void onStopClicked(){
        timeSwapBuff += timeInMilliseconds;
        handler.removeCallbacks(updateTimeRunnable);
    }

    private void onHoldClicked(){
        timeSwapBuff += timeInMilliseconds;
        handler.removeCallbacks(updateTimeRunnable);
        stopwatchTextView.setText("00:00:00");
    }
}