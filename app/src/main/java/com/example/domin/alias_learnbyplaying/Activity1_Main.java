package com.example.domin.alias_learnbyplaying;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity1_Main extends AppCompatActivity {

    private Button button_play;
    private Button button_info;
    MediaPlayer click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButtonPlay();
        addListeneronButtonInfo();
    }

    public void addListenerOnButtonPlay(){
        button_play = (Button)findViewById(R.id.button_play);
        button_play.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (click!=null) {
                            click.stop();
                            click.release();
                        }
                        click = MediaPlayer.create(Activity1_Main.this,R.raw.ok);
                        click.start();
                        Intent intent = new Intent("com.example.domin.alias_learnbyplaying.Activity3_Options");
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
                if (click!=null) {
                    click.stop();
                    click.release();
                }
                click = MediaPlayer.create(Activity1_Main.this,R.raw.ok);
                click.start();
                Intent intent = new Intent("com.example.domin.alias_learnbyplaying.Activity2_Instructions");
                startActivity(intent);
            }
        });
    }
}
