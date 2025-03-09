package com.ClanEventHelper.EventUtility;

import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.client.game.ItemManager;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class TrackedItems {
    private static final Set<Integer> TRACKED_ITEM_IDS = new HashSet<>();
    private static final Set<String> TRACKED_ITEM_NAMES = new HashSet<>();

    @Inject
    private ItemManager itemManager;

    public TrackedItems(ItemManager itemManager){
        this.itemManager = itemManager;
    }

    public TrackedItems(Item item){
        int itemId = item.getId();
        String name = "Bones";

        if (TRACKED_ITEM_NAMES.contains(name)){

        }
    }
}
