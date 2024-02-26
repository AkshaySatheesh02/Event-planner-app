package com.example.eventplannerapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference eventsRef = database.getReference("events");
    ImageButton b;
    Button dis;

    //datestuff
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    //Time stuff
    Button timeButton;
    int hour, minute;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getBundleExtra("data");
        String user = bundle.getString("un");
        String ename = bundle.getString("s");
        int p = bundle.getInt("p");
        b = (ImageButton) findViewById(R.id.imageButton);
        dis = (Button) findViewById(R.id.discard);
        dis.setOnClickListener(this);
        b.setOnClickListener(this);

        //datestuff
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        //end

        //time
        timeButton = findViewById(R.id.timeButton);
        //end

        TextView type = findViewById(R.id.textView12);
        TextView price = findViewById(R.id.textView11);
        type.setText("Type:" + ename);
        price.setText("Price: Rs." + p);
        Button submitButton = findViewById(R.id.buttonCreateEvent);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                // Retrieve the values entered in the EditText fields
                EditText titleEditText = findViewById(R.id.editTextEventname);
                EditText descriptionEditText = findViewById(R.id.editTextEventDescription);
                Button editTextDate = findViewById(R.id.datePickerButton);
                Button editTextTime = findViewById(R.id.timeButton);
                EditText editTextLocation = findViewById(R.id.editTextEventLocation);


                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String time = editTextTime.getText().toString();
                String date = editTextDate.getText().toString();
                String Location = editTextLocation.getText().toString();
                //String p= price.getText().toString();
                //String t=type.getText().toString();

                // Create a new child node under the 'events' node with a unique key
                String eventId = eventsRef.push().getKey();
                assert eventId != null;
                DatabaseReference eventRef = eventsRef.child(eventId);

                // Set the values for the child node using the entered values
                eventRef.child("EventName").setValue(title);
                eventRef.child("User").setValue(user);
                eventRef.child("Time").setValue(time);
                eventRef.child("EventLocation").setValue(Location);
                eventRef.child("Date").setValue(date);
                eventRef.child("Type").setValue(ename);
                eventRef.child("Price").setValue(p);
                eventRef.child("description").setValue(description, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                            //Toast.makeText(MainActivity.this, "Event created successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, pay_method.class);
                            Bundle b = new Bundle();
                            b.putString("eventid",eventId);
                            b.putString("un", user);
                            intent.putExtra("data", b);
                            startActivity(intent);
                            finish();
                            // Perform any additional actions here
                        } else {
                            Toast.makeText(MainActivity.this, "Event creation failed", Toast.LENGTH_SHORT).show();
                            // Handle the error here
                        }
                    }
                });
            }
        });


    }
    @Override
    public void onBackPressed()
    {

        Bundle bundle = getIntent().getBundleExtra("data");
        String user = bundle.getString("un");
        Intent intent = new Intent(this, EventsActivity.class);
        Bundle b= new Bundle();
        b.putString("un",user);
        intent.putExtra("data",b);
        startActivity(intent);

    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }



    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;

    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";

        //default should never happen
        return "JAN";
    }
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
    };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    public void createIntent(String user){
        Intent intent = new Intent(MainActivity.this, home.class);
        Bundle b = new Bundle();
        b.putString("un", user);
        intent.putExtra("data", b);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View view) {
        Bundle bundle=getIntent().getBundleExtra("data");
        String user= bundle.getString("un");
        if (view.equals(b)) {


            createIntent(user);
        }
        else if(view.equals(dis)){
            Intent intent =new Intent(this,EventsActivity.class);
            Bundle b = new Bundle();

            b.putString("un", user);
            intent.putExtra("data", b);
            startActivity(intent);
        }
    }
}


