package com.example.lab_1_2_studentname_studentid_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class ProductsAdapter extends ArrayAdapter {

    private static final String TAG = "ProductsAdapter";
    Context context;
    int layoutRes;
    List<Products> prodList;
    DatabaseHelper databaseHelper;

    public ProductsAdapter(@NonNull Context context, int resource, List<Products> prodList, DatabaseHelper dbHelper) {
        super(context, resource, prodList);
        this.prodList = prodList;
        this.databaseHelper = dbHelper;
        this.context = context;
        this.layoutRes = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = convertView;
        if (v == null) v = inflater.inflate(layoutRes, null);
        TextView etProductName = v.findViewById(R.id.tv_name);
        TextView etProductPrice = v.findViewById(R.id.tv_price);
        TextView etProductDesc = v.findViewById(R.id.tv_description);
        TextView etProductLatitude = v.findViewById(R.id.tv_latitude);
        TextView etProductLongitude = v.findViewById(R.id.tv_longitude);

        final Products prod = prodList.get(position);
        etProductName.setText(prod.getProductName());
        etProductPrice.setText(String.valueOf(prod.getProductPrice()));
        etProductDesc.setText(prod.getDescription());
        etProductLatitude.setText(String.valueOf(prod.getLatitude()));
        etProductLongitude.setText(String.valueOf(prod.getLongitude()));

        //button update

        v.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct(prod);
            }
            private void updateProduct(final Products prod) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.edit_products, null);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                final EditText etProductName = view.findViewById(R.id.et_productName);
                final EditText etProductPrice = view.findViewById(R.id.et_productPrice);
                final EditText etProductDesc = view.findViewById(R.id.et_productDesc);
                final EditText etProductLatitude = view.findViewById(R.id.et_productLat);
                final EditText etProductLongitude = view.findViewById(R.id.et_productLog);

                etProductName.setText(prod.getProductName());
                etProductPrice.setText(String.valueOf(prod.getProductPrice()));
                etProductLatitude.setText(String.valueOf(prod.getLatitude()));
                etProductLongitude.setText(String.valueOf(prod.getLongitude()));
                etProductDesc.setText(prod.getDescription());

                view.findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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

                        if (databaseHelper.updateProducts(prod.getProductId(), name, desc, Double.parseDouble(price), Double.parseDouble(latitude), Double.parseDouble(longitude)))
                            displayProducts();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        //delete products
        v.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(prod);
            }

            private void deleteProduct(final Products product) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (databaseHelper.deleteProducts(prod.getProductId()))
                            displayProducts();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "The product (" + prod.getProductName() + ") is not deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        Log.d(TAG, "getView: " + getCount());
        return v;
    }

    @Override
    public int getCount() {
        return prodList.size();
    }

    private void displayProducts() {
        Cursor cursor = databaseHelper.getAllProducts();
        prodList.clear();
        if (cursor.moveToFirst()) {
            do {
                prodList.add(new Products(
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

        notifyDataSetChanged();

            }


}

