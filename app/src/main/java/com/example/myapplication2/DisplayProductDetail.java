package com.example.corona_mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayProductDetail extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter ;

    ProgressDialog progressDialog;

    List<ProductItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product_detail);

        recyclerView = (RecyclerView) findViewById(R.id.displayProductrecyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayProductDetail.this));

        progressDialog = new ProgressDialog(DisplayProductDetail.this);

        progressDialog.setMessage("Loading Images From Database.");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("ProductDetail");

        // Adding Add Value Event Listener to databaseReference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    ProductItem imageUploadInfo = postSnapshot.getValue(ProductItem.class);

                    list.add(imageUploadInfo);
                }

                adapter = new ProductRecyclerViewAdapter(getApplicationContext(), list);

                recyclerView.setAdapter(adapter);

                // Hiding the progress dialog.
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
                progressDialog.dismiss();

            }
        });

    }
}