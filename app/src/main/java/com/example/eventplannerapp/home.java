package com.example.eventplannerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference eventsRef = database.getReference("events");

    LinearLayout eventsLayout;
    List<DataSnapshot> eventSnapshots;
    int currentEventIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        eventsLayout = findViewById(R.id.eventsLayout);
        Button prevButton = findViewById(R.id.prevButton);
        Button nextButton = findViewById(R.id.nextButton);

        Bundle b = getIntent().getBundleExtra("data");
        String user = b.getString("un");
        String pay= b.getString("pay");

        Query eventQuery = eventsRef.orderByChild("User").equalTo(user);
        eventQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    eventSnapshots = new ArrayList<>();

                    for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                        eventSnapshots.add(eventSnapshot);
                    }

                    currentEventIndex = 0;
                    displayEventDetails(currentEventIndex);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancellation
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentEventIndex > 0) {
                    currentEventIndex--;
                    displayEventDetails(currentEventIndex);
                } else {
                    Toast.makeText(home.this, "No previous events", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentEventIndex < eventSnapshots.size() - 1) {
                    currentEventIndex++;
                    displayEventDetails(currentEventIndex);
                } else {
                    Toast.makeText(home.this, "No more events", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button addevent=findViewById(R.id.addEvent);
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(home.this,EventsActivity.class);
                Bundle bundle= new Bundle();
                bundle.putString("un",user);
                intent.putExtra("data",bundle);
                startActivity(intent);


            }
        });


    }

   /* private void displayEventDetails(int index) {
        eventsLayout.removeAllViews(); // Clear existing views

        DataSnapshot eventSnapshot = eventSnapshots.get(index);
        String title = eventSnapshot.child("EventName").getValue(String.class);
        String description = eventSnapshot.child("description").getValue(String.class);
        String time = eventSnapshot.child("Time").getValue(String.class);
        String location = eventSnapshot.child("EventLocation").getValue(String.class);
        String date = eventSnapshot.child("Date").getValue(String.class);

        // Create TextViews to display event details
        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText( title);

        TextView descriptionTextView = new TextView(this);
        descriptionTextView.setText("Description: " + description);

        TextView timeTextView = new TextView(this);
        timeTextView.setText("Time: " + time);

        TextView locationTextView = new TextView(this);
        locationTextView.setText("Location: " + location);

        TextView dateTextView = new TextView(this);
        dateTextView.setText("Date: " + date);

        // Add TextViews to the eventsLayout
        eventsLayout.addView(titleTextView);
        eventsLayout.addView(descriptionTextView);
        eventsLayout.addView(timeTextView);
        eventsLayout.addView(locationTextView);
        eventsLayout.addView(dateTextView);
    }*/
   private void displayEventDetails(int index) {
       DataSnapshot eventSnapshot = eventSnapshots.get(index);
       String eventKey = eventSnapshot.getKey();

       String title = eventSnapshot.child("EventName").getValue(String.class);
       String description = eventSnapshot.child("description").getValue(String.class);
       String time = eventSnapshot.child("Time").getValue(String.class);
       String location = eventSnapshot.child("EventLocation").getValue(String.class);
       String date = eventSnapshot.child("Date").getValue(String.class);

       // Check if there are existing TextViews in eventsLayout
       if (eventsLayout.getChildCount() > 0) {
           // Replace the text of existing TextViews
           TextView titleTextView = (TextView) eventsLayout.findViewById(R.id.titleTextView);
           titleTextView.setText( title);

           TextView descriptionTextView = (TextView) eventsLayout.findViewById(R.id.descriptionTextView);
           descriptionTextView.setText( description);

           TextView timeTextView = (TextView) eventsLayout.findViewById(R.id.timeTextView);
           timeTextView.setText( time);

           TextView locationTextView = (TextView) eventsLayout.findViewById(R.id.locationTextView);
           locationTextView.setText( location);

           TextView dateTextView = (TextView) eventsLayout.findViewById(R.id.dateTextView);
           dateTextView.setText( date);
       } else {
           // Create new TextViews and add them to eventsLayout
           TextView titleTextView = new TextView(this);
           titleTextView.setText("Title: " + title);
           eventsLayout.addView(titleTextView);

           TextView descriptionTextView = new TextView(this);
           descriptionTextView.setText("Description: " + description);
           eventsLayout.addView(descriptionTextView);

           TextView timeTextView = new TextView(this);
           timeTextView.setText("Time: " + time);
           eventsLayout.addView(timeTextView);

           TextView locationTextView = new TextView(this);
           locationTextView.setText("Location: " + location);
           eventsLayout.addView(locationTextView);

           TextView dateTextView = new TextView(this);
           dateTextView.setText("Date: " + date);
           eventsLayout.addView(dateTextView);
       }
       Button deleteButton = eventsLayout.findViewById(R.id.deleteButton);
       deleteButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // Call the method to delete the event from the database
               deleteEvent( eventKey);
           }
       });
   }
    private void deleteEvent(String eventKey) {
        DatabaseReference eventReference = eventsRef.child(eventKey);
        eventReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(home.this, "Event deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(home.this, "Failed to delete event", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
