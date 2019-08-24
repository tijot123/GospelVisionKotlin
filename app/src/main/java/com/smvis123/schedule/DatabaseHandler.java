package com.smvis123.schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "db_favorites";

    // Contacts table name
    private static final String TABLE_FAVORITES = "favourites";

    // Contacts Table Columns names
    private static final String KEY_ROW_ID = "id";
    private static final String KEY_ID = "itemid";
    private static final String KEY_NAME = "title";
    private static final String KEY_DES = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_TYPE = "type";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + KEY_ROW_ID + " INTEGER PRIMARY KEY," + KEY_ID + " INTEGER," + KEY_NAME + " TEXT," + KEY_DES + " TEXT," + KEY_IMAGE + " TEXT,"
                + KEY_TYPE + " TEXT " + ")";

        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);

        // Create tables again
        onCreate(db);
    }


    public void addfav(int id, String name, String des, String image, String type) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id); // Contact Name
        values.put(KEY_NAME, name);
        values.put(KEY_DES, des);
        values.put(KEY_IMAGE, image);
        values.put(KEY_TYPE, type);// Contact Phone Number


        // Inserting Row
        db.insert(TABLE_FAVORITES, null, values);
        db.close();

        Log.i("Added", "Added" + name);


    }


    public int getmaxid() {

        SQLiteDatabase db = this.getWritableDatabase();
        final SQLiteStatement stmt = db
                .compileStatement("SELECT MAX(id) FROM " + TABLE_FAVORITES);

        return (int) stmt.simpleQueryForLong();


    }


    public void clearall(String type) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_FAVORITES + " WHERE type='" + type + "';");


    }

    public void delete(String id, String type) {

        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("DELETE FROM " + TABLE_FAVORITES + " WHERE " + KEY_DES + " = '" + id + "' AND type='" + type + "';");


    }

    public List<String> getData(int id, String type) {

        List<String> Data = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_FAVORITES + " WHERE type = '" + type + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                // Adding contact to list
                Data.add(cursor.getString(id));
                Log.i("cache retreived", "cache retreived");

            } while (cursor.moveToNext());
        }
        cursor.close();

        return Data;


    }


    public int getCount() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int data = cursor.getCount();
        cursor.close();
        return data;
    }


}