package com.example.httesti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = (ImageView) findViewById(R.id.imageView5);
        imageView.setImageResource(R.drawable.stickmanrun1);

        imageView2 = (ImageView) findViewById(R.id.imageView8);

        //this is for making the animation in the downloadscreen
        //we made the animation by just swappng two diffrent imageviews images


        imageView.postDelayed(swapImage, i);
        i += 100;
        imageView.postDelayed(swapImage2, i);
        i += 100;
        imageView.postDelayed(swapImage3, i);
        i += 100;
        imageView.postDelayed(swapImage4, i);
        i += 100;
        imageView.postDelayed(swapImage5, i);
        i += 100;
        imageView.postDelayed(swapImage6, i);
        i += 100;
        imageView.postDelayed(swapImage, i);
        i += 100;
        imageView.postDelayed(swapImage2, i);
        i += 100;
        imageView.postDelayed(swapImage3, i);
        i += 100;
        imageView.postDelayed(swapImage4, i);
        i += 100;
        imageView.postDelayed(swapImage5, i);
        i += 100;
        imageView.postDelayed(swapImage6, i);

        imageView.postDelayed(swapImage7,i);

        i += 100;
        imageView.postDelayed(swapImage, i);
        i += 100;
        imageView.postDelayed(swapImage2, i);
        i += 100;
        imageView.postDelayed(swapImage3, i);
        i += 100;
        imageView.postDelayed(swapImage4, i);
        i += 100;
        imageView.postDelayed(swapImage5, i);
        i += 100;
        imageView.postDelayed(swapImage6, i);
        i += 100;
        imageView.postDelayed(swapImage, i);
        i += 100;
        imageView.postDelayed(swapImage2, i);
        i += 100;
        imageView.postDelayed(swapImage3, i);
        i += 100;
        imageView.postDelayed(swapImage4, i);
        i += 100;
        imageView.postDelayed(swapImage5, i);
        i += 100;
        imageView.postDelayed(swapImage6, i);

        imageView.postDelayed(swapImage8,i);

        i += 100;
        imageView.postDelayed(swapImage, i);
        i += 100;
        imageView.postDelayed(swapImage2, i);
        i += 100;
        imageView.postDelayed(swapImage3, i);
        i += 100;
        imageView.postDelayed(swapImage4, i);
        i += 100;
        imageView.postDelayed(swapImage5, i);
        i += 100;
        imageView.postDelayed(swapImage6, i);
        i += 100;
        imageView.postDelayed(swapImage, i);
        i += 100;
        imageView.postDelayed(swapImage2, i);
        i += 100;
        imageView.postDelayed(swapImage3, i);
        i += 100;
        imageView.postDelayed(swapImage4, i);
        i += 100;
        imageView.postDelayed(swapImage5, i);
        i += 100;
        imageView.postDelayed(swapImage6, i);
        i += 100;

        imageView.postDelayed(swapImage9,i);

        i += 100;
        imageView.postDelayed(swapImage, i);
        i += 100;
        imageView.postDelayed(swapImage2, i);
        i += 100;
        imageView.postDelayed(swapImage3, i);
        i += 100;
        imageView.postDelayed(swapImage4, i);
        i += 100;
        imageView.postDelayed(swapImage5, i);
        i += 100;
        imageView.postDelayed(swapImage6, i);
        i += 100;
        imageView.postDelayed(swapImage, i);
        i += 100;
        imageView.postDelayed(swapImage2, i);
        i += 100;
        imageView.postDelayed(swapImage3, i);
        i += 100;
        imageView.postDelayed(swapImage4, i);
        i += 100;
        imageView.postDelayed(swapImage5, i);
        i += 100;
        imageView.postDelayed(swapImage6, i);
        i += 100;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        },4200);


    }

    //Methods for swappng the images

    ImageView imageView;
    ImageView imageView2;
    int i = 0;
    Runnable swapImage = new Runnable() {
        @Override
        public void run() {
            imageView.setImageResource(R.drawable.stickmanrun6);
        }
    };

    Runnable swapImage2 = new Runnable() {
        @Override
        public void run() {
            imageView.setImageResource(R.drawable.stickmanrun5);
        }
    };
    Runnable swapImage3 = new Runnable() {
        @Override
        public void run() {
            imageView.setImageResource(R.drawable.stickmanrun4);
        }
    };
    Runnable swapImage4 = new Runnable() {
        @Override
        public void run() {
            imageView.setImageResource(R.drawable.stickmanrun3);
        }
    };
    Runnable swapImage5 = new Runnable() {
        @Override
        public void run() {
            imageView.setImageResource(R.drawable.stickmanrun2);
        }
    };
    Runnable swapImage6 = new Runnable() {
        @Override
        public void run() {
            imageView.setImageResource(R.drawable.stickmanrun1);
        }
    };


    Runnable swapImage7 = new Runnable() {
        @Override
        public void run() {
            imageView2.setImageResource(R.drawable.ic_sun);
        }
    };
    Runnable swapImage8 = new Runnable() {
        @Override
        public void run() {
            imageView2.setImageResource(R.drawable.ic_rain);
        }
    };
    Runnable swapImage9 = new Runnable() {
        @Override
        public void run() {
            imageView2.setImageResource(R.drawable.ic_snow);
        }
    };
}