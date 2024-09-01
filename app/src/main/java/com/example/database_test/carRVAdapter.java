package com.example.database_test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

public class carRVAdapter extends RecyclerView.Adapter<carRVAdapter.CarViewHolder>
{

    private  ArrayList<CAR>  cars;
    private OnRecyclerViewItemClickListener listener;

    public carRVAdapter(ArrayList<CAR>  cars , OnRecyclerViewItemClickListener listener)
    {
         this.cars = cars;
         this.listener = listener;
    }

    public ArrayList<CAR> getCars() {
        return cars;
    }

    public void setCars(ArrayList<CAR> cars) {
        this.cars = cars;
    }

    @NonNull
    @NotNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_car_layout , null , false);
        CarViewHolder carviewholder = new CarViewHolder(v);
        return carviewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull carRVAdapter.CarViewHolder holder, int position)
    {
        CAR c = cars.get(position);
        if(c.getImage() != null && !c.getImage().isEmpty())
            holder.img.setImageResource(R.drawable.carcar);
        //ده لو عاوزها تيجى من الداتا بيز اللى فوق دى انا مثبتها
            //holder.img.setImageURI(Uri.parse(c.getImage()));
        holder.tv_model.setText(c.getModel());
        holder.tv_color.setText(c.getColor());

        //علشان لو هناخد لون من اليوزر مثلا زى #ffffff  يتحول الى لون طبيعى وليس مجرد اسم فقط
        try
        {
            holder.tv_color.setTextColor(Color.parseColor(c.getColor()));
        }
        catch (Exception e)
        {
            
        }

        holder.tv_dbl.setText(c.getDbl()+"");

        //هنا بعمل اشاره على الداتا بيز علشان ارجع البيانات بتاعتها بعدين
        holder.tv_model.setTag(c.getId());

    }



    @Override
    public int getItemCount()
    {
        return cars.size();
    }


    class CarViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img ;
        TextView tv_model;
        TextView tv_color;
        TextView tv_dbl;
        public CarViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView)
        {
            super(itemView);
            img = itemView.findViewById(R.id.custome_car_imgview);
            tv_model = itemView.findViewById(R.id.custome_car_tv_model);
            tv_color = itemView.findViewById(R.id.custome_car_tv_color);
            tv_dbl = itemView.findViewById(R.id.custome_car_tv_dbl);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int id = (int) tv_model.getTag();
                    listener.onItemClick(id);

                }
            });
        }
    }

}
