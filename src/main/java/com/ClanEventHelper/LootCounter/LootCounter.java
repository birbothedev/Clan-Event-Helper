package com.ClanEventHelper.LootCounter;

import com.ClanEventHelper.EventUtility.WorldTimer;
import com.ClanEventHelper.UI.LootDropTable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.game.ItemManager;

import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemStack;

import javax.inject.Inject;
import javax.swing.*;
import java.util.HashMap;
import java.util.function.Consumer;

@Slf4j
public class LootCounter {
    @Getter
    private final HashMap<LootClass, String> droppedItems = new HashMap<>();
    private boolean tracking = false;

    private LootDropTable lootDropTable;

    @Inject
    private ItemManager itemManager;

    private final Client client;
    private Consumer<HashMap<LootClass, String>> lootListener;


    @Inject
    public LootCounter(Client client) {
        this.client = client;
    }

    public void startTracking() {
        if (!tracking) {
            log.info("Starting loot tracking...");
            droppedItems.clear(); // Clear previous loot
            tracking = true; // Enable tracking
        }
    }

    public void stopTracking() {
        if (tracking) {
            log.info("Stopping loot tracking...");
            tracking = false; // Disable tracking
        }
    }

    @Subscribe
    // method gets called automatically when an item is spawned
    public void onNpcLootReceived(NpcLootReceived event) {
        log.info("NpcLootReceived event triggered");
        if(tracking){
            if (event.getNpc() != null){
                NPC npc = event.getNpc();

                for (ItemStack item : event.getItems()){
                    int itemId = item.getId();
                    int quantity = item.getQuantity();
                    WorldTimer timeStamp = new WorldTimer("Europe/London");

                    // Get item name from ItemManager
                    ItemComposition itemComposition = itemManager.getItemComposition(itemId);
                    String name = itemComposition.getName();

                    log.info("Item dropped: {} (ID: {}) x{}", name, itemId, quantity);

                    // Store the drop
                    LootClass loot = new LootClass(itemId, name, quantity, timeStamp);
                    droppedItems.put(loot, String.valueOf(timeStamp));

                    //update the table
                    SwingUtilities.invokeLater(() -> {
                        if (lootListener != null) {
                            lootListener.accept(droppedItems);
                        }
                    });
                }
            }
        }
    }

    public void addLootListener(Consumer<HashMap<LootClass, String>> listener) {
        this.lootListener = listener;
    }

}

//need to add ability to track items from other activies like thieving, minigame rewards, etc