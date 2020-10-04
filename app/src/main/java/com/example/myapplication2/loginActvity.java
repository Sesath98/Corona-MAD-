package com.example.myapplication2;

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

import Model.Users;

public class loginActvity extends AppCompatActivity
{
    private EditText InputName, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actvity);

        LoginButton =(Button)findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_password);
        InputName = (EditText)findViewById(R.id.login_user);
        loadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginUser();
            }
        });


    }

    private void LoginUser() {
        String name = InputName.getText().toString();
        String password = InputPassword.getText().toString();

          if (TextUtils.isEmpty(name))
          {
            Toast.makeText(this, "please write your phone number ", Toast.LENGTH_SHORT).show();
          }
          else if (TextUtils.isEmpty(password))
          {
            Toast.makeText(this, "please write your password", Toast.LENGTH_SHORT).show();
          }
          else
              {
                  loadingBar.setTitle("Login Account");
                  loadingBar.setMessage("Please wait, while we are checking the credentials");
                  loadingBar.setCanceledOnTouchOutside(false);
                  loadingBar.show();

                    AllowAccessToAccount(name,password);

              }
          }

    private void AllowAccessToAccount(String name, String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(parentDbName).child(name).exists())
                {
                    Users usersData = dataSnapshot.child(parentDbName).child(name).getValue(Users.class);

                    if(usersData.getName().equals(name))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            Toast.makeText(loginActvity.this,"logged in successfully...",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(loginActvity.this, HomeActivity.class);
                            startActivity(intent);

                        }

                    }

                }
                else
                {
                    Toast.makeText(loginActvity.this,"Account with this" + name + " name do not exist.",Toast.LENGTH_SHORT).show();
                     loadingBar.dismiss();
                     Toast.makeText(loginActvity.this,"you need to create a new account.",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
