package com.example.lab_1_2_studentname_studentid_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "products_database";

    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " VARCHAR(20) NOT NULL, " +
                COLUMN_DESC + " VARCHAR(20) NOT NULL, " +
                COLUMN_LATITUDE + " DOUBLE NOT NULL, " +
                COLUMN_PRICE + " DOUBLE NOT NULL, " +
                COLUMN_LONGITUDE + " DOUBLE NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop table and then create it
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    // insert
    public boolean addProduct(String name, String description, double latitude, double price, double longitude) {
        // we need a writeable instance of SQLite database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DESC, description);
        contentValues.put(COLUMN_PRICE, String.valueOf(price));
        contentValues.put(COLUMN_LATITUDE, String.valueOf(latitude));
        contentValues.put(COLUMN_LONGITUDE, String.valueOf(longitude));
        // the insert method associated to SQLite database instance returns -1 if nothing is inserted
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }

    //select all products
    public Cursor getAllProducts() {
        // we need a readable instance of database
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    //update products
    public boolean updateProducts(int id, String name, String description, double price, double latitude, double longitude) {
        // we need a writeable instance of database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DESC, description);
        contentValues.put(COLUMN_PRICE, String.valueOf(price));
        contentValues.put(COLUMN_LATITUDE, String.valueOf(latitude));
        contentValues.put(COLUMN_LONGITUDE, String.valueOf(longitude));

        // update method associated to SQLite database instance returns number of rows affected
        return sqLiteDatabase.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }

    //delete products
    public boolean deleteProducts(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        // the delete method associated to the SQLite database instance returns the number of rows affected
        return sqLiteDatabase.delete(TABLE_NAME,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }

    //search names
    public String searchName(){
        String sql = "SELECT "+COLUMN_NAME+" FROM "+TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return "";
    }

    // number of products in database
    public long productsCount() {
        String sql = "SELECT "+COLUMN_NAME+" FROM "+TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        long totalProducts = cursor.getCount();
        cursor.close();

        return totalProducts;
    }

    public long productsLatitude() {
        String sql = "SELECT "+COLUMN_LATITUDE+" FROM "+TABLE_NAME+" WHERE "+COLUMN_ID;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        long totalProducts = cursor.getCount();
        cursor.close();

        return totalProducts;
    }



}
