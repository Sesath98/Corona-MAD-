package com.example.corona_mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowDetails extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    ListView listViewProducts;

    List<ProductItem> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ProductDetail");

        listViewProducts = (ListView) findViewById(R.id.listViewItem);

        productList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot productDatasnapshot : dataSnapshot.getChildren()){

                    ProductItem productItem = productDatasnapshot.getValue(ProductItem.class);

                    productList.add(productItem);

                }

                GetProductDetail getProductDetail = new GetProductDetail(ShowDetails.this, productList);
                listViewProducts.setAdapter(getProductDetail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }

        });

    }

}