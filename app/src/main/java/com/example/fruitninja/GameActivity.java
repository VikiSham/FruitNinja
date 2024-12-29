package com.example.fruitninja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout frameLayout;
    private fruitNinjaView fruitNinjaView;
    private TextView tvTimer;
    private Runnable runnable;
    private TextView tvScore;
    private ImageView life1, life2, life3;
    private Dialog endgame;
    private Button startOver;
    private  Intent musicService = null;
    private SaveSetting saveSetting;
    private BroadcastReceiver phoneReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        setContentView(R.layout.activity_game);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        frameLayout = findViewById(R.id.frm);
        fruitNinjaView = new fruitNinjaView(this, width, height, timeHandler, scoreHandler, lifeHandler);
        frameLayout.addView(fruitNinjaView); //הצגה

        tvTimer = findViewById(R.id.tvTime);
        tvScore = findViewById(R.id.score);

        life1 = findViewById(R.id.life1);
        life2 = findViewById(R.id.life2);
        life3 = findViewById(R.id.life3);

        endgame = new Dialog(this);
        endgame.setContentView(R.layout.dialog);
        endgame.setTitle("GAME OVER");
        startOver = endgame.findViewById(R.id.startOver);
        startOver.setOnClickListener(this);
        saveSetting = new SaveSetting(this);
        if (saveSetting.isBackmusic())
        {
            musicService = new Intent(this,MusicService.class);
            startService(musicService);
        }


         phoneReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    stopService(musicService);
                }
                if (state.equals(TelephonyManager.CALL_STATE_IDLE)) {
                    startService(musicService);
                }
            }
        };


    }

    public void playPause(View view) {
        fruitNinjaView.changeState();
    }

    Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int time = msg.arg1;
            int min = time / 60;
            int sec = time % 60;

            tvTimer.setText(String.format("time :%02d:%02d", min, sec));
        }
    };

    private Handler scoreHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            tvScore.setText("" + msg.arg1);
        }
    };


    private Handler lifeHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            int life = msg.arg1;
            if (life == 1) {
                life1.setVisibility(View.VISIBLE);
                life2.setVisibility(View.INVISIBLE);
                life3.setVisibility(View.INVISIBLE);

            }
            if (life == 2) {
                life1.setVisibility(View.VISIBLE);
                life2.setVisibility(View.VISIBLE);
                life3.setVisibility(View.INVISIBLE);
            }
            if (life >= 3) {
                life1.setVisibility(View.VISIBLE);
                life2.setVisibility(View.VISIBLE);
                life3.setVisibility(View.VISIBLE);
            }
            if (life == 0) {
                life1.setVisibility(View.INVISIBLE);
                life2.setVisibility(View.INVISIBLE);
                life3.setVisibility(View.INVISIBLE);


                endgame.show();

            }
        }
    };

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        endgame.dismiss();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "this is my text to send.");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.menu_email) {
            share();
            return true;
        }
        if (id == R.id.menu_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.menu_info) {
            Intent intent = new Intent(this, Info.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        try
        {
            stopService(musicService);
        }
        catch (Exception e)
        {}

        super.onDestroy();
    }
}