package com.ClanEventHelper.LootCounter;

import com.ClanEventHelper.UI.LootDropTable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ItemSpawned;
import net.runelite.client.game.ItemManager;

import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
public class LootCounter {
    @Getter
    private final List<LootClass> droppedItems = new ArrayList<>();
    private final Map<Item, Integer> itemsList = new HashMap<>();
    private boolean tracking = false;

    private LootDropTable lootDropTable;

    @Inject
    private ItemManager itemManager;

    private final Client client;
    private Consumer<List<LootClass>> lootListener;

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
    public void onItemSpawned(ItemSpawned event) {
        if(tracking){
            TileItem item = event.getItem();
            int itemId = item.getId();
            int quantity = item.getQuantity();

            // Get item name from ItemManager
            ItemComposition itemComposition = itemManager.getItemComposition(itemId);
            String name = itemComposition.getName();

            log.info("Item dropped: {} (ID: {}) x{}", name, itemId, quantity);

            // Store the drop
            droppedItems.add(new LootClass(itemId, name, quantity));

            //update the table
            SwingUtilities.invokeLater(() -> {
                if (lootListener != null) {
                    lootListener.accept(droppedItems);
                }
            });
        }
    }

    public void addLootListener(Consumer<List<LootClass>> listener) {
        this.lootListener = listener;
    }
}
