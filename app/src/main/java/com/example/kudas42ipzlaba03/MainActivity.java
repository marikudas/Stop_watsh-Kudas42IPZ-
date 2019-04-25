package com.example.kudas42ipzlaba03;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView pole;
    Handler obrabotka;
    private int seconds = 0;
    boolean runing = false;
    boolean w_run = false;
    Runnable r = new Runnable() {
        @Override
        public void run() {
            if(runing)
            {
                seconds++;
            }

            int sek = seconds%60;
            int min = seconds%3600/60;
            int chas = seconds/3600;

            String vivod = String.format("%d.%02d.%02d", chas,min,sek);
            pole.setText(vivod);
            obrabotka.postDelayed(this,1000);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        runing = w_run;
        obrabotka.post(r);
    }

    @Override
    protected void onStop() {
        super.onStop();
        w_run = runing;
        runing = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("runing", runing);
        outState.putBoolean("w_run", w_run);
        outState.putInt("seconds", seconds);
        obrabotka.removeCallbacks(r);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pole = findViewById(R.id.textView);
        if(savedInstanceState != null)
        {
            w_run = savedInstanceState.getBoolean("w_run");
            runing = savedInstanceState.getBoolean("runing");
            seconds = savedInstanceState.getInt("seconds");
        }
        obrabotka = new Handler();
    }

    public void StartClick(View start)
    {
        runing = true;
    }

    public void PauseClick(View pause)
    {
        runing = false;
    }

    public void ResetClick(View res)
    {
        seconds = 0;
        pole.setText(R.string.timer);
    }
}
