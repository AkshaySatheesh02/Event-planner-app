package com.example.eventplannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Bundle bundle = getIntent().getBundleExtra("data");
        String user = bundle.getString("un");
        String pay=bundle.getString("pay");
        ImageView gifImageView = findViewById(R.id.load);
        ImageView gifImageView2 = findViewById(R.id.tick);
        ImageView gifImageView3= findViewById(R.id.booked);
        gifImageView3.setVisibility(View.GONE);
        gifImageView2.setVisibility(View.GONE);
        gifImageView.setVisibility(View.VISIBLE);
        int durationMillis = 2000; // 5 seconds

        // Create a Handler

        Handler handler = new Handler();

// Delay hiding the GIF after the specified duration
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Hide the GIF after the specified duration
                gifImageView.setVisibility(View.GONE);
                if(!pay.equals("cash"))
                    gifImageView2.setVisibility(View.VISIBLE);
                else
                    gifImageView3.setVisibility(View.VISIBLE);


                Handler handler2=new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gifImageView2.setVisibility(View.GONE);
                        Intent intent = new Intent(payment.this, home.class);
                        Bundle b = new Bundle();
                        b.putString("pay",pay);
                        b.putString("un", user);
                        intent.putExtra("data", b);
                        startActivity(intent);
                        finish();
                    }
                },3000);


            }
        }, durationMillis);
    }
    // Assuming you have a reference to the ImageView containing the GIF


// Show the GIF


    // Define the duration in milliseconds


}