package com.example.fruitninja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Info extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    public void GoToMain (View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}