package com.negi.mach15group.freeeats;

import java.util.List;

public class Item {

    private String event_name,start_time,type;
    private  String stop_time;
   private String item;
    private Double lat,lon;
    private int images;
    private String phone;

    public Item(String event_name,String item, String start_time,String stop_time, String type, int images, Double lat, Double lon,String phone) {

        this.event_name = event_name;
        this.item = item;
        this.start_time=start_time;
        this.stop_time=stop_time;

        this.type = type;
        this.lat=lat;
        this.lon=lon;
        this.phone=phone;
        this.images = images;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getLat() {
        return lat;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }


    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public String getEvent_name() {
        return event_name;
    }







    public String getType() {
        return type;
    }

    public int getImages() {
        return images;
    }
}
