package com.example.fruitninja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity
{
    private Switch s1;
    private Switch s2;
    private Switch s3;
    private SaveSetting saveSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        s1 = findViewById(R.id.switch1);
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    saveSetting.updateSound(isChecked);

            }

        });
        s2 = findViewById(R.id.switch2);
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                saveSetting.updateBackMusic(isChecked);

            }

        });
        s3 = findViewById(R.id.switch3);
        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                saveSetting.updateVibrate(isChecked);

            }

        });
        saveSetting = new SaveSetting(this); //הרשאה
        //
        s1.setChecked(saveSetting.isSound());
        s2.setChecked(saveSetting.isBackmusic());
        s3.setChecked(saveSetting.isVibrate());

    }


  /*  public void changeValue(View view)
    {
        if (view.getId() == s1.getId())
        {
            if (s1.isChecked() == true)
            {
                saveSetting.updateSound(true);
            }
            else
            {
                saveSetting.updateSound(false);
            }
        }
        if (view.getId() == s2.getId())
        {
            if (s2.isChecked() == true)
            {
                saveSetting.updateBackMusic(true);
            }
            else
            {
                saveSetting.updateBackMusic(false);
            }
        }
        if (view.getId() == s3.getId())
        {
            if (s3.isChecked() == true)
            {
                saveSetting.updateVibrate(true);
            }
            else
            {
                saveSetting.updateVibrate(false);
            }
        }

    }
*/
    public void GoToMain (View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}