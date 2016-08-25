package com.augmentis.ayp.myalarmclock;

import java.util.UUID;

/**
 * Created by Apinya on 8/24/2016.
 */
public class AClock {
    private UUID id;
    private String title;
    private int hour;
    private int min;


    public AClock(){
        this(UUID.randomUUID());
    }

    public AClock(UUID uuid){
        this.id = uuid;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        builder.append("UUID=").append(id);
//        builder.append(",Title=").append(title);
//        builder.append(",Hour=").append(hour);
//        builder.append(",Minute=").append(min);
//        return builder.toString();
//    }
}
