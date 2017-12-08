package com.example.domin.alias_learnbyplaying;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Activity6_Play extends AppCompatActivity {

    private TextView score_textview, term, score_txt, my_chronometer;
    private Button correct, wrong;
    int score, goal_result, chronometer;
    int attempt = 0;
    CountDownTimer timer;
    ArrayList<String> corpus = new ArrayList<>();
    ArrayList<Team> Teams = new ArrayList<>();
    ArrayList<Team> Teams_Sorted = new ArrayList<>(Teams.size());
    ArrayList<EndOfTurnTerms> EoTTerms = new ArrayList<>();
    boolean emptyCorpus, play = false;
    MediaPlayer mpcorrect, mpwrong, mpdrama, mpend, mpshotgun, mpwin, mpclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        goal_result = getIntent().getExtras().getInt("GOAL");
        chronometer = getIntent().getExtras().getInt("CHRONO");
        corpus = getIntent().getExtras().getStringArrayList("CORPUS");
        Teams = (ArrayList<Team>)getIntent().getSerializableExtra("TEAMS");
        Collections.shuffle(Teams);
        newAttempt();
        addListenerOnButtonCorrect();
        addListenerOnButtonWrong();
    }

    public int turn(int attempt, ArrayList<Team> Teams) { return (attempt % Teams.size()); }

    public String getTeam(int attempt, ArrayList<Team> Teams) { return (Teams.get(turn(attempt, Teams)).getName()); }

    public String getReader(int attempt, ArrayList<Team> Teams){
        if ((attempt / Teams.size()) % 2 == 0) { return (Teams.get(turn(attempt, Teams)).getPlayer1()); }
        else{ return (Teams.get(turn(attempt, Teams)).getPlayer2()); }
    }

    public String getListener(int attempt, ArrayList<Team> Teams){
        if ((attempt / Teams.size()) % 2 == 0) { return (Teams.get(turn(attempt, Teams)).getPlayer2()); }
        else{ return (Teams.get(turn(attempt, Teams)).getPlayer1()); }
    }

    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(Activity6_Play.this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure you want to quit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mpclick != null) {
                    mpclick.stop();
                    mpclick.release();
                }
                mpclick = MediaPlayer.create(Activity6_Play.this, R.raw.ok);
                mpclick.start();
                mpdrama.stop();
                timer.cancel();
                Intent intent = new Intent(Activity6_Play.this, Activity1_Main.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog quit = builder.create();
        quit.show();
    }

    public void addListenerOnButtonCorrect(){
        score_txt = (TextView)findViewById(R.id.textView_score_txt);
        term = (TextView)findViewById(R.id.textView_term);
        score_textview = (TextView)findViewById(R.id.textView_score);
        correct = (Button)findViewById(R.id.button_correct);
        correct.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mpcorrect != null) {
                            mpcorrect.stop();
                            mpcorrect.release();
                        }
                        mpcorrect = MediaPlayer.create(Activity6_Play.this, R.raw.correct);
                        mpcorrect.start();
                        score++;
                        EoTTerms.add(new EndOfTurnTerms(corpus.get(0), "+"));
                        corpus.remove(0);
                        if (corpus.isEmpty()){
                            emptyCorpus = true;
                            timer.cancel();
                            SeeTerms();
                        }
                        else {
                            term.setText(corpus.get(0));
                            score_textview.setText(String.valueOf(score));
                            if (score == 0) {
                                score_txt.setTextColor(getResources().getColor(R.color.blue));
                                score_textview.setTextColor(getResources().getColor(R.color.blue));
                            }
                            else if (score > 0) {
                                score_txt.setTextColor(getResources().getColor(R.color.green));
                                score_textview.setTextColor(getResources().getColor(R.color.green));
                            }
                            else {
                                score_txt.setTextColor(getResources().getColor(R.color.red));
                                score_textview.setTextColor(getResources().getColor(R.color.red));
                            }
                        }
                    }
                }
        );
    }

    public void addListenerOnButtonWrong(){
        score_txt = (TextView)findViewById(R.id.textView_score_txt);
        term = (TextView)findViewById(R.id.textView_term);
        score_textview = (TextView)findViewById(R.id.textView_score);
        wrong = (Button)findViewById(R.id.button_wrong);
        wrong.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mpwrong != null) {
                            mpwrong.stop();
                            mpwrong.release();
                        }
                        mpwrong = MediaPlayer.create(Activity6_Play.this, R.raw.wrong);
                        mpwrong.start();
                        score--;
                        EoTTerms.add(new EndOfTurnTerms(corpus.get(0), "-"));
                        corpus.remove(0);
                        if (corpus.isEmpty()){
                            emptyCorpus = true;
                            timer.cancel();
                            SeeTerms();
                        }
                        else {
                            term.setText(corpus.get(0));
                            score_textview.setText(String.valueOf(score));
                            if (score == 0) {
                                score_txt.setTextColor(getResources().getColor(R.color.blue));
                                score_textview.setTextColor(getResources().getColor(R.color.blue));
                            } else if (score > 0) {
                                score_txt.setTextColor(getResources().getColor(R.color.green));
                                score_textview.setTextColor(getResources().getColor(R.color.green));
                            } else {
                                score_txt.setTextColor(getResources().getColor(R.color.red));
                                score_textview.setTextColor(getResources().getColor(R.color.red));
                            }
                        }
                    }

                }
        );
    }

    public void newAttempt(){
        EoTTerms.clear();
        GetReady();
        score_textview = (TextView) findViewById(R.id.textView_score);
        score_txt = (TextView)findViewById(R.id.textView_score_txt);
        term = (TextView) findViewById(R.id.textView_term);
        score_txt.setTextColor(getResources().getColor(R.color.blue));
        score_textview.setTextColor(getResources().getColor(R.color.blue));
        score_textview.setText("0");
        corpus.remove(0);
        if (corpus.isEmpty()){
            emptyCorpus = true;
            timer.cancel();
            SeeTerms();
        }
        else { term.setText(""); }
    }

    public void GetReady(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(Activity6_Play.this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("Get Ready: " + getTeam(attempt, Teams));
        builder.setMessage("Reader: " + getReader(attempt, Teams) + "\nListener: " + getListener(attempt, Teams));
        if (mpdrama != null) {
            mpdrama.stop();
            mpdrama.release();
        }
        mpdrama = MediaPlayer.create(Activity6_Play.this, R.raw.drama);
        builder.setNeutralButton("START", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                timer = new CountDownTimer((chronometer + 1) * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (millisUntilFinished < 10000) {
                            mpdrama.start();
                        }
                        my_chronometer = (TextView)findViewById(R.id.textView_chronometer);
                        my_chronometer.setText(String.valueOf(millisUntilFinished / 1000));
                    }
                    @Override
                    public void onFinish() {
                        if (mpend != null) {
                            mpend.stop();
                            mpend.release();
                        }
                        mpend= MediaPlayer.create(Activity6_Play.this, R.raw.end);
                        mpend.start();
                        SeeTerms();
                    }
                };
                if (mpshotgun != null) {
                    mpshotgun.stop();
                    mpshotgun.release();
                }
                mpshotgun = MediaPlayer.create(Activity6_Play.this, R.raw.shotgun);
                mpshotgun.start();
                timer.start();
                term.setText(corpus.get(0));
                dialogInterface.cancel();
            }
        });
        AlertDialog getReady = builder.create();
        getReady.setCancelable(false);
        getReady.setCanceledOnTouchOutside(false);
        getReady.show();
    }

    public void Highscore(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(Activity6_Play.this, AlertDialog.THEME_HOLO_LIGHT);
        Teams_Sorted.clear();
        if (emptyCorpus == true){
            builder.setTitle("Sorry, End of File\nHIGHSCORE");
        }
        else {
            builder.setTitle("HIGHSCORE");
        }
        Teams.get(turn(attempt, Teams)).addScore(score);
        score = 0;
        for (Team team : Teams) {
            Teams_Sorted.add(team);
        }
        Collections.sort(Teams_Sorted, new Comparator<Team>(){
            public int compare(Team t1, Team t2){
                return t2.getScore() - t1.getScore();
            }
        });
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Teams_Sorted.size(); i++){
            sb.append(i+1 + ". " + Teams_Sorted.get(i).getName() + " " +  Teams_Sorted.get(i).getScore() + "\n");
        }

        builder.setMessage(sb.toString());
        builder.setNeutralButton("PROCEED", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mpclick != null) {
                    mpclick.stop();
                    mpclick.release();
                }
                mpclick = MediaPlayer.create(Activity6_Play.this, R.raw.ok);
                mpclick.start();
                if (Teams.get(turn(attempt, Teams)).getScore() >= goal_result || emptyCorpus == true){
                    dialogInterface.cancel();
                    announceWinner();
                }
                else {
                    attempt++;
                    dialogInterface.cancel();
                    newAttempt();
                }
            }
        });
        AlertDialog highscore = builder.create();
        highscore.setCancelable(false);
        highscore.setCanceledOnTouchOutside(false);
        highscore.show();
    }

    public void SeeTerms() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Activity6_Play.this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("Score: " + score);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < EoTTerms.size(); i++) {
            sb.append(EoTTerms.get(i).getTerm() + " " + EoTTerms.get(i).getResult() + "\n");
        }
        builder.setMessage(sb.toString());
        builder.setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mpclick != null) {
                    mpclick.stop();
                    mpclick.release();
                }
                mpclick = MediaPlayer.create(Activity6_Play.this, R.raw.ok);
                mpclick.start();
                dialogInterface.cancel();
                Highscore();
            }
        });
        final AlertDialog seeTerms = builder.create();
        seeTerms.setCanceledOnTouchOutside(false);
        seeTerms.setCancelable(false);
        my_chronometer = (TextView)findViewById(R.id.textView_chronometer);
        my_chronometer.setText("");
        seeTerms.show();
    }

    public void announceWinner(){
        if (play == false){
            if (mpwin != null) {
                mpwin.stop();
                mpwin.release();
            }
            mpwin = MediaPlayer.create(Activity6_Play.this, R.raw.win);
            mpwin.start();
        }
        play = true;
        final AlertDialog.Builder builder = new AlertDialog.Builder(Activity6_Play.this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("Congratulations");
        builder.setMessage(Teams_Sorted.get(0).getName() + " are the real MVP");
        builder.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mpwin.stop();
                Intent intent = new Intent(Activity6_Play.this, Activity1_Main.class);
                startActivity(intent);
            }
        });
        builder.setNeutralButton("BACK TO HIGHSCORES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Highscore();
            }
        });
        AlertDialog winner = builder.create();
        winner.setCanceledOnTouchOutside(false);
        winner.setCancelable(false);
        winner.show();
    }
}
