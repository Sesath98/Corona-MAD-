package com.example.couchpotatolk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.couchpotatolk.Model.Vendors;

public class SellerLoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Vendors";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        LoginButton =(Button)findViewById(R.id.seller_login_btn);
        emailInput = (EditText)findViewById(R.id.seller_login_email);
        passwordInput = (EditText) findViewById(R.id.seller_login_password);

        loadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginSeller();
            }
        });


    }

    private void LoginSeller() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter your email  ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Log into your account");
            loadingBar.setMessage("Please wait while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(email,password);

        }
    }



    private void AllowAccessToAccount(final String email, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(parentDbName).child(email).exists())
                {
                    Vendors usersData = dataSnapshot.child(parentDbName).child(email).getValue(Vendors.class);

                    if(usersData.getName().equals(email))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            Toast.makeText(SellerLoginActivity.this,"Logged in successfully...",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                            startActivity(intent);

                        }

                    }

                }
                else
                {
                    Toast.makeText(SellerLoginActivity.this,"Account with " + email + "  do not exist. ",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(SellerLoginActivity.this,"Register to create a new account.",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}