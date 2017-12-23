package com.example.domin.alias_learnbyplaying;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Activity5_Corpus extends AppCompatActivity {

    private ListView list;
    private Button button_select_corpus, button_create_new;
    private TextView tv_length;
    ArrayList<String> my_assets = new ArrayList<>();
    ArrayList<String> copy_my_assets = new ArrayList<>();
    ArrayList<String> my_corpus = new ArrayList<>();
    String term = null;
    int goal_result, chronometer;
    int length = 0;
    SoundPool mSoundPool;
    int clickId, errId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corpus);
        goal_result = getIntent().getExtras().getInt("GOAL");
        chronometer = getIntent().getExtras().getInt("CHRONO");
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        clickId = mSoundPool.load(this, R.raw.ok, 1);
        errId = mSoundPool.load(this, R.raw.err, 1);
        getAssests();
        addListenerOnButtonSelectCorpus();
        addListenerOnButtonCreateNew();
    }

    public void getAssests(){
        AssetManager assetManager = getApplicationContext().getAssets();
        try {
            for (String file : assetManager.list("")) {
                if (file.endsWith(".txt"))
                    my_assets.add(file);
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        for (String clone : my_assets){
            copy_my_assets.add(clone.replace(".txt", ""));
        }
        list = (ListView)findViewById(R.id.list);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, copy_my_assets);
        list.setAdapter(aa);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSoundPool.play(clickId,1,1,1,0,1);
                tv_length = (TextView)findViewById(R.id.textView_length);
                CheckedTextView item = (CheckedTextView) view;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(my_assets.get(i))));
                    while ((term = reader.readLine()) != null) {
                        if (item.isChecked()){ length++; }
                        else { length--; }
                    }
                    reader.close();
                    tv_length.setText(String.valueOf(length));
                }
                catch (FileNotFoundException e) { e.printStackTrace(); }
                catch (IOException e) { e.printStackTrace(); }
            }
        });
    }

    public void add2Array() {
        SparseBooleanArray map = list.getCheckedItemPositions();
        ArrayList<Integer> indexes = new ArrayList();
        for (int i = 0; i < map.size(); i++) {
            if (map.valueAt(i) == true) {
                indexes.add(map.keyAt(i));
            }
        }
        for (int i = 0; i < indexes.size(); i++) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(my_assets.get(indexes.get(i)))));
                while ((term = reader.readLine()) != null) { my_corpus.add(term); }
            }
            catch (FileNotFoundException e) { e.printStackTrace(); }
            catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void addListenerOnButtonSelectCorpus() {
        button_select_corpus = (Button) findViewById(R.id.button_select_corpus);
        button_select_corpus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        add2Array();
                        if (my_corpus.isEmpty()) {
                            Toast.makeText(Activity5_Corpus.this, "Check at least one corpus", Toast.LENGTH_SHORT).show();
                            mSoundPool.play(errId,1,1,1,0,1);

                        }
                        else {
                            mSoundPool.play(clickId,1,1,1,0,1);
                            Collections.shuffle(my_corpus);
                            Intent intent = new Intent(Activity5_Corpus.this, Activity6_Teams.class);
                            intent.putStringArrayListExtra("CORPUS", my_corpus);
                            intent.putExtra("GOAL", goal_result);
                            intent.putExtra("CHRONO", chronometer);
                            startActivity(intent);
                        }
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
                        mSoundPool.play(clickId,1,1,1,0,1);
                        add2Array();
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
            Intent intent = new Intent(Activity5_Corpus.this, Activity6_Teams.class);
            intent.putStringArrayListExtra("CORPUS", my_corpus);
            intent.putExtra("GOAL", goal_result);
            intent.putExtra("CHRONO", chronometer);
            reader.close();
            inputStream.close();
            startActivity(intent);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Activity5_Corpus.this, Activity4_Options.class);
        startActivity(intent);
    }
}