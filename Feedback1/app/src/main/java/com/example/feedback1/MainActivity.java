package com.example.feedback1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.versionedparcelable.CustomVersionedParcelable;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.DatabaseMetaData;

public class MainActivity extends AppCompatActivity {
    EditText namedata, emaildata ,messagedata;
    Button send, details;
    DatabaseReference reff;

    customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        namedata = findViewById(R.id.namedata);
        emaildata = findViewById(R.id.emildata);
        messagedata = findViewById(R.id.messagedata);

        send = findViewById(R.id.btn_send);
        details = findViewById(R.id.btn_details);

        customer=new customer();
        reff= FirebaseDatabase.getInstance().getReference().child("customer");
        send.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                customer.setName(namedata.getText().toString().trim());
                customer.setEmail(emaildata.getText().toString().trim());
                customer.setMessage(messagedata.getText().toString().trim());


                reff.push().setValue(customer);
                Toast.makeText(MainActivity.this,"data inserted successfully",Toast.LENGTH_LONG).show();




            }

            





    }
    public void sendData(){
        String namedataText = namedata.getText().toString();
        String emaildataText = emaildata.getText().toString();
        String messagedataText = messagedata.getText().toString();



    }

}