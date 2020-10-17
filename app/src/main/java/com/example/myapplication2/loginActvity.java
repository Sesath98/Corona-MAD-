package com.example.myapplication2;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import com.rey.material.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.Model.Users;
import com.example.myapplication2.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import io.paperdb.Paper;

public class loginActvity extends AppCompatActivity
{
    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";

//    checkboxRemeberme
     private CheckBox chkBoxRememberme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actvity);

        LoginButton =(Button)findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_password);
        InputPhoneNumber = (EditText)findViewById(R.id.login_user);
        loadingBar = new ProgressDialog(this);

        //    checkboxRemeberme
        chkBoxRememberme = (CheckBox)findViewById(R.id.remember_mebtn);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginUser();
            }
        });


    }

    private void LoginUser() {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

          if (TextUtils.isEmpty(phone))
          {
            Toast.makeText(this, "please write your number ", Toast.LENGTH_SHORT).show();
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

                    AllowAccessToAccount(phone,password);

              }
          }






    private void AllowAccessToAccount(String phone, String password)
    {
        if(chkBoxRememberme.isChecked())
        {

            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }



        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(usersData.getName().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            Toast.makeText(loginActvity.this,"logged in successfully...",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(loginActvity.this, HomeActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(loginActvity.this,"Password is incorrect",Toast.LENGTH_SHORT).show();

                        }

                    }

                }
                else
                {
                    Toast.makeText(loginActvity.this,"Account with this " + phone + " name do not exist. ",Toast.LENGTH_SHORT).show();
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