package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_shop__details);
        setContentView(R.layout.activity_item__details);
        setContentView(R.layout.activity_confirmation__page);
        Toast.makeText(MainActivity.this, "Firebase connectivity successfully", Toast.LENGTH_SHORT).show();

    }

}
