package com.example.eventplannerapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventplannerapp.R;

public class EventsActivity extends AppCompatActivity {

    Button book_wed,book_bir,book_far,book_nam;
    Button home,logout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Bundle bundle=getIntent().getBundleExtra("data");
        String user= bundle.getString("un");
        book_wed=findViewById(R.id.weddingbtn);
        book_bir=findViewById(R.id.birthdaybtn);
        book_far=findViewById(R.id.farewellbtn);
        book_nam=findViewById(R.id.namingbtn);
        home=findViewById(R.id.homebtn);
        logout=findViewById(R.id.logout);


        book_wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="Wedding Event";
                int price=6000;
                createIntent(user,s,price);
            }
        });
        book_bir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="Birthday Event";
                int price=3500;
                createIntent(user,s,price);
            }
        });
        book_far.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="Farewell Event";
                int price=2000;
                createIntent(user,s,price);
            }
        });
        book_nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="Naming Ceremony";
                int price=3000;
                createIntent(user,s,price);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventsActivity.this, home.class);
                Bundle b = new Bundle();
                b.putString("un", user);
                intent.putExtra("data", b);
                startActivity(intent);
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
    @Override
    public void onBackPressed()
    {   Bundle bundle = getIntent().getBundleExtra("data");
        String user = bundle.getString("un");
        super.onBackPressed();
        Intent intent = new Intent(this, EventsActivity.class);
        Bundle b= new Bundle();
        b.putString("un",user);
        intent.putExtra("data",b);
        startActivity(intent);

    }
    public void createIntent(String user,String s,int price){
        Intent intent = new Intent(EventsActivity.this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("un", user);
        b.putString("s", s);
        b.putInt("p", price);
        intent.putExtra("data", b);
        startActivity(intent);
        finish();
    }}