package com.example.corona_mad;

public class ProductItem {

    String id;
    String prod_Name;
    String prod_unit_price;
    String status;
    String imageName;
    String imageUrl;

    public ProductItem() {

    }

    public ProductItem(String id, String prod_Name, String prod_unit_price, String status, String imageName, String imageUrl) {
        this.id = id;
        this.prod_Name = prod_Name;
        this.prod_unit_price = prod_unit_price;
        this.status = status;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public ProductItem(String id, String prod_Name, String prod_unit_price, String status ) {
        this.id = id;
        this.prod_Name = prod_Name;
        this.prod_unit_price = prod_unit_price;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProd_Name() {
        return prod_Name;
    }

    public void setProd_Name(String prod_Name) {
        this.prod_Name = prod_Name;
    }

    public String getProd_unit_price() {
        return prod_unit_price;
    }

    public void setProd_unit_price(String prod_unit_price) {
        this.prod_unit_price = prod_unit_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
