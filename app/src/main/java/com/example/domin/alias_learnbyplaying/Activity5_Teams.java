package com.example.domin.alias_learnbyplaying;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

public class Activity5_Teams extends AppCompatActivity {
    private Button button_sign, button_play, button_apply, button_edit1, button_edit2, button_edit3, button_edit4, button_edit5, button_edit6;
    private EditText editTextTeam, editTextPlayer1, editTextPlayer2;
    private TextView textView1, textView2, textView3, textView4, textView5, textView6;
    String tmp_team, tmp_player1, tmp_player2 = null;
    int goal_result, chronometer;
    int attempt = 0;//index of the TeamsArray
    ArrayList<Team> Teams = new ArrayList<>();
    ArrayList<String> corpus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        goal_result = getIntent().getExtras().getInt("GOAL");
        chronometer = getIntent().getExtras().getInt("CHRONO");
        corpus = getIntent().getExtras().getStringArrayList("CORPUS");
        button_play = (Button) findViewById(R.id.button_play);
        button_sign = (Button) findViewById(R.id.button_sign_team);
        button_apply = (Button) findViewById(R.id.button_apply);
        button_edit1 = (Button) findViewById(R.id.button_edit1);
        button_edit2 = (Button) findViewById(R.id.button_edit2);
        button_edit3 = (Button) findViewById(R.id.button_edit3);
        button_edit4 = (Button) findViewById(R.id.button_edit4);
        button_edit5 = (Button) findViewById(R.id.button_edit5);
        button_edit6 = (Button) findViewById(R.id.button_edit6);
        editTextTeam = (EditText) findViewById(R.id.editText_team_name);
        editTextPlayer1 = (EditText) findViewById(R.id.editText_player1_name);
        editTextPlayer2 = (EditText) findViewById(R.id.editText_player2_name);
        textView1 = (TextView) findViewById(R.id.textView_team1);
        textView2 = (TextView) findViewById(R.id.textView_team2);
        textView3 = (TextView) findViewById(R.id.textView_team3);
        textView4 = (TextView) findViewById(R.id.textView_team4);
        textView5 = (TextView) findViewById(R.id.textView_team5);
        textView6 = (TextView) findViewById(R.id.textView_team6);
        button_play.setVisibility(View.GONE);
        button_apply.setVisibility(View.GONE);
        button_edit1.setVisibility(View.GONE);
        button_edit2.setVisibility(View.GONE);
        button_edit3.setVisibility(View.GONE);
        button_edit4.setVisibility(View.GONE);
        button_edit5.setVisibility(View.GONE);
        button_edit6.setVisibility(View.GONE);
        addListenerOnButtonSign();
        addListenerOnButtonPlay();
        addListenerOnButtonEdit1();
        addListenerOnButtonEdit2();
        addListenerOnButtonEdit3();
        addListenerOnButtonEdit4();
        addListenerOnButtonEdit5();
        addListenerOnButtonEdit6();
    }

    public void addListenerOnButtonSign(){
        final TextView[] textViews = {textView1, textView2, textView3, textView4, textView5, textView6};
        final Button[] Buttons = {button_edit1, button_edit2, button_edit3, button_edit4, button_edit5, button_edit6};
        final MediaPlayer click = MediaPlayer.create(this, R.raw.ok);
        button_sign.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tmp_team = editTextTeam.getText().toString();
                        tmp_player1 = editTextPlayer1.getText().toString();
                        tmp_player2 = editTextPlayer2.getText().toString();
                        if (tmp_team.length() < 1 || tmp_player1.length() < 1 ||tmp_player2.length() < 1){
                            Toast.makeText(Activity5_Teams.this, "Uhmmm, info missing...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            click.start();
                            textViews[attempt].setText(tmp_team);
                            Buttons[attempt].setVisibility(View.VISIBLE);
                            attempt++;
                            Teams.add(new Team(tmp_team, tmp_player1, tmp_player2));
                            editTextTeam.setText("");
                            editTextPlayer1.setText("");
                            editTextPlayer2.setText("");
                            if (attempt == 6) {
                                button_sign.setVisibility(View.GONE);
                            }
                            if (attempt == 2) {
                                button_play.setVisibility(View.VISIBLE);
                            }
                            hideKeyboard();
                        }
                    }
                }
        );
    }

    public void edit(final Button button, final TextView textView, final int attempt){
        final MediaPlayer click = MediaPlayer.create(this, R.raw.ok);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        click.start();
                        editTextTeam.setText(Teams.get(attempt).getName());
                        editTextPlayer1.setText(Teams.get(attempt).getPlayer1());
                        editTextPlayer2.setText(Teams.get(attempt).getPlayer2());
                        button_apply.setVisibility(View.VISIBLE);
                        button_apply.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        click.start();
                                        tmp_team = editTextTeam.getText().toString();
                                        tmp_player1 = editTextPlayer1.getText().toString();
                                        tmp_player2 = editTextPlayer2.getText().toString();
                                        Teams.get(attempt).setName(editTextTeam.getText().toString());
                                        Teams.get(attempt).setPlayer1(editTextPlayer1.getText().toString());
                                        Teams.get(attempt).setPlayer2(editTextPlayer2.getText().toString());
                                        textView.setText("");
                                        textView.setText(tmp_team);
                                        editTextTeam.setText("");
                                        editTextPlayer1.setText("");
                                        editTextPlayer2.setText("");
                                        button_apply.setVisibility(View.GONE);
                                        hideKeyboard();
                                    }
                                }
                        );
                    }
                }
        );
    }

    public void addListenerOnButtonEdit1(){ edit(button_edit1, textView1, 0); }
    public void addListenerOnButtonEdit2(){ edit(button_edit2, textView2, 1); }
    public void addListenerOnButtonEdit3(){ edit(button_edit3, textView3, 2); }
    public void addListenerOnButtonEdit4(){ edit(button_edit4, textView4, 3); }
    public void addListenerOnButtonEdit5(){ edit(button_edit5, textView5, 4); }
    public void addListenerOnButtonEdit6(){ edit(button_edit6, textView6, 5); }

    public void addListenerOnButtonPlay(){
        Collections.shuffle(Teams);
        final MediaPlayer click = MediaPlayer.create(this, R.raw.ok);
        button_play.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        click.start();
                        Intent intent = new Intent(Activity5_Teams.this, Activity6_Play.class);
                        intent.putExtra("GOAL", goal_result);
                        intent.putExtra("CHRONO", chronometer);
                        intent.putExtra("TEAMS", (ArrayList<Team>)Teams);
                        intent.putStringArrayListExtra("CORPUS", corpus);
                        startActivity(intent);
                    }
                }
        );
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
