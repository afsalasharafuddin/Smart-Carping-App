package com.example.scps;

public class area_details_class {
    private String location;
    private String slots;
    public area_details_class(){

    }
    public area_details_class(String location, String slots){
        this.location=location;
        this.slots=slots;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }
}
