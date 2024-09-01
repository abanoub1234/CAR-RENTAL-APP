package com.example.database_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView rv;
    private FloatingActionButton FAB;
    private Toolbar toolbar;
    public static carRVAdapter adapter;
    private DatabaseAccess DB;
    public static final String CAR_KEY = "car_key";
    private static final int Add_Car_Request_Code = 1;
    private static final int Edit_Car_Request_Code = 2;
    private static final int Permission_Request_code = 5;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //لطلب الاذن من المستخدم لجلب الصور من الموبايل من الزاكره الخارجيه
        if(ContextCompat.checkSelfPermission(this , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE} , Permission_Request_code);
        }

        toolbar = findViewById(R.id.details_toolbar);
        FAB = findViewById(R.id.main_fab);
        rv= findViewById(R.id.main_rv);

        DB =  DatabaseAccess.getinstance(this);
        DB.open();
        ArrayList<CAR> cars = DB.AllCars();
        DB.close();

        adapter = new carRVAdapter(cars, new OnRecyclerViewItemClickListener()
        {
            @Override
            public void onItemClick(int carId)
            {
                Intent intent = new Intent(getBaseContext() , ViewCardActivity.class);
                intent.putExtra(CAR_KEY,carId);
                startActivityForResult(intent , Edit_Car_Request_Code);
                
            }
        });
        rv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getBaseContext() , 2);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);



        FAB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(getBaseContext() , ViewCardActivity.class);
                startActivityForResult(intent , Add_Car_Request_Code);

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.main_menu , menu);
        SearchView search_view = (SearchView)menu.findItem(R.id.main_search).getActionView();
        search_view.setSubmitButtonEnabled(true);
        MenuItem dark =  menu.findItem(R.id.dark_but);
        Switch swit = (Switch) menu.findItem(R.id.dark_but).getActionView();
        swit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(swit.isChecked())
                {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else
                {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }
            }
        });
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                DB.open();
                ArrayList<CAR> car = DB.getCars(query);
                DB.close();
                adapter.setCars(car);
                adapter.notifyDataSetChanged();
                if(!car.isEmpty())
                {
                    Toast.makeText(getBaseContext() , "car was found" , Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });

        search_view.setOnCloseListener(new SearchView.OnCloseListener()
        {
            @Override
            public boolean onClose()
            {

                DB.open();
                ArrayList<CAR> cars = DB.AllCars();
                DB.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Add_Car_Request_Code || requestCode==Edit_Car_Request_Code)
        {

            DB.open();
            ArrayList<CAR> car = DB.AllCars();
            DB.close();
            adapter.setCars(car);
            adapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case Permission_Request_code:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                   //تم الحصول على الصلاحيه بنجاح

                }
        }
    }


}