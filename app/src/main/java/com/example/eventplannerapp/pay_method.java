package com.example.eventplannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class pay_method extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference eventsRef = database.getReference("events");
    final String[] s = {"cash"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_method);
        Bundle bundle = getIntent().getBundleExtra("data");
        String user = bundle.getString("un");
        String eventId=bundle.getString("eventid");
        RadioGroup pay=findViewById(R.id.radiogrp);
        RadioButton upi=findViewById(R.id.radioButton);
        RadioButton cash=findViewById(R.id.radioButton2);
        Button b=findViewById(R.id.button);


        DatabaseReference eventRef = eventsRef.child(eventId);

        EditText et=findViewById(R.id.box);
        et.setVisibility(View.GONE);
        pay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

                // Perform operations based on the selected radio button
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == upi.getId()) {
                        et.setVisibility(View.VISIBLE);

                        // Option 1 is selected
                        // Perform operations for Option 1
                    } else if (checkedId == cash.getId()) {

                        // Option 2 is selected
                        // Perform operations for Option 2
                    }
                }

    });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String upi_id = et.getText().toString();
                if(!upi_id.equals(""))
                    s[0] =upi_id;
                eventRef.child("payment").setValue(s[0]);
                startintent(user);
            }
        });

}
    void startintent(String user)
    {
        Intent intent = new Intent(pay_method.this, payment.class);
        Bundle b = new Bundle();
        b.putString("un", user);
        b.putString("pay",s[0]);
        intent.putExtra("data", b);
        startActivity(intent);
        finish();
    }


}