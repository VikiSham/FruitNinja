package com.example.fruitninja;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class MusicControl
{
    private static SoundPool soundPool;
    private static int sound1;
    private static float volume;

    public static void setValue(Context context)
    {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                soundPool = new SoundPool.Builder().setMaxStreams(20).build();
            } else {
                soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 1);
            }
            volume = 1;
            loadSound(context);

    }

    private static void loadSound(Context context)
    {

            sound1 = soundPool.load(context, R.raw.knifeslice, 1);

    }

    public static void playSound()
    {
          soundPool.play(sound1,volume,volume,1,0,1);
    }



}
