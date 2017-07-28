package com.idtech.christophermiller.finalproject;

/**
 * Created by iD Student on 7/21/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }

    static boolean gameover = false;
    public static int sanic;
    public int score;
    private ArrayList<EvilStar> evilStars;
    private ArrayList<EvilStar> evilStarsToRemove;
    private GameThread thread;
    WaffleNinja waffleNinja;
    Paint textPaint;
    MediaPlayer missionInwaffable;
    MediaPlayer death;
    Context context;
    int deathSong = 0;
    int highScore;
    Button mainButton;
    Bitmap background = BitmapFactory.decodeResource(getResources(),
            OptionsActivity.getBackground());

    public GameView(Context context) {
        this(context, null);
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getHolder().addCallback(this);
        setFocusable(true);
        thread = new GameThread(getHolder(), this);
        waffleNinja = new WaffleNinja(BitmapFactory.decodeResource(getResources(),OptionsActivity.getWaffle()), 50, 50);
        textPaint = new Paint();
        textPaint.setTextSize(100);
        textPaint.setColor(Color.WHITE);
    }

    public static boolean getGameover(){
        return gameover;
    }
    public static void setSanic(int newSanic){
        sanic = newSanic;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


        background = Util.getResizedBitmap(background, getWidth(), getHeight());

        if (sanic == 0)
            sanic = 500;
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Random generator = new Random();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.waffleninja);
                int startingXPosition = generator.nextInt(getWidth() - bitmap.getWidth());
                Bitmap evilStarBM = BitmapFactory.decodeResource(getResources(),
                        R.drawable.evilstar);

                EvilStar EvilStar = new EvilStar(evilStarBM, startingXPosition, 0);
                evilStars.add(EvilStar);
            }
        }, 0, sanic);
        thread.setRunning(true);
        thread.start();

        restartGame();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (gameover) {
                setGameover(false);
                restartGame();
            }
            else {

                if (event.getX() > getWidth() / 2) {
                    waffleNinja.xVelocity = 10;
                } else {
                    waffleNinja.xVelocity = -10;
                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            waffleNinja.xVelocity = 0;
        }
        return true;
    }

    boolean ticker;
    @Override
    protected void onDraw(Canvas canvas) {
        if(canvas == null)
            return;
        //canvas.drawColor(Color.BLUE);
        canvas.drawARGB(255, 0, 0, 128);
        canvas.drawBitmap(background, 0, 0, null);
        if (gameover) {
            if(ticker) {

                missionInwaffable.pause();
                playDeath();
            }
            gameover = true;
            ticker = false;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.roasted);
            canvas.drawBitmap(bitmap, 50, 800, null);
            canvas.drawText("Game over!", 0, 200, textPaint);
            canvas.drawText("You got a score of: " + score, 0, 500, textPaint);
            canvas.drawText("Touch to retry!", 0, 750, textPaint);
        }
        else {
            waffleNinja.draw(canvas);
            // Draw Stars
            ArrayList<EvilStar> tempEvilStars;
            synchronized (evilStars) {
                tempEvilStars = new ArrayList<EvilStar>(evilStars);
            }
            for (EvilStar evilStar : tempEvilStars) {
                evilStar.draw(canvas);
                if (evilStar.getY() > canvas.getHeight()) {
                    evilStarsToRemove.add(evilStar);
                    score++;
                }
            }
            synchronized (evilStars) {
                evilStars = tempEvilStars;
            }
            tempEvilStars.removeAll(evilStarsToRemove);
            evilStarsToRemove.clear();

            // Draw Score
            canvas.drawText("Current score:" + score, 0, 200, textPaint);
        }
    }
    protected void collisionsCheck() {
        Bitmap waffleNinjaBitmap = waffleNinja.getBitmap();

        ArrayList<EvilStar> tempEvilStars;
        synchronized (evilStars) {
            tempEvilStars = new ArrayList<EvilStar>(evilStars);
        }

        for (EvilStar evilStar : tempEvilStars) {
            Bitmap evilStarBitmap = evilStar.getBitmap();

            if (evilStar.getX() < waffleNinja.getX() + waffleNinjaBitmap.getWidth()
                    && evilStar.getX() + evilStarBitmap.getWidth() - 30 > waffleNinja.getX() - 5) {


                if (evilStar.getY() < waffleNinja.getY() + waffleNinjaBitmap.getHeight()
                        && evilStar.getY() + evilStarBitmap.getHeight() - 30 > waffleNinja.getY() - 5){
                    setGameover(true);
                }
            }
        }
    }
    public void restartGame(){
        evilStars = new ArrayList<EvilStar>();
        evilStarsToRemove = new ArrayList<EvilStar>();
        score = 0;
        playMission();
        ticker = true;
        gameover = false;
    }
    public void playMission(){
        missionInwaffable = MediaPlayer.create(context, R.raw.missionimwaffable);
        missionInwaffable.start();
    }

    public void playDeath(){
        if (deathSong == 0) {
            death = MediaPlayer.create(context, R.raw.roblox);
            death.start();
        }
        if (deathSong == 1) {
            death = MediaPlayer.create(context, R.raw.missionfailed);
            death.start();
        }
        if (deathSong == 0)
        deathSong++;
        else
        deathSong = 0;
    }
    public void highScore(){
    }
}