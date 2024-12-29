package com.example.fruitninja;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Sprite extends Figure
{
    private static Random rnd;

    public Sprite(Bitmap img, int width, int height)
    {
        super(img,width, height);
        rnd = new Random();
       init();
    }


    public boolean move2()//אם חרגתי מגבולות המסך תאפס את איקס
    {
        y+= dy;

        if (y + image.getHeight() > height)
        {
            init();
            return false;
        }
        return true;

    }

    public void init() {
        this.y = -rnd.nextInt(800);
        dy = 10 + rnd.nextInt(20);
        int size = 100+ rnd.nextInt(100);
        image = Bitmap.createScaledBitmap(image, size, size, false);
        this.x =  rnd.nextInt(this.width-image.getWidth());
    }
}
