package com.example.domin.alias_learnbyplaying;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class Activity6_Teams extends AppCompatActivity {
    private Button button_sign, button_play, button_apply;
    private ImageButton button_edit1, button_edit2, button_edit3, button_edit4, button_edit5, button_edit6;
    private ImageButton button_del1, button_del2, button_del3, button_del4, button_del5, button_del6;
    private EditText editTextTeam, editTextPlayer1, editTextPlayer2;
    private TextView textView1, textView2, textView3, textView4, textView5, textView6;
    String tmp_team, tmp_player1, tmp_player2 = null;
    int goal_result, chronometer;
    int attempt = 0;//index of the TeamsArray
    int current_size = 0;
    ArrayList<Team> Teams = new ArrayList<>();
    ArrayList<String> corpus = new ArrayList<>();
    SoundPool mSoundPool;
    int clickId, delId, errId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        goal_result = getIntent().getExtras().getInt("GOAL");
        chronometer = getIntent().getExtras().getInt("CHRONO");
        corpus = getIntent().getExtras().getStringArrayList("CORPUS");
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        clickId = mSoundPool.load(this, R.raw.ok, 1);
        errId = mSoundPool.load(this, R.raw.err, 1);
        delId = mSoundPool.load(this, R.raw.del, 1);
        button_play = (Button) findViewById(R.id.button_play);
        button_sign = (Button) findViewById(R.id.button_sign_team);
        button_apply = (Button) findViewById(R.id.button_apply);
        button_edit1 = (ImageButton) findViewById(R.id.button_edit1);
        button_edit2 = (ImageButton) findViewById(R.id.button_edit2);
        button_edit3 = (ImageButton) findViewById(R.id.button_edit3);
        button_edit4 = (ImageButton) findViewById(R.id.button_edit4);
        button_edit5 = (ImageButton) findViewById(R.id.button_edit5);
        button_edit6 = (ImageButton) findViewById(R.id.button_edit6);
        button_del1 = (ImageButton) findViewById(R.id.button_del1);
        button_del2 = (ImageButton) findViewById(R.id.button_del2);
        button_del3 = (ImageButton) findViewById(R.id.button_del3);
        button_del4 = (ImageButton) findViewById(R.id.button_del4);
        button_del5 = (ImageButton) findViewById(R.id.button_del5);
        button_del6 = (ImageButton) findViewById(R.id.button_del6);
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
        button_del1.setVisibility(View.GONE);
        button_del2.setVisibility(View.GONE);
        button_del3.setVisibility(View.GONE);
        button_del4.setVisibility(View.GONE);
        button_del5.setVisibility(View.GONE);
        button_del6.setVisibility(View.GONE);
        addListenerOnButtonSign();
        addListenerOnButtonPlay();
        addListenerOnButtonEdit1();
        addListenerOnButtonEdit2();
        addListenerOnButtonEdit3();
        addListenerOnButtonEdit4();
        addListenerOnButtonEdit5();
        addListenerOnButtonEdit6();
        addListenerOnButtonDel1();
        addListenerOnButtonDel2();
        addListenerOnButtonDel3();
        addListenerOnButtonDel4();
        addListenerOnButtonDel5();
        addListenerOnButtonDel6();
    }

    public void addListenerOnButtonSign(){
        final TextView[] textViews = {textView1, textView2, textView3, textView4, textView5, textView6};
        final ImageButton[] editButtons = {button_edit1, button_edit2, button_edit3, button_edit4, button_edit5, button_edit6};
        final ImageButton[] delButtons = {button_del1, button_del2, button_del3, button_del4, button_del5, button_del6};
        button_sign.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tmp_team = editTextTeam.getText().toString();
                        tmp_player1 = editTextPlayer1.getText().toString();
                        tmp_player2 = editTextPlayer2.getText().toString();
                        if (tmp_team.length() < 1 || tmp_player1.length() < 1 ||tmp_player2.length() < 1){
                            Toast.makeText(Activity6_Teams.this, "Uhmmm, info missing...", Toast.LENGTH_SHORT).show();
                            mSoundPool.play(errId,1,1,1,0,1);

                        }
                        else if (tmp_team.length() > 15 || tmp_player1.length() > 15 || tmp_player2.length() > 15){
                            Toast.makeText(Activity6_Teams.this, "Sorry, maximum length is 15", Toast.LENGTH_SHORT).show();
                            mSoundPool.play(errId,1,1,1,0,1);
                        }
                        else {
                            mSoundPool.play(clickId,1,1,1,0,1);
                            textViews[attempt].setText(tmp_team);
                            editButtons[attempt].setVisibility(View.VISIBLE);
                            delButtons[attempt].setVisibility(View.VISIBLE);
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

    public void edit(final ImageButton button, final TextView textView, final int index){
        final ImageButton[] editButtons = {button_edit1, button_edit2, button_edit3, button_edit4, button_edit5, button_edit6};
        final ImageButton[] delButtons = {button_del1, button_del2, button_del3, button_del4, button_del5, button_del6};
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSoundPool.play(clickId,1,1,1,0,1);
                        editTextTeam.setText(Teams.get(index).getName());
                        editTextPlayer1.setText(Teams.get(index).getPlayer1());
                        editTextPlayer2.setText(Teams.get(index).getPlayer2());
                        button_sign.setVisibility(View.GONE);
                        button_play.setVisibility(View.GONE);
                        for (int i = 0; i < Teams.size(); i++){
                            delButtons[i].setVisibility(View.GONE);
                            editButtons[i].setVisibility(View.GONE);
                        }
                        button_apply.setVisibility(View.VISIBLE);
                        button_apply.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mSoundPool.play(clickId,1,1,1,0,1);
                                        tmp_team = editTextTeam.getText().toString();
                                        tmp_player1 = editTextPlayer1.getText().toString();
                                        tmp_player2 = editTextPlayer2.getText().toString();
                                        Teams.get(index).setName(editTextTeam.getText().toString());
                                        Teams.get(index).setPlayer1(editTextPlayer1.getText().toString());
                                        Teams.get(index).setPlayer2(editTextPlayer2.getText().toString());
                                        textView.setText("");
                                        textView.setText(tmp_team);
                                        editTextTeam.setText("");
                                        editTextPlayer1.setText("");
                                        editTextPlayer2.setText("");
                                        button_apply.setVisibility(View.GONE);
                                        if (Teams.size() >= 2) { button_play.setVisibility(View.VISIBLE); }
                                        button_sign.setVisibility(View.VISIBLE);
                                        for (int i = 0; i < Teams.size(); i++) {
                                            delButtons[i].setVisibility(View.VISIBLE);
                                            editButtons[i].setVisibility(View.VISIBLE);
                                        }
                                        hideKeyboard();
                                    }
                                }
                        );
                    }
                }
        );
    }

    public void del(final ImageButton button, final TextView textView, final int index){
        final TextView[] textViews = {textView1, textView2, textView3, textView4, textView5, textView6};
        final ImageButton[] editButtons = {button_edit1, button_edit2, button_edit3, button_edit4, button_edit5, button_edit6};
        final ImageButton[] delButtons = {button_del1, button_del2, button_del3, button_del4, button_del5, button_del6};
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSoundPool.play(delId,1,1,1,0,1);
                        Teams.remove(index);
                        textViews[index].setText("");
                        editButtons[index].setVisibility(View.GONE);
                        delButtons[index].setVisibility(View.GONE);
                        attempt--;
                        button_sign.setVisibility(View.VISIBLE);
                        if (attempt == 1) { button_play.setVisibility(View.GONE); }
                        current_size = Teams.size();
                        for (int i = 0; i < attempt + 1; i++) {
                            textViews[i].setText("");
                            editButtons[i].setVisibility(View.GONE);
                            delButtons[i].setVisibility(View.GONE);
                        }
                        for (int i = 0; i < current_size; i++){
                            textViews[i].setText(Teams.get(i).getName());
                            editButtons[i].setVisibility(View.VISIBLE);
                            delButtons[i].setVisibility(View.VISIBLE);
                        }
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
    public void addListenerOnButtonDel1(){ del(button_del1, textView1, 0); }
    public void addListenerOnButtonDel2(){ del(button_del2, textView2, 1); }
    public void addListenerOnButtonDel3(){ del(button_del3, textView3, 2); }
    public void addListenerOnButtonDel4(){ del(button_del4, textView4, 3); }
    public void addListenerOnButtonDel5(){ del(button_del5, textView5, 4); }
    public void addListenerOnButtonDel6(){ del(button_del6, textView6, 5); }

    public void addListenerOnButtonPlay(){
        button_play.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSoundPool.play(clickId,1,1,1,0,1);
                        Intent intent = new Intent(Activity6_Teams.this, Activity7_Play.class);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Activity6_Teams.this, Activity5_Corpus.class);
        intent.putExtra("GOAL", goal_result);
        intent.putExtra("CHRONO", chronometer);
        startActivity(intent);
    }
}