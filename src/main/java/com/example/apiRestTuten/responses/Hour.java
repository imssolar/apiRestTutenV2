package com.example.apiRestTuten.responses;

public class Hour {

    private String time;
    private String timezone;

    public Hour(String time, String timezone) {
        this.time = time;
        this.timezone = timezone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}
