package com.example.scps;

public class histo {
    String date,startTime,endTime,fee,vehicleNo,userId;
    public histo(){

    }

    public histo(String date, String startTime, String endTime, String fee, String vehicleNo,String userId) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fee = fee;
        this.vehicleNo = vehicleNo;
        this.userId=userId;
    }
public String getUserId(){
        return userId;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
}
