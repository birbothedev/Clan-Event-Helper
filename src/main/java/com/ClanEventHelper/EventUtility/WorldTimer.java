package com.ClanEventHelper.EventUtility;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Getter
public class WorldTimer {

    LocalDateTime timeStamp;
    String timeZone;

    public WorldTimer(String timeZone){
        this.timeStamp = LocalDateTime.now();
        this.timeZone = String.valueOf(ZonedDateTime.now(ZoneId.of(timeZone)).toLocalDateTime());;
    }


    public String formatTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return timeStamp.format(formatter);

    }

    @Override
    public String toString(){
        return formatTime();
    }

    public void setTimeZone(String timeZone){
        this.timeZone = timeZone;
        this.timeStamp = ZonedDateTime.now(ZoneId.of(timeZone)).toLocalDateTime();
    }

}
