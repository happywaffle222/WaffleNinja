package com.idtech.christophermiller.finalproject;

/**
 * Created by iD Student on 7/21/2017.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class WaffleNinja {
    public Bitmap bitmap;
    public static int x;
    public static int y;
    public int xVelocity;
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public WaffleNinja(Bitmap bitmap, int x, int y){
        this.bitmap = bitmap;
        this.x = x;
        this.y = 1400;
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
        setX(x + xVelocity, canvas);
    }
    public void setX(int newX, Canvas canvas){
        if(newX >= canvas.getWidth() - bitmap.getWidth() - OptionsActivity.ninjaModeRight)
            x = canvas.getWidth() - bitmap.getWidth() - OptionsActivity.ninjaModeRight;
        else if(newX <= 0 + OptionsActivity.ninjaModeLeft)
            x = 0 + OptionsActivity.ninjaModeLeft;
        else
            x = newX;
    }
}
