package com.example.lab_1_2_studentname_studentid_android;

public class Products {

        int productId;
        String productName, description;
        double productPrice;
        double latitude, longitude;

    public Products(int productId, String productName, String description, double productPrice, double latitude, double longitude) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.productPrice = productPrice;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Products(String productName, String description, double productPrice, double latitude, double longitude) {
        this.productName = productName;
        this.description = description;
        this.productPrice = productPrice;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
