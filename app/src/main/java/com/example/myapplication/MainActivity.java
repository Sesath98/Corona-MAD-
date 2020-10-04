 package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity {

     TextView rateCount, showRating;
     EditText review;
     Button submmit;
     RatingBar ratingBar;
     float rateValue;
     String temp;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         rateCount = findViewById(R.id.ratecount);
         ratingBar = findViewById(R.id.ratingBar);
         review = findViewById(R.id.review);
         submmit = findViewById(R.id.submitbtn);
         showRating = findViewById(R.id.showrating);

         ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
             @Override
             public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                 rateValue = ratingBar.getRating();

                 if (rateValue <= 1 && rateValue > 0)
                     rateCount.setText("Bad" + rateValue + "/5");
                 else if (rateValue <= 2 && rateValue > 1)
                     rateCount.setText("ok" + rateValue + "/5");
                 else if (rateValue <= 3 && rateValue > 2)
                     rateCount.setText("good" + rateValue + "/5");
                 else if (rateValue <= 4 && rateValue > 3)
                     rateCount.setText("very good" + rateValue + "/5");
                 else if (rateValue <= 5 && rateValue > 4)
                     rateCount.setText("Excellent" + rateValue + "/5");

             }
         });
         submmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 temp = rateCount.getText().toString();
                 showRating.setText("your Rating: \n " + temp + "\n" + review.getText());
                 review.setText("");
                 ratingBar.setRating(0);
                 rateCount.setText("");
             }
         });
         Toast.makeText(MainActivity.this,"firebase connection success",Toast.LENGTH_LONG).show();

     }
 }