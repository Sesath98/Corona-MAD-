package com.example.corona_mad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    Context context;

    List<ProductItem> MainImageUploadInfoList;

    public ProductRecyclerViewAdapter(Context context, List<ProductItem> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_recycler_view_adapter, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductItem UploadInfo = MainImageUploadInfoList.get(position);

        holder.ProductNameTextView.setText(UploadInfo.getProd_Name());
        holder.ProductUnitPriceTextView.setText(UploadInfo.getProd_unit_price());

        holder.editBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, EditProductActivity.class);

                intent.putExtra("PRODUCT_ID",UploadInfo.getId());
                intent.putExtra("PRODUCT_NAME",UploadInfo.getProd_Name());
                intent.putExtra("PRODUCT_UNIT_PRICE",UploadInfo.getProd_unit_price());
                intent.putExtra("PRODUCT_STATUS",UploadInfo.getStatus());
                intent.putExtra("PRODUCT_ImageName",UploadInfo.getImageName());
                intent.putExtra("PRODUCT_ImageURL",UploadInfo.getImageUrl());

                context.startActivity(intent);
            }

        });

        //Loading image from Glide library.
        Glide.with(context).load(UploadInfo.getImageUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView ProductUnitPriceTextView, ProductNameTextView ;
        public Button editBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.productImage);

            ProductNameTextView = (TextView) itemView.findViewById(R.id.ProductNameText);

            ProductUnitPriceTextView = (TextView) itemView.findViewById(R.id.retrievedProducPrice);

            editBtn = (Button) itemView.findViewById(R.id.editProductRecycleView);


        }
    }

}