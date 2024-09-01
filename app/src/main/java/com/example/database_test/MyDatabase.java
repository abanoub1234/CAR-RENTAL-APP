package com.example.database_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class MyDatabase extends SQLiteAssetHelper
{
    public static final String DB_Name = "CARS.db";
    public static final int DB_Version = 1;
    public static final String TABLE_NAME = "cars";
    public static final String ID = "id";
    public static final String MODEL = "model";
    public static final String COLOR = "color";
    public static final String Image = "image";
    public static final String Discriprtion = "discription";
    public static final String DistancePerLetter = "dbl";

    public MyDatabase(Context context)
    {
        super(context , DB_Name ,null , DB_Version);
    }

    //@Override
    //public void onCreate(SQLiteDatabase sqLiteDatabase)
    //{
    // sqLiteDatabase.execSQL(" CREATE TABLE " +TABLE_NAME+ "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  + MODEL + " TEXT, " +COLOR+ " TEXT, " + DistancePerLetter+ " REAL )" );
    //}

    //@Override
    //public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    //{
    //    sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
    //   onCreate(sqLiteDatabase);
    //}


}
