package com.example.eventplannerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText loginusn,loginpw;
    Button loginbtn;
    TextView signupredirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginusn=findViewById(R.id.login_usn);
        loginpw=findViewById(R.id.login_pw);
        loginbtn=findViewById(R.id.login_btn);
        signupredirect=findViewById(R.id.signupRedirect);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUsername() | !validatePassword()){

                }
                else{
                    checkuser();

                }
            }
        });

        signupredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

    }


    public Boolean validateUsername(){
        String val = loginusn.getText().toString();
        if(val.isEmpty()){
            loginusn.setError("username cannot be empty");
            return  false;
        }
        else{
            loginusn.setError(null);
            return true;
        }
    }


    public Boolean validatePassword(){
        String val = loginpw.getText().toString();
        if(val.isEmpty()){
            loginpw.setError("Password cannot be empty");
            return false;
        }
        else{
            loginpw.setError(null);
            return true;
        }
    }

    public void checkuser() {
        String userUsername = loginusn.getText().toString().trim();
        String userPassword = loginpw.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    loginusn.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    assert passwordFromDB != null;
                    if (passwordFromDB.equals(userPassword)) { // Compare passwords using equals() method
                        // Passwords match, successful login
                        loginpw.setError(null);

                        Intent intent = new Intent(LoginActivity.this, EventsActivity.class);
                        Bundle b= new Bundle();
                        b.putString("un",userUsername);
                        intent.putExtra("data",b);
                        startActivity(intent);
                    } else {
                        // Passwords don't match
                        loginpw.setError("Invalid password!");
                        loginpw.requestFocus();
                    }
                } else {
                    // User does not exist
                    loginusn.setError("User does not exist!");
                    loginusn.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }


}