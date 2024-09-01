package com.example.database_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class DatabaseAccess
{
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static  DatabaseAccess instance;

    private DatabaseAccess(Context context)
    {
         this.openHelper = new MyDatabase(context);
    }

    public static DatabaseAccess getinstance(Context context)
    {
        if(instance == null)
        {
            instance = new DatabaseAccess(context);
        }

        return  instance;
    }

    public void open()
    {
        this.database = this.openHelper.getWritableDatabase();
    }

    public void close()
    {
        if(this.database != null)
        {
            this.database.close();
        }
    }

    public  Boolean Insert_Car (CAR car)
    {
        ContentValues Values = new ContentValues();
        Values.put(MyDatabase.MODEL , car.getModel());
        Values.put(MyDatabase.COLOR , car.getColor());
        Values.put(MyDatabase.DistancePerLetter , car.getDbl());
        Values.put(MyDatabase.Image , car.getImage());
        Values.put(MyDatabase.Discriprtion , car.getDiscription());
        long result = database.insert(MyDatabase.TABLE_NAME , null ,Values );
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public Boolean Update_Car(CAR car)
    {
        ContentValues Values = new ContentValues();
        Values.put(MyDatabase.MODEL , car.getModel());
        Values.put(MyDatabase.COLOR , car.getColor());
        Values.put(MyDatabase.DistancePerLetter , car.getDbl());
        //Values.put(MyDatabase.Image , car.getImage());
        Values.put(MyDatabase.Discriprtion , car.getDiscription());
        String args [] = {car.getId()+"" };
        int result = database.update(MyDatabase.TABLE_NAME , Values , "id=?" , args);
        if(result==0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public  Boolean Delete_Car(CAR car)
    {
        String args [] = {car.getId()+"" };
        int result = database.delete(MyDatabase.TABLE_NAME , "id=?" , args);
        if(result==0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public ArrayList<CAR> AllCars()
    {

        ArrayList<CAR> cars = new ArrayList<>();
        Cursor c = database.rawQuery(" SELECT * FROM " + MyDatabase.TABLE_NAME , null );
        if(c !=null && c.moveToFirst())
        {
            do {
                int id = c.getInt(c.getColumnIndex(MyDatabase.ID));
                String model = c.getString(c.getColumnIndex(MyDatabase.MODEL));
                String color = c.getString(c.getColumnIndex(MyDatabase.COLOR));
                Double dbl = c.getDouble(c.getColumnIndex(MyDatabase.DistancePerLetter));
                String Image = c.getString(c.getColumnIndex(MyDatabase.Image));
                String description = c.getString(c.getColumnIndex(MyDatabase.Discriprtion));
                CAR car = new CAR(id , model , color , dbl , Image , description);
                cars.add(car);
            }

            while(c.moveToNext());
            c.close();
        }
        return cars;
    }

    public CAR getCar(int car_id)
    {

       Cursor c = database.rawQuery(" SELECT * FROM " + MyDatabase.TABLE_NAME + " WHERE " + MyDatabase.ID + "=?" , new String[] {String.valueOf(car_id)});
        if(c !=null && c.moveToFirst())
        {
                int id = c.getInt(c.getColumnIndex(MyDatabase.ID));
                String model = c.getString(c.getColumnIndex(MyDatabase.MODEL));
                String color = c.getString(c.getColumnIndex(MyDatabase.COLOR));
                Double dbl = c.getDouble(c.getColumnIndex(MyDatabase.DistancePerLetter));
                String Image = c.getString(c.getColumnIndex(MyDatabase.Image));
                String description = c.getString(c.getColumnIndex(MyDatabase.Discriprtion));
                CAR car = new CAR(id , model , color , dbl , Image , description);
          c.close();
          return car;
        }
        return null;
    }


    public ArrayList<CAR> getCars(String mod)
    {
        ArrayList<CAR> cars = new ArrayList<>();

        Cursor c = database.rawQuery(" SELECT * FROM " + MyDatabase.TABLE_NAME + " WHERE " + MyDatabase.MODEL + " =? " , new String[] {mod} );
        if(c.moveToFirst())
        {
            do {
                int id = c.getInt(c.getColumnIndex(MyDatabase.ID));
                String model = c.getString(c.getColumnIndex(MyDatabase.MODEL));
                String color = c.getString(c.getColumnIndex(MyDatabase.COLOR));
                Double dbl = c.getDouble(c.getColumnIndex(MyDatabase.DistancePerLetter));
                String Image = c.getString(c.getColumnIndex(MyDatabase.Image));
                String description = c.getString(c.getColumnIndex(MyDatabase.Discriprtion));
                CAR car = new CAR(id , model , color , dbl , Image , description);
                cars.add(car);
            }

            while(c.moveToNext());
            c.close();
        }
        return cars;
    }

    public long Get_Cars_Count()
    {
        //ارجاع عدد الصفوف فى جدول معين
        return DatabaseUtils.queryNumEntries(database, MyDatabase.TABLE_NAME);
    }



}
