package com.ClanEventHelper.LootCounter;

import com.ClanEventHelper.EventUtility.WorldTimer;
import lombok.Data;
import lombok.Getter;

@Data
public class LootClass {
    @Getter
    private final int id;
    @Getter
    private final String name;
    @Getter
    private final int quantity;
    private final WorldTimer timeStamp;


    public LootClass(int id, String name, int quantity, WorldTimer timeStamp){
         this.id = id;
         this.name = name;
         this.quantity = quantity;
         this.timeStamp = timeStamp;
    }

    public WorldTimer getTimestamp() {
        return timeStamp;
    }


}
