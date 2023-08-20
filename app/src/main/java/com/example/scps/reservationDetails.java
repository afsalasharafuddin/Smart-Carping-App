package com.example.scps;

public class reservationDetails {
    String userId;
    String vehicleNo;
    String date;
    String startTime;
    String endTime;
    String totalTime;
    String slot;
    String fee;

    public reservationDetails(String userId, String vehicleNo, String date, String startTime, String endTime, String totalTime, String slot, String fee) {
        this.userId = userId;
        this.vehicleNo = vehicleNo;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.slot = slot;
        this.fee = fee;
    }

    public String getUserId() {
        return userId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public String getSlot() {
        return slot;
    }

    public String getFee() {
        return fee;
    }
}
