//package com.example.myapplication2;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import org.w3c.dom.Text;
//
//
//public class Item_Details extends AppCompatActivity {
//
//    private TextView counterTxt;
//    private Button minusButton;
//    private Button plusButton;
//    private int counter = 0;
//
//    private ImageView product_image;
//    private Button add_cart_btn;
//    private TextView Product_name, price_tag ;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_item__details);
//
//        counterTxt =  findViewById(R.id.Qty_num_btn);
//        minusButton=findViewById(R.id.minusButton);
//        plusButton=findViewById(R.id.plusButton);
//
//        minusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(counter == 0){
//                    counter = 0;
//                }else {
//                    counter--;
//                    counterTxt.setText(Integer.toString(counter));
//                }
//            }
//        });
//
//        plusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                counter++;
//                counterTxt.setText(Integer.toString(counter));
//            }
//        });
//
//    }
//
//    public void resetCounter(View view){
//        counter = 0;
//        counterTxt.setText(String.valueOf(counter));
//    }
//
//
//
//
//
//
//
//
//
//}
//
//
//
//
