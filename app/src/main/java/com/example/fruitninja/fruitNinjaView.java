package com.example.fruitninja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

public class fruitNinjaView extends SurfaceView implements Runnable
{
    private int width;
    private int height;
    private Context context;
    private Bitmap bitmapApple, bitmapBanana, bitmapKiwi, bitmapWatermelon, bitmapBack, bitmapBomb;
    private ArrayList<Sprite> figures;
    private ArrayList<Sprite> bombs;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Thread thread;
    private Paint bgPaint;
    private Paint paint;
    private boolean isRuning = true;
    private Path path;
    private Background background;
    private int count = 0;
    private Thread myThread;
    private Handler timeHandler;
    private int score = 0;
    private int life = 3;
    private Handler scoreHandler;
    private Handler lifeHandler;
    private SaveSetting saveSetting;
    private Vibrator vibrator;

    public fruitNinjaView(Context context, int width, int height, Handler handler, Handler scoreHandler, Handler lifeHandler) {
        super(context);
        this.timeHandler = handler;
        this.lifeHandler = lifeHandler;
        this.width = width;
        this.height = height;
        this.context = context;
        this.scoreHandler = scoreHandler;
        figures=new ArrayList<>();
        bombs=new ArrayList<>();
        path = new Path();
        bgPaint = new Paint();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        saveSetting = new SaveSetting(this.context);

        MusicControl.setValue(this.context);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        paint.setColor(Color.rgb(255, 225, 255));
        bgPaint.setColor(Color.rgb(0, 225, 0));

        //background:
        bitmapBack = BitmapFactory.decodeResource(getResources(),R.drawable.woodbackground);
        bitmapBack = Bitmap.createScaledBitmap(bitmapBack, width, height, false);
        background = new Background(bitmapBack,width,height);

        bitmapApple = BitmapFactory.decodeResource(getResources(),R.drawable.apple);
        bitmapApple = Bitmap.createScaledBitmap(bitmapApple, 100, 100, false);
        bitmapBanana = BitmapFactory.decodeResource(getResources(),R.drawable.banana);
        bitmapBanana = Bitmap.createScaledBitmap(bitmapBanana, 100, 100, false);
        bitmapKiwi = BitmapFactory.decodeResource(getResources(),R.drawable.kiwi);
        bitmapKiwi = Bitmap.createScaledBitmap(bitmapKiwi, 100, 100, false);
        bitmapWatermelon = BitmapFactory.decodeResource(getResources(),R.drawable.watermelon);
        bitmapWatermelon = Bitmap.createScaledBitmap(bitmapWatermelon, 100, 100, false);
        bitmapBomb = BitmapFactory.decodeResource(getResources(),R.drawable.bomb);
        bitmapBomb = Bitmap.createScaledBitmap(bitmapBomb, 100,100, false);


        figures.add(new Sprite(bitmapApple,width,height));
        figures.add(new Sprite(bitmapApple,width,height));
        //figures.add(new Sprite(bitmapApple,width,height));
        figures.add(new Sprite(bitmapBanana,width,height));
        figures.add(new Sprite(bitmapBanana,width,height));
      //  figures.add(new Sprite(bitmapBanana,width,height));
        figures.add(new Sprite(bitmapKiwi,width,height));
        figures.add(new Sprite(bitmapKiwi,width,height));
       // figures.add(new Sprite(bitmapKiwi,width,height));
        figures.add(new Sprite(bitmapWatermelon,width,height));
    //    figures.add(new Sprite(bitmapWatermelon,width,height));
        figures.add(new Sprite(bitmapWatermelon,width,height));

        bombs.add(new Sprite(bitmapBomb, width, height));
        bombs.add(new Sprite(bitmapBomb, width, height));
        bombs.add(new Sprite(bitmapBomb, width, height));

        holder = getHolder();
        thread = new Thread(this); //run function
        thread.start();
        startTimer();

    }

    //draw all
    public void draw()
    {
        if (holder.getSurface().isValid()) //אם המשטח פנוי תנעל אותו ותחזיר את הקנבאס
        {
            canvas = holder.lockCanvas();
            background.draw(canvas);
            for (Sprite figure: figures)
                figure.draw(canvas); //לצייר
            for (Sprite figure: bombs)
                figure.draw(canvas); //לצייר
            canvas.drawPath(path,paint);
            holder.unlockCanvasAndPost(canvas); //לפרסם
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
            path.moveTo(event.getX(),event.getY());
        if(event.getAction() == MotionEvent.ACTION_UP)
            path.reset();
        if(event.getAction()==MotionEvent.ACTION_MOVE) {
            path.lineTo(event.getX(), event.getY());
            for (Sprite fruit: figures)
            {
                if(fruit.isTouch((int)event.getX(), (int)event.getY()))
                {
                    if (saveSetting.isSound())
                        MusicControl.playSound();

                    score++;
                    Message msg = scoreHandler.obtainMessage();
                    msg.arg1 = score;
                    scoreHandler.sendMessage(msg);
                    fruit.init();
                }
            }
            for (Sprite bomb: bombs)
            {
                if(bomb.isTouch((int)event.getX(), (int)event.getY()))
                {
                    if (saveSetting.isVibrate())
                        vibrator.vibrate(400);
                    life--;
                    Message Msg = lifeHandler.obtainMessage();
                    Msg.arg1 = life;
                    lifeHandler.sendMessage(Msg);
                    bomb.init();
                    if (life == 0)
                        isRuning = false;
                }
            }
        }
        return true;
    }

    @Override
    public void run()
    {
        while (isRuning)
        {
            draw();

            for (Sprite fruit: figures) {
                if (!fruit.move2()) {
                    life--;
                    Message msg = lifeHandler.obtainMessage();
                    msg.arg1 = life;
                    lifeHandler.sendMessage(msg);
                    if (life == 0)
                        isRuning = false;
                }
            }
            for (Sprite bomb: bombs)
            {
               bomb.move2();
            }
            try
            {
                Thread.sleep(70); // 70 frames per second
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }

    public void changeState() {
        isRuning = !isRuning;
        if (isRuning) {
            thread = new Thread(this); //run function
            thread.start();
            startTimer();
        }
    }


        private void startTimer()
        {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (isRuning) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message myMsg = timeHandler.obtainMessage();
                        count++;
                        myMsg.arg1 = count;
                        timeHandler.sendMessage(myMsg);
                    }
                } };
            myThread = new Thread(runnable);
            myThread.start();
        }
}
