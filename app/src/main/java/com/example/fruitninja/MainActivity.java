package com.example.fruitninja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity

{
    BroadcastReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load data for file
        batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW))
                {
                        Toast.makeText(context, "Low Battery! charge the phone", Toast.LENGTH_SHORT).show();
                }
            }
        };

        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
    }

    @Override
    protected void onDestroy () {
        unregisterReceiver(batteryReceiver);
        super.onDestroy();
    }



    public void goToGame(View view)
    {
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);

    }


    public void goToSetting(View view)
    {
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }

    public void GoToShare(View view)
    {
        Intent intent = new Intent(this,ShareActivity.class);
        startActivity(intent);
    }

    public void GoToInfo (View view)
    {
        Intent intent = new Intent(this,Info.class);
        startActivity(intent);
    }

}