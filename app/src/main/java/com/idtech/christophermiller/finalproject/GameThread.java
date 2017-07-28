package com.idtech.christophermiller.finalproject;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;

    private boolean running;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }


    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas canvas;
        while (running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.onDraw(canvas);
                    this.gameView.collisionsCheck();
                }
            }
            finally{
                if (canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
