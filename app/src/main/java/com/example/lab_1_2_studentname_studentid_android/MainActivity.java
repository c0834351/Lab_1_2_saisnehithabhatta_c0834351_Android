package com.example.lab_1_2_studentname_studentid_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //location


    //SQLITE database helper
    Products product;
    DatabaseHelper databaseHelper;

    EditText etProductName, etProductPrice, etProductDesc, etProductLatitude, etProductLongitude;
    TextView tvDisplayProducts;
            //tvAddress;
    Button addProductsBtn, countBtn, locationBtn;
    TextView count;

    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tvAddress = findViewById(R.id.addressText);
        locationBtn = findViewById(R.id.location_button);
        countBtn = findViewById(R.id.btn_products_count);
        count = findViewById(R.id.count);
        etProductName = findViewById(R.id.et_productName);
        etProductPrice = findViewById(R.id.et_productPrice);
        etProductDesc = findViewById(R.id.et_productDesc);
        etProductLatitude = findViewById(R.id.et_productLat);
        etProductLongitude = findViewById(R.id.et_productLog);
        tvDisplayProducts = findViewById(R.id.tv_display_products);
        addProductsBtn = findViewById(R.id.btn_add_product);

        findViewById(R.id.location_button).setOnClickListener(this);
        findViewById(R.id.btn_add_product).setOnClickListener(this);
        findViewById(R.id.tv_display_products).setOnClickListener(this);
        findViewById(R.id.btn_products_count).setOnClickListener(this);

        //initializing the dbHelper
        databaseHelper = new DatabaseHelper(this);

        //initialize fusedLocation
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        locationBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //check permission
//                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    getLocation();
//                } else {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
//                }
//            }
//        });

    }

//    public void getLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                Location location = task.getResult();
//                if (location != null) {
//                    try {
//                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
//                        List<Address> address = geocoder.getFromLocation(
//                                location.getLatitude(), location.getLongitude(), 1
//                        );
//                        tvAddress.setText("Latitude: " + address.get(0).getLatitude() + " Longitude: " + address.get(0).getLongitude() + " Country Name: " + address.get(0).getCountryName());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        });
//
//    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_product:
                addProducts();
                break;
            case R.id.tv_display_products:
                Intent intent = new Intent(this, ProductsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_products_count:
                long i =  databaseHelper.productsCount();
                count.setText(String.valueOf(i));
                Toast.makeText(this, "number of count", Toast.LENGTH_SHORT).show();
                break;
            case R.id.location_button:
                 intent = new Intent(this, MapsActivity.class);
                 startActivity(intent);

        }
    }

    private void addProducts() {
        String name = etProductName.getText().toString().trim();
        String desc = etProductDesc.getText().toString().trim();
        String latitude = etProductLatitude.getText().toString().trim();
        String longitude = etProductLongitude.getText().toString().trim();
        String price = etProductPrice.getText().toString().trim();
        if (name.isEmpty()) {
            etProductName.setError("name field cannot be empty");
            etProductName.requestFocus();
            return;
        }
        if (price.isEmpty()) {
            etProductPrice.setError("price cannot be empty");
            etProductPrice.requestFocus();
            return;
        }
        if (desc.isEmpty()) {
            etProductDesc.setError("description field cannot be empty");
            etProductDesc.requestFocus();
            return;
        }
        if (latitude.isEmpty()) {
            etProductLatitude.setError("latitude cannot be empty");
            etProductLatitude.requestFocus();
            return;
        }
        if (longitude.isEmpty()) {
            etProductLongitude.setError("longitude cannot be empty");
            etProductLongitude.requestFocus();
            return;
        }
        // insert products into database table

        product = new Products(name, desc, Double.valueOf(price), Double.valueOf(latitude), Double.valueOf(longitude));

        if(databaseHelper.addProduct(product.getProductName(),product.getDescription(),product.getLatitude(), product.getProductPrice(),product.getLongitude()))
        {
            Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Product NOT Added", Toast.LENGTH_SHORT).show();
        }
        clearFields();

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        clearFields();
    }

    private void clearFields() {
        etProductName.setText("");
        etProductPrice.setText("");
        etProductLatitude.setText("");
        etProductLongitude.setText("");
        etProductDesc.setText("");

        etProductName.clearFocus();
        etProductPrice.clearFocus();
        etProductDesc.clearFocus();
        etProductLongitude.clearFocus();
        etProductLongitude.clearFocus();

    }
}