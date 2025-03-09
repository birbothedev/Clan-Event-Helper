package com.ClanEventHelper.EventUtility;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class WorldTimer {

    LocalDateTime timeStamp;

    public WorldTimer(){
        this.timeStamp = LocalDateTime.now();

    }


    public String formatTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return timeStamp.format(formatter);

    }

    @Override
    public String toString(){
        return formatTime();
    }

}
