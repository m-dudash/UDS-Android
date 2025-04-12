package com.example.db_mdudash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBname = "dormitory.db";
    public static final int DBversion = 1;


    public DBHelper(Context context) {
        super(context, DBname, null, DBversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDormitoriesTable = "CREATE TABLE "+DBContract.Dormitories.TABLE_NAME + " ("+
                DBContract.Dormitories.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBContract.Dormitories.COLUMN_ADDRESS + " TEXT, "+
                DBContract.Dormitories.COLUMN_NAME + " TEXT)";
        db.execSQL(createDormitoriesTable);

        String createRoomsTable = "CREATE TABLE "+DBContract.Rooms.TABLE_NAME+" ("+
                DBContract.Rooms.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DBContract.Rooms.COLUMN_DORMITORY_ID + " INTEGER, "+
                DBContract.Rooms.COLUMN_NUMBER + " TEXT, "+
                DBContract.Rooms.COLUMN_BEDS + " INTEGER, "+
                DBContract.Rooms.COLUMN_AREA + " INTEGER, "+
                DBContract.Rooms.COLUMN_EQUIPMENT + " TEXT, "+
                DBContract.Rooms.COLUMN_PRICE + " REAL, "+
                DBContract.Rooms.COLUMN_IS_OCCUPIED + " INTEGER, "+
                "FOREIGN KEY("+DBContract.Rooms.COLUMN_DORMITORY_ID+") REFERENCES "+
                DBContract.Dormitories.TABLE_NAME+"("+DBContract.Dormitories.COLUMN_ID+"))";
        db.execSQL(createRoomsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DBContract.Rooms.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+DBContract.Dormitories.TABLE_NAME);
        onCreate(db);
    }


    //DORMITORY CRUD ====================
    public long addDormitory(String name, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.Dormitories.COLUMN_ADDRESS, address);
        values.put(DBContract.Dormitories.COLUMN_NAME, name);
        long id = db.insert(DBContract.Dormitories.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public Cursor getAllDormitories(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(DBContract.Dormitories.TABLE_NAME, null,null,null,null,null,null);
    }

    public int updateDormitory(int id, String name, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.Dormitories.COLUMN_NAME, name);
        values.put(DBContract.Dormitories.COLUMN_ADDRESS, address);
        String[] whereId = new String[]{String.valueOf(id)};
        int rowsAffected = db.update(DBContract.Dormitories.TABLE_NAME, values, DBContract.Dormitories.COLUMN_ID + " = ?", whereId);
        db.close();
        return  rowsAffected;
    }

    public void deleteDormitory(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereId = new String[]{String.valueOf(id)};
        db.delete(DBContract.Rooms.TABLE_NAME, DBContract.Rooms.COLUMN_DORMITORY_ID + " = ?", whereId);
        db.delete(DBContract.Dormitories.TABLE_NAME, DBContract.Dormitories.COLUMN_ID + " = ?", whereId);
        db.close();
    }



    //ROOM CRUD =========================
    public long addRoom(int dormitoryId, String number, int beds, String equipment, int area, int isOccupied, double price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.Rooms.COLUMN_DORMITORY_ID, dormitoryId);
        values.put(DBContract.Rooms.COLUMN_NUMBER, number);
        values.put(DBContract.Rooms.COLUMN_EQUIPMENT, equipment);
        values.put(DBContract.Rooms.COLUMN_AREA, area);
        values.put(DBContract.Rooms.COLUMN_BEDS, beds);
        values.put(DBContract.Rooms.COLUMN_IS_OCCUPIED, isOccupied);
        values.put(DBContract.Rooms.COLUMN_PRICE, price);
        long id = db.insert(DBContract.Rooms.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public Cursor getAllRooms(int dormitoryId){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(DBContract.Rooms.TABLE_NAME, null, DBContract.Rooms.COLUMN_DORMITORY_ID + " = ?", new String[]{String.valueOf(dormitoryId)}, null,null,
                DBContract.Rooms.COLUMN_IS_OCCUPIED + " ASC, " + DBContract.Rooms.COLUMN_NUMBER +" ASC");
    }

    public Cursor getRoomById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(DBContract.Rooms.TABLE_NAME, null, DBContract.Rooms.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null,null,null);
    }

    public int updateRoom(int id, String number, int beds, String equipment, int area, int isOccupied, double price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.Rooms.COLUMN_NUMBER, number);
        values.put(DBContract.Rooms.COLUMN_EQUIPMENT, equipment);
        values.put(DBContract.Rooms.COLUMN_AREA, area);
        values.put(DBContract.Rooms.COLUMN_BEDS, beds);
        values.put(DBContract.Rooms.COLUMN_IS_OCCUPIED, isOccupied);
        values.put(DBContract.Rooms.COLUMN_PRICE, price);

        int rowsAffected = db.update(DBContract.Rooms.TABLE_NAME, values, DBContract.Rooms.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    public void deleteRoom(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBContract.Rooms.TABLE_NAME, DBContract.Rooms.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
