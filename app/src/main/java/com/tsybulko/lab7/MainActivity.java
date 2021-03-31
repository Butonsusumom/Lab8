package com.tsybulko.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final static int ONE_SECOND = 1000;
    public final static int TIMER_PERIOD = 10000;

    private Button button;
    private ProgressBar progressBar;
    private TextView textTimer;
    private Timer timerTask;
    private int i = 0;

    private CountDownTimer countDownTimer = new CountDownTimer(TIMER_PERIOD,ONE_SECOND) {
        @Override
        public void onTick(long millisUntilFinished) {
            Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
            i++;
            textTimer.setText(String.valueOf(TIMER_PERIOD/ONE_SECOND-i));
            progressBar.setProgress((int)i*100/(TIMER_PERIOD/ONE_SECOND));
            //i++;
        }

        @Override
        public void onFinish() {
            i++;
            textTimer.setText(String.valueOf(TIMER_PERIOD/ONE_SECOND-i));
            progressBar.setProgress((int)i*100/(TIMER_PERIOD/ONE_SECOND));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        textTimer = findViewById(R.id.textTimer);
        button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerTask != null && timerTask.getStatus() != AsyncTask.Status.FINISHED)
                    timerTask.cancel(false);
                timerTask = (Timer) new Timer().execute();
            }
        });
    }

    public class Timer extends AsyncTask<Integer, Integer, String> {

         @Override
         protected String doInBackground(Integer... params) {
             countDownTimer.start();
             return "Task Completed.";
         }
         @Override
         protected void onPostExecute(String result) {
             Log.d("Over","Over");
             i=0;
             textTimer.setText("");
             progressBar.setProgress(0);
         }
         @Override
         protected void onPreExecute() {
             countDownTimer.cancel();
             i = 0;
             progressBar.setProgress(i);

         }
     }
}