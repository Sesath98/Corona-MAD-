package com.example.corona_mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProductActivity extends AppCompatActivity {

    EditText prodName;
    EditText prodUnitPrice;
    Spinner prodStatus;

    String prod_id;
    String name;
    String unitPrice;
    String status;
    String imageName;
    String imageURL;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ProductDetail");

        prodName = findViewById(R.id.editProductName);
        prodUnitPrice = findViewById(R.id.editUnitPriceInput);
        prodStatus = findViewById(R.id.editProductspinner);

        Intent intent = getIntent();

        name = intent.getStringExtra("PRODUCT_NAME");
        unitPrice = intent.getStringExtra("PRODUCT_UNIT_PRICE");
        status = intent.getStringExtra("PRODUCT_STATUS");
        prod_id = intent.getStringExtra("PRODUCT_ID");
        imageName = intent.getStringExtra("PRODUCT_ImageName");
        imageURL = intent.getStringExtra("PRODUCT_ImageURL");

        //Toast.makeText(this, intent.getStringExtra("PRODUCT_ID"), Toast.LENGTH_SHORT).show();

        prodName.setText(name);
        prodUnitPrice.setText(unitPrice);
        prodStatus.setSelection(1);

        Button updateBtn = (Button) findViewById(R.id.editAndProductBtn);
        Button deleteProdBtn = (Button) findViewById(R.id.deleteProductBtn);

        updateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateProduct();
            }
        });

        deleteProdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(prod_id);
            }
        });

    }

    private void updateProduct() {

        final String strProdName = prodName.getText().toString();
        final String strProdUnitPrice = prodUnitPrice.getText().toString();
        final String strProdStatus = prodStatus.getSelectedItem().toString();

        if(strProdName.matches(("")) || strProdUnitPrice.matches((""))){

            if(strProdName.matches(("")))
                prodName.setError("Please Enter Product Name..!!");
            if (strProdUnitPrice.matches(""))
                prodUnitPrice.setError("Please Enter Unit Price");

        }else{

            ProductItem productItem = new ProductItem(prod_id, strProdName, strProdUnitPrice, strProdStatus, imageName, imageURL);

            myRef.child(prod_id).setValue(productItem);

            //Toast.makeText(this, " Updated succesfully "+ prod_id + " " + name, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

    }

    private void deleteProduct(String prod_id){

        DatabaseReference deleteProd = myRef.child(prod_id);

        deleteProd.removeValue();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}