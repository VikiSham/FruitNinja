package com.example.fruitninja;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service
{

    MediaPlayer player;

    @Override
    public void onCreate()
    {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.fruitninja_backmusic);
        player.setLooping(true); // Set looping
        player.setVolume(100, 100);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        player.stop();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


}
