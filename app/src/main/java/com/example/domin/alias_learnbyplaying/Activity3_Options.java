package com.example.domin.alias_learnbyplaying;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Activity3_Options extends AppCompatActivity {

    private Button button_set;
    private TextView textView_chrono, textView_goal_result;
    private SeekBar seekbar_goal, seekbar_chrono;
    int goal_result, chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        addListenerOnButtonSet();
        addListenerOnSeekBarGoal();
        addListenerOnSeekBarChrono();
    }

    public void addListenerOnSeekBarGoal(){
        seekbar_goal = (SeekBar)findViewById(R.id.seekBar_goal_result);
        textView_goal_result = (TextView)findViewById(R.id.textView_goal_result);
        seekbar_goal.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int tmp_progressgoal;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        tmp_progressgoal = i;
                        textView_goal_result.setText(String.valueOf(tmp_progressgoal + 5));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView_goal_result.setText(String.valueOf(tmp_progressgoal + 5));
                    }
                }
        );
    }

    public void addListenerOnSeekBarChrono() {
        seekbar_chrono = (SeekBar) findViewById(R.id.seekBar_chronometer);
        textView_chrono = (TextView) findViewById(R.id.textView_chronometer);
        seekbar_chrono.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int tmp_progresschrono;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        tmp_progresschrono = i;
                        textView_chrono.setText(String.valueOf(tmp_progresschrono + 10));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView_chrono.setText(String.valueOf(tmp_progresschrono + 10));
                    }
                }
        );
    }

    public void addListenerOnButtonSet() {
        button_set = (Button) findViewById(R.id.button_set);
        textView_chrono = (TextView) findViewById(R.id.textView_chronometer);
        textView_goal_result = (TextView) findViewById(R.id.textView_goal_result);
        button_set.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goal_result = Integer.parseInt(textView_goal_result.getText().toString());
                        chronometer = Integer.parseInt(textView_chrono.getText().toString());
                        Intent intent = new Intent(Activity3_Options.this, Activity4_Corpus.class);
                        intent.putExtra("GOAL", goal_result);
                        intent.putExtra("CHRONO", chronometer);
                        startActivity(intent);
                    }
                }
        );
    }
}
