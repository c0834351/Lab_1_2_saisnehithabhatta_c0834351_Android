package com.example.lab_1_2_studentname_studentid_android;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    List<Products> productList;

    ListView productListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        productList = new ArrayList<>();
        productListView = findViewById(R.id.lv_products);

        //instantiate dbHelper
        databaseHelper = new DatabaseHelper(this);

        //displaying the list of products

        displayListOfProducts();

    }

    long count = 0;
    private void displayListOfProducts() {
        Cursor cursor = databaseHelper.getAllProducts();
//        productList.add(new Products(1,"sunscreen", "brand", 33.2,32.3,43.4));
//        productList.add(new Products(2,"moisturiser", "brand", 33.2,32.3,43.4));
        databaseHelper.addProduct("sunscreen", "brand", 33.2,32.3,43.4 );
        databaseHelper.addProduct("moisturiser", "brand", 33.2,32.3,43.4);
        databaseHelper.addProduct("soaps", "brand", 33.2,32.3,43.4 );
        databaseHelper.addProduct("biscuits", "brand", 33.2,32.3,43.4);
        databaseHelper.addProduct("chocolates", "brand", 33.2,32.3,43.4 );
        databaseHelper.addProduct("shampoos", "brand", 33.2,32.3,43.4);
        databaseHelper.addProduct("ice-cream", "brand", 33.2,32.3,43.4 );
        databaseHelper.addProduct("mazza", "brand", 33.2,32.3,43.4);

        count = productListView.getCount();

        if (cursor.moveToFirst()) {
            do {
                productList.add(new Products(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }

        ProductsAdapter prodAdap = new ProductsAdapter(this, R.layout.list_layout_product, productList, databaseHelper);
        productListView.setAdapter(prodAdap);


    }
}