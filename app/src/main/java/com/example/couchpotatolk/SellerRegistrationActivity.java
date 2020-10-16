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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class SellerRegistrationActivity extends AppCompatActivity {

    //variable declaration
    private EditText nameInput, phoneInput, emailInput, passwordInput, websiteInput, descInput;
    private Button create_vendor;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration_activity);

        create_vendor = (Button)findViewById(R.id.seller_register_btn);
        nameInput = (EditText)findViewById(R.id.seller_name);
        phoneInput = (EditText)findViewById(R.id.seller_phone);
        emailInput = (EditText)findViewById(R.id.seller_email);
        passwordInput = (EditText)findViewById(R.id.seller_password);
        websiteInput = (EditText)findViewById(R.id.seller_website);
        descInput = (EditText)findViewById(R.id.seller_desc);
        loadingBar = new ProgressDialog(this);

        create_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();
            }
        });
}

    private void CreateAccount() {
        String name = nameInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String website = websiteInput.getText().toString();
        String description = descInput.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please fill in your name.", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please fill a valid email address.", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please fill a 6 digit password.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(website))
        {
            Toast.makeText(this, "Please fill a valid URL.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(description))
        {
            Toast.makeText(this, "Please enter description.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creating your account..");
            loadingBar.setMessage("Please wait while we check your credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatePhoneNumber (name,phone,email,password,website,description);
        }

}

    private void ValidatePhoneNumber(final String name, final String phone, final String email, final String password, final String website, final String description) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Vendors").child(phone).exists()))
                {
                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("email",email);
                    userdataMap.put("password",password);
                    userdataMap.put("website",website);
                    userdataMap.put("name",name);
                    userdataMap.put("description",description);

                    RootRef.child("Vendors").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(SellerRegistrationActivity.this,"Congratulations! Your account has been created successfully.",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(SellerRegistrationActivity.this,"Network error. Please try again later",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
                else
                {
                    Toast.makeText(SellerRegistrationActivity.this,"This"+ phone + "already exists.",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(SellerRegistrationActivity.this,"Please try again using another phone number",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}


