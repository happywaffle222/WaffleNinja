package com.idtech.christophermiller.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private int highScore;
    boolean play;
    MediaPlayer launch;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        launchMusic();
    }

    void launchMusic(){
        launch = MediaPlayer.create(this, R.raw.afewdollars);
        launch.start();
    }
    void play(View mainmenu){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        launch.stop();
    }
    void options(View mainmenu){
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }
}