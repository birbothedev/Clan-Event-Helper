package com.ClanEventHelper;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.StatChanged;
import net.runelite.client.eventbus.Subscribe;

@Slf4j
public class XpTracker {
    private int totalXpGained = 0;

    // Method to retrieve the total XP gained
    public int getTotalXpGained()
    {
        return totalXpGained;
    }

    // Subscribe to the ExperienceChanged event
    @Subscribe
    public void onExperienceChanged(StatChanged event)
    {
        // Get the experience gained in the event
        int xpGained = event.getXp();

        // Add the gained XP to the total
        totalXpGained += xpGained;

        // Log the XP gained and the total XP
        log.info("XP Gained: {}. Total XP Gained: {}", xpGained, totalXpGained);
    }
}
