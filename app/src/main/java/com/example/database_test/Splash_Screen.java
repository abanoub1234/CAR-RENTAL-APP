package com.example.database_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash_Screen extends AppCompatActivity
{
   Animation TopAnimation , BottomAnimation;
   ImageView img;
   TextView Logo;
   TextView Slogan;
   public static final int Splashe_Screen = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_splash_screen);

        TopAnimation = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        BottomAnimation = AnimationUtils.loadAnimation(this , R.anim.bottom_animation);

        img =  findViewById(R.id.img_splash);
        Logo = findViewById(R.id.tv_logo);
        Slogan = findViewById(R.id.tv_slogan);

        img.setAnimation(TopAnimation);
        Logo.setAnimation(BottomAnimation);
        Slogan.setAnimation(BottomAnimation);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(Splash_Screen.this , MainActivity.class);
                startActivity(intent);
                finish();
            }
        },Splashe_Screen);

    }
}