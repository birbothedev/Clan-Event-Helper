package com.ClanEventHelper.LootCounter;

import lombok.Data;

@Data
public class LootClass {
    int id;
    String name;
    int quantity;


    public LootClass(int id, String name, int quantity){
        this.id = id;
         this.name = name;
         this.quantity = quantity;
    }
}
