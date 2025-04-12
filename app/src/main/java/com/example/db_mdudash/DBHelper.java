package com.example.db_mdudash;

import android.content.Context;
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
        db.execSQL("DROP TABLE IF EXISTS "+DBContract.Dormitories.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+DBContract.Rooms.TABLE_NAME);
        onCreate(db);
    }
}
