package com.example.corona_mad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GetProductDetail extends ArrayAdapter<ProductItem> {

    private Activity context;
    private List<ProductItem> productItemList;

    public GetProductDetail(Activity context, List<ProductItem> productItemList){
        super(context, R.layout.activity_show_details, productItemList);

        this.context = context;
        this.productItemList = productItemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_show_details, null, true);

        TextView textViewProdName = (TextView) listViewItem.findViewById(R.id.textViewProdName);
        TextView textViewUnitPrice = (TextView) listViewItem.findViewById((R.id.textViewUnitPrice));
        Button editBtnUpdate = (Button) listViewItem.findViewById(R.id.EditbuttonForUpdate);

        final ProductItem productItem = productItemList.get(position);

        textViewProdName.setText(productItem.getProd_Name());
        textViewUnitPrice.setText(productItem.getProd_unit_price());

        editBtnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, EditProductActivity.class);

                intent.putExtra("PRODUCT_ID",productItem.getId());
                intent.putExtra("PRODUCT_NAME",productItem.getProd_Name());
                intent.putExtra("PRODUCT_UNIT_PRICE",productItem.getProd_unit_price());
                intent.putExtra("PRODUCT_STATUS",productItem.getStatus());
                context.startActivity(intent);
            }

        });

        return  listViewItem;

    }

}

