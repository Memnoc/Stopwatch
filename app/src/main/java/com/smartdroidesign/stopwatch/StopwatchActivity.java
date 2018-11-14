package com.smartdroidesign.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    // Save the state of the watch if it's about to be destroyed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    // If the activity's paused, stop the watch
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // If the activity's resumed, start the watch again if it was running previously
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // Starts the stop watch running when the Start button is clicked
    public void onClickStart(View view) {
        running = true;
    }

    // Stops the stop watch running when the Stop button is clicked
    public void onClickStop(View view) {
        running = false;
    }

    // Resets the stop watch running when the Reset button is clicked
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }


    // Sets the number of seconds on the timer
    private void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.UK, "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });


    }
}
