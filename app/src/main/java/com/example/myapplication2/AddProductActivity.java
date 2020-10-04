package com.example.corona_mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductActivity extends AppCompatActivity {

    EditText prodName, unitName;
    Spinner status;
    Button btnAdd;

    //Firebase Connection
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ProductDetail");

        // Assign From UI
        prodName = findViewById(R.id.addProductName);
        unitName = findViewById(R.id.addUnitPriceInput);
        status = (Spinner) findViewById(R.id.addProductspinner);
        btnAdd = findViewById(R.id.addProductBtn);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

    }

    private void insertData(){

        final String prod_name = prodName.getText().toString().trim();
        final String prod_unit_price = unitName.getText().toString();
        final String prod_status = status.getSelectedItem().toString();

        if(prod_name.matches("") || prod_unit_price.matches("")){

            if(prod_name.matches(""))
                prodName.setError("Please Enter Product Name.!");

            if(prod_unit_price.matches(""))
                    unitName.setError("Please Enter Product Unit Price.!");

        }else{

            String id = myRef.push().getKey();

            ProductItem productItem = new ProductItem(id, prod_name, prod_unit_price, prod_status);

            myRef.child(id).setValue(productItem);

            Toast.makeText(this, "added succesfully "+ id + " " + prod_name, Toast.LENGTH_SHORT).show();

            ((EditText) findViewById(R.id.addProductName)).setText("");
            ((EditText) findViewById(R.id.addUnitPriceInput)).setText("");
        }

    }

}