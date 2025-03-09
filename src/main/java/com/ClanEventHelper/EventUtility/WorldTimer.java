package com.ClanEventHelper.EventUtility;

import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class WorldTimer {

    ZonedDateTime timeStamp;
    String timeZone;

    public WorldTimer(String timeZone){
        this.timeStamp = ZonedDateTime.now(ZoneId.of(timeZone));
        this.timeZone = timeZone;
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
        this.timeStamp = ZonedDateTime.now(ZoneId.of(timeZone));
    }

}

//add dropdowns to choose from like 3 or 4 different time zones