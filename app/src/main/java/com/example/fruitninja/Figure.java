package com.example.fruitninja;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

public abstract class Figure
{
    protected int x = 0, dx = 0;
    protected int y = 0, dy = 0;
    protected int width;
    protected int height;
    protected Bitmap image;

    public Figure(Bitmap img, int width, int height)
    {
        this.image = img;
        this.width = width;
        this.height = height;
    }

    public void draw(Canvas canvas) //מקבלת משטח ציור ומציירת תמונה
    {
        canvas.drawBitmap(image,x,y,null);
    }

    public boolean isTouch(int px, int py) {
        if (x < px && x + image.getWidth() > px)
            if (y < py && y + image.getHeight() > py) {
                Log.d("TAG", "isTouch: ");
                return true;
            }
        return false;
    }



}
