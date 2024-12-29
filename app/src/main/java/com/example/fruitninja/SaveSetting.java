package com.example.fruitninja;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveSetting
{
    private  SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private final String VIBRATE = "Vibrate"; //מגדירה מחרוזת קבועה
    private  boolean vibrate;
    private final String BMUSIC = "BackMusic";
    private  boolean backmusic;
    private final String SOUND = "Sound";
    private  boolean sound;


    public SaveSetting(Context context) // get access to res
    {
        sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = sp.edit();
        vibrate = sp.getBoolean(VIBRATE, true);
        backmusic = sp.getBoolean(BMUSIC, true);
        sound = sp.getBoolean(SOUND, true);
    }

    public void updateSound(boolean s) //כל פעם שישנו משתנה נבצע את הפעולה
    {
        sound = s;
        editor.putBoolean(SOUND, s);
        editor.commit();
    }

    public void updateVibrate(boolean vib) //כל פעם שישנו משתנה נבצע את הפעולה
    {
        vibrate = vib;
        editor.putBoolean(VIBRATE, vib);
        editor.commit(); // save
    }

    public void updateBackMusic(boolean bm) //כל פעם שישנו משתנה נבצע את הפעולה
    {
        backmusic = bm;
        editor.putBoolean(BMUSIC, bm);
        editor.commit();
    }

    public  boolean isVibrate()
    {
        return vibrate;
    }

    public  boolean isBackmusic()
    {
        return backmusic;
    }

    public  boolean isSound()
    {
        return sound;
    }

}
