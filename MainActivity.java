package com.example.eggtimers;


import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    Button button;
    SeekBar seekBar;
    TextView textView;
    Boolean counterIsActive=false;
    CountDownTimer countDownTimer;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        imageView=(ImageView)findViewById(R.id.imageView);
        textView=(TextView)findViewById(R.id.textView);
        textView.setText("00:30");
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min=progress/60;
                int sec=progress-(min*60);
                String clockminutes=String.valueOf(min).length()==1?"0"+String.valueOf(min):String.valueOf(min);
                String clockseconds=String.valueOf(sec).length()==1?"0"+String.valueOf(sec): String.valueOf(sec);

                textView.setText(clockminutes+":"+clockseconds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void startTimer(View view)
    {
        if(counterIsActive==false) {
            counterIsActive = true;
            seekBar.setEnabled(false);
            button.setText("Stop");
            countDownTimer=new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000)   // extra 100 is added to make timer work correctly due to delay .
            {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {

                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    Toast.makeText(MainActivity.this, "Eggs Done !", Toast.LENGTH_LONG).show();
                    mediaPlayer.start();

                    imageView.animate().rotation(360f).setDuration(2000);


                }
            }.start();
        }
        else
        {
            resetTimer();
        }
    }

    public void resetTimer()
    {
        textView.setText("00:30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        button.setText("Start");
        seekBar.setEnabled(true);
        counterIsActive=false;

    }
    public void updateTimer(int secondsLeft)
    {
        int min=secondsLeft/60;
        int sec=secondsLeft-(min*60);
        String clockminutes=String.valueOf(min).length()==1?"0"+String.valueOf(min):String.valueOf(min);
        String clockseconds=String.valueOf(sec).length()==1?"0"+String.valueOf(sec): String.valueOf(sec);

        textView.setText(clockminutes+":"+clockseconds);

    }
}
