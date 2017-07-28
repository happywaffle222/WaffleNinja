package com.idtech.christophermiller.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by iD Student on 7/27/2017.
 */

public class OptionsActivity extends Activity {
    public static int waffle = 0;
    public static int background = 0;
    public static int ninjaModeLeft = 0;
    public static int ninjaModeRight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
    }
    void back(View options){
        onBackPressed();
    }
    void easy(View options){
        GameView.setSanic(1000);
    }
    void medium(View options){
        GameView.setSanic(500);
    }
    void hard(View options){
        GameView.setSanic(250);
    }
    void ninja(View options){
        ninjaModeLeft = 10;
        ninjaModeRight = 30;
        GameView.setSanic(175);
        waffle = 4;
        background = 1;
    }
    void red(View options){
        waffle = 1;
    }
    void pink(View options){
        waffle = 2;
    }
    void blue(View options){
        waffle = 3;
    }
    public static int getWaffle(){
        if (waffle == 1)
            return R.drawable.redwaffle;
        else if (waffle == 2)
            return R.drawable.purplewaffle;
        else if (waffle == 3)
            return R.drawable.bluewaffle;
        else if (waffle == 4)
            return R.drawable.ninjamode;
        else
            return R.drawable.waffleninja;
    }
    public static int getBackground(){
        if(background == 0)
            return R.drawable.dojo;
        else
        return R.drawable.ninjamodebackground;
    }
}
