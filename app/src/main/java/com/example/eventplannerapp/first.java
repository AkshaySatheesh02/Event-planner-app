package com.example.eventplannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class first extends AppCompatActivity implements View.OnClickListener {
    Button login;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        login=findViewById(R.id.lu);
        signup=findViewById(R.id.su);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(login)){
            Intent intent= new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent= new Intent(this,SignupActivity.class);
            startActivity(intent);
        }

    }
}