package com.example.eventplannerapp;

public class Event {
    private String eventId;
    private String eventName;
    private String eventDate;
    private String eventLocation;
    private String eventDescription;

    public Event() {
        // Default constructor required for Firebase Realtime Database
    }

    public Event(String eventName, String eventDate, String eventLocation, String eventDescription) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventDescription = eventDescription;
    }


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    // Override toString() method if needed
    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                '}';
    }
}
