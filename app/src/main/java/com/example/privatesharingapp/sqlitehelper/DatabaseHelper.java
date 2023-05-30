package com.example.privatesharingapp.sqlitehelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user table
        String CREATE_TABLE_COMMAND = "CREATE TABLE "
                + Util.TABLE_NAME + "("
                + Util.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Util.NAME +" TEXT,"
                + Util.USERNAME +" TEXT,"
                + Util.PHONE +" TEXT,"
                + Util.IMAGE +" BLOB,"
                + Util.PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_COMMAND);
        // Create truck table
        String CREATE_TABLE2_COMMAND = "CREATE TABLE "
                + Util.TABLE2_NAME + "("
                + Util.TRUCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Util.NAME +" TEXT,"
                + "good" +" TEXT,"
                + "weight" +" TEXT,"
                + "height" +" TEXT,"
                + "width" +" TEXT,"
                + "length" +" TEXT,"
                + "sender" +" TEXT,"
                + "receiver" +" TEXT,"
                + "pickup" +" TEXT,"
                + "dropOff" +" TEXT"
                + ")";
        db.execSQL(CREATE_TABLE2_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Util.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+Util.TABLE2_NAME);
        onCreate(db);
    }

    // INSERT
    public long insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NAME, user.getName());
        contentValues.put(Util.USERNAME, user.getUsername());
        contentValues.put(Util.PHONE, user.getPhone());
        contentValues.put(Util.IMAGE, user.getImage());
        contentValues.put(Util.PASSWORD, user.getDescription());

        long rowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return rowId;
    }
    public long insertTruck(Truck truck){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NAME, truck.getName());
        contentValues.put("good", truck.getGood());
        contentValues.put("weight", truck.getWeight());
        contentValues.put("height", truck.getHeight());
        contentValues.put("width", truck.getWidth());
        contentValues.put("length", truck.getLength());
        contentValues.put("sender", truck.getSender());
        contentValues.put("receiver", truck.getReceiver());
        contentValues.put("pickup", truck.getPickup());
        contentValues.put("dropOff", truck.getDropOff());

        long rowId = db.insert(Util.TABLE2_NAME, null, contentValues);
        db.close();
        return rowId;
    }
    // GET
    public boolean getUsers(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.USER_ID},
                Util.USERNAME + " =? and " +
                        Util.PASSWORD + " =?",
                new String[]{username, password}, null, null, null);

        int numOfRows = cursor.getCount();
        if (numOfRows > 0)
            return true;
        else
            return false;
    }
    public ArrayList<Truck> getAllTrucks(){
        ArrayList<Truck> trucks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util.TABLE2_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                trucks.add(new Truck(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return trucks;
    }
    public ArrayList<Truck> getMyTrucks(String sender){
        ArrayList<Truck> trucks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util.TABLE2_NAME + " WHERE " + Util.SENDER + "='" + sender + "'", null);
        if (cursor.moveToFirst()) {
            do {
                trucks.add(new Truck(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return trucks;
    }
}
