package com.example.corona_mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    String[] country = { "Available", "Out of Stock", "Unavailable"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void addProductBtn(View view){

        Intent intent = new Intent(this, AddProductActivity.class);

        startActivity(intent);

    }

    public void editProductBtn(View view){

        Intent intent = new Intent(this, EditProductActivity.class);

        startActivity(intent);

    }

    public void viewProductBtn(View view){

        Intent intent = new Intent(this, ShowDetails.class);

        startActivity(intent);

    }

}