package com.example.domin.alias_learnbyplaying;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity1_Main extends AppCompatActivity {

    private Button button_play, button_info, button_about;
    SoundPool mSoundPool;
    int clickId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        clickId = mSoundPool.load(this, R.raw.ok, 1);
        addListenerOnButtonPlay();
        addListeneronButtonInfo();
        addListenerOnButtonAbout();
    }

    public void addListenerOnButtonPlay(){
        button_play = (Button)findViewById(R.id.button_play);
        button_play.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSoundPool.play(clickId, 1, 1, 1, 0, 1);
                        Intent intent = new Intent("com.example.domin.alias_learnbyplaying.Activity4_Options");
                        startActivity(intent);
                    }
                }
        );
    }

    public void addListeneronButtonInfo(){
        button_info = (Button)findViewById(R.id.button_info);
        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSoundPool.play(clickId, 1, 1, 1, 0, 1);
                Intent intent = new Intent("com.example.domin.alias_learnbyplaying.Activity2_Instructions");
                startActivity(intent);
            }
        });
    }

    public void addListenerOnButtonAbout() {
        button_about = (Button)findViewById(R.id.button_about);
        button_about.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSoundPool.play(clickId,1,1,1,0,1);
                        Intent intent = new Intent("com.example.domin.alias_learnbyplaying.Activity3_About");
                        startActivity(intent);
                    }
                }
        );
    }
}
