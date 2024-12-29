package com.example.fruitninja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ShareActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
    }

    public void GoToMain (View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void sendMail(View view)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:emilynudel@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "My email's body");

        try
        {
            startActivity(emailIntent);
        }
        catch (android.content.ActivityNotFoundException ex)
        {
            Toast.makeText(this, "No email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

}