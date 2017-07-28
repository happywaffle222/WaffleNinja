package com.idtech.christophermiller.finalproject;

/**
 * Created by iD Student on 7/21/2017.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by iD Student on 7/20/2017.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.Random;

public class EvilStar {
    private Bitmap bitmap;
    private int intX;
    private int intY;
    int ticker;
    int rotVel;

    public EvilStar(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.intX = x;
        this.intY = y;

        Random r = new Random();
        rotVel = r.nextInt(10);
    }

    public void draw(Canvas canvas) {
        Matrix matrix = new Matrix();
        matrix.postRotate(ticker * (rotVel + 1), bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        matrix.postTranslate(intX, intY);
        canvas.drawBitmap(bitmap, matrix, null);
        this.intY = this.intY + 10;
        ticker++;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap newBitmap) {
        bitmap = newBitmap;
    }

    public int getX() { return intX; }

    public void setX(int x) {
        intX = x;
    }

    public int getY() {
        return intY;
    }

    public void setY(int y) {
        intY = y;
    }

}
