package com.example.domin.alias_learnbyplaying;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Activity4_Corpus extends AppCompatActivity {

    private Spinner spinner;
    private Button button_select_corpus, button_create_new;
    ArrayList<String> my_assets = new ArrayList<>();
    ArrayList<String> my_corpus = new ArrayList<>();
    String term = null;//var for adding terms to corpus
    int goal_result, chronometer;
    MediaPlayer click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corpus);
        goal_result = getIntent().getExtras().getInt("GOAL");
        chronometer = getIntent().getExtras().getInt("CHRONO");
        //set file names to spinner via AssetManager
        AssetManager assetManager = getApplicationContext().getAssets();
        try {
            for (String file : assetManager.list("")) {
                if (file.endsWith(".txt"))
                    my_assets.add(file);
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Activity4_Corpus.this, android.R.layout.simple_list_item_1, my_assets);
        spinner.setAdapter(arrayAdapter);
        addListenerOnButtonSelectCorpus();
        addListenerOnButtonCreateNew();
    }

    public void addListenerOnButtonSelectCorpus() {
        button_select_corpus = (Button) findViewById(R.id.button_select_corpus);
        button_select_corpus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (click != null) {
                            click.stop();
                            click.release();
                        }
                        click = MediaPlayer.create(Activity4_Corpus.this, R.raw.ok);
                        click.start();
                        String corpus_string = spinner.getSelectedItem().toString();
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(corpus_string)));
                            while ((term = reader.readLine()) != null) {
                                my_corpus.add(term);
                            }
                            Collections.shuffle(my_corpus);
                            Intent intent = new Intent(Activity4_Corpus.this, Activity5_Teams.class);
                            intent.putStringArrayListExtra("CORPUS", my_corpus);
                            intent.putExtra("GOAL", goal_result);
                            intent.putExtra("CHRONO", chronometer);
                            reader.close();
                            startActivity(intent);
                        }
                        catch (FileNotFoundException e) { e.printStackTrace(); }
                        catch (IOException e) { e.printStackTrace(); }
                    }
                }
        );
    }

    public void addListenerOnButtonCreateNew() {
        button_create_new = (Button) findViewById(R.id.button_create_new);
        final int READ_REQ = 24;
        button_create_new.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (click != null) {
                            click.stop();
                            click.release();
                        }
                        click = MediaPlayer.create(Activity4_Corpus.this, R.raw.ok);
                        click.start();
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("text/*");
                        startActivityForResult(intent, READ_REQ);
                    }
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) { uri = resultData.getData(); }
            readTextFile(uri);
        }
    }

    private void readTextFile(Uri uri) {
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((term = reader.readLine()) != null) {
                my_corpus.add(term);
            }
            Collections.shuffle(my_corpus);
            Intent intent = new Intent(Activity4_Corpus.this, Activity5_Teams.class);
            intent.putStringArrayListExtra("CORPUS", my_corpus);
            intent.putExtra("GOAL", goal_result);
            intent.putExtra("CHRONO", chronometer);
            reader.close();
            inputStream.close();
            startActivity(intent);
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}