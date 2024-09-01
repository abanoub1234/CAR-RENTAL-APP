package com.example.database_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ViewCardActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private TextInputEditText et_model;
    private TextInputEditText et_color;
    private TextInputEditText et_dpl;
    private Button btn_details;
    private TextInputEditText et_discription;
    private ImageView iv;
    private int car_id = -1;
    public static final String CAR_KEY = "car_key";
    private DatabaseAccess DB;
    private static final int Pick_Image_Req_Code = 1;
    private Uri ImageURI = null;
    public static final int Add_Car_Result_Code = 2;
    public static final int Edit_Car_Result_Code = 3;
    public static final int Delete_Car_Result_Code = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);
        toolbar = findViewById(R.id.details_toolbar);
        //setSupportActionBar(toolbar);

        iv = findViewById(R.id.details_iv);
        btn_details = findViewById(R.id.detaisl_btn);
        btn_details.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent();
                intent.setClass(getBaseContext() , DetailsActivity.class);
                startActivity(intent);
            }
        });

        et_model = findViewById(R.id.et_details_model);
        et_color = findViewById(R.id.et_details_color);
        et_dpl = findViewById(R.id.et_details_Dpl);
        et_discription = findViewById(R.id.et_details_description);
        DB = DatabaseAccess.getinstance(this);
        Intent intent = getIntent();
        car_id = intent.getIntExtra(CAR_KEY , -1);

        if(car_id==-1)
        {
            //عمليه اضافه
            enableFields();
            clearFields();

        }
        else
        {
            //عمليه عرض
            disableFields();
            DB.open();
            CAR car = DB.getCar(car_id);
            DB.close();
            if(car != null)
            {
                fillcarToFields(car);

            }
        }

        iv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent in = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in , Pick_Image_Req_Code);
            }
        });
    }

    private void disableFields()
    {
        iv.setEnabled(false);
        et_model.setEnabled(false);
        et_color.setEnabled(false);
        et_discription.setEnabled(false);
        et_dpl.setEnabled(false);
    }

    private void enableFields()
    {
        iv.setEnabled(true);
        et_model.setEnabled(true);
        et_color.setEnabled(true);
        et_discription.setEnabled(true);
        et_dpl.setEnabled(true);
    }

    private void clearFields()
    {
        iv.setImageURI(null);
        et_model.setText("");
        et_color.setText("");
        et_discription.setText("");
        et_dpl.setText("");
    }

    private void fillcarToFields(CAR c)
    {
      if(c.getImage() != null  && !c.getImage().equals(""))
         iv.setImageResource(R.drawable.carcar);
         //iv.setImageURI(Uri.parse(c.getImage()));
      et_model.setText(c.getModel());
      et_color.setText(c.getColor());
      et_discription.setText(c.getDiscription());
      et_dpl.setText(c.getDbl() + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.details_menu , menu);
        MenuItem save =   menu.findItem(R.id.details_menu_save);
        MenuItem Edit =   menu.findItem(R.id.details_menu_edit);
        MenuItem Delete =   menu.findItem(R.id.details_menu_delete);

        if(car_id==-1)
        {
            //عمليه اضافه
         save.setVisible(true);
         Edit.setVisible(false);
         Delete.setVisible(false);

        }
        else
        {
            //عمليه عرض
            save.setVisible(true);
            Edit.setVisible(true);
            Delete.setVisible(true);

        }

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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        String  color , desc ,model , Image ;
        Double dbl;
        boolean res;
        switch(item.getItemId())
        {
            case R.id.details_menu_save:
                model = et_model.getText().toString();
                color = et_color.getText().toString();
                Image = String.valueOf(ImageURI);
                dbl = Double.parseDouble(et_dpl.getText().toString());
                desc = et_discription.getText().toString();
                CAR c = new CAR(car_id,model , color , dbl , Image , desc);
                DB.open();
                if(car_id == -1)
                {
                    res = DB.Insert_Car(c);
                    if(res)
                    {
                        Toast.makeText(this, "Car Added Successfuly", Toast.LENGTH_LONG).show();
                        setResult(Add_Car_Result_Code , null);
                        finish();
                    }

                }
                else
                {
                    res = DB.Update_Car(c);
                    if(res)
                    {
                        Toast.makeText(this, "Car Edited Successfuly", Toast.LENGTH_LONG).show();
                        setResult(Edit_Car_Result_Code , null);
                        finish();
                    }

                }

                DB.close();
                return true;

            case R.id.details_menu_edit:
                enableFields();
                return true;

            case R.id.details_menu_delete:
                model = et_model.getText().toString();
                color = et_color.getText().toString();
                //if(ImageURI != null)
                //img = ImageURI.toString();
                dbl = Double.parseDouble(et_dpl.getText().toString());
                desc = et_discription.getText().toString();
                CAR c2 = new CAR(car_id,model , color , dbl , "" , desc);
                DB.open();
                boolean result = DB.Delete_Car(c2);
                if(result)
                {
                    Toast.makeText(this, "Car Deleted Successfuly", Toast.LENGTH_LONG).show();
                    setResult(Edit_Car_Result_Code , null);
                    finish();
                }
                return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Pick_Image_Req_Code && resultCode == RESULT_OK)
        {
            if(data != null)
            {
                ImageURI = data.getData();
                iv.setImageURI(ImageURI);
            }

        }
    }
}