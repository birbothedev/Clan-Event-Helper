package com.ClanEventHelper.LootCounter;


import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.InventoryID;

import javax.inject.Inject;

@Slf4j
public class CasketCounter {
    private final Client client;
    private int initialCasketCount = 0;
    private int currentCasketCount = 0;
    private static final int CASKET_ITEM_ID = 405;

    @Inject
    public CasketCounter(Client client){
        this.client = client;
    }

    public void scanBankForCaskets() {
        ItemContainer bank = client.getItemContainer(InventoryID.BANK);
        if (bank == null) {
            log.info("Bank is empty or not open.");
            return;
        }

        initialCasketCount = countCaskets(bank);
        currentCasketCount = initialCasketCount;
        log.info("Initial casket count: " + initialCasketCount);
    }

    public int getInitialCasketCount(){
        return currentCasketCount;
    }

    public void checkForNewCaskets() {
        ItemContainer bank = client.getItemContainer(InventoryID.BANK);
        if (bank == null) {
            log.info("Bank is empty or not open.");
            return;
        }

        int newCount = countCaskets(bank);
        if (newCount > currentCasketCount) {
            int casketsGained = newCount - currentCasketCount;
            log.info("Player gained " + casketsGained + " casket(s)!");
            currentCasketCount = newCount;
        }
    }

    private int countCaskets(ItemContainer bank) {
        int count = 0;
        for (Item item : bank.getItems()) {
            if (item.getId() == CASKET_ITEM_ID) {
                count += item.getQuantity();
            }
        }
        return count;
    }

    public int getCasketsGained() {
        return currentCasketCount - initialCasketCount;
    }
}
