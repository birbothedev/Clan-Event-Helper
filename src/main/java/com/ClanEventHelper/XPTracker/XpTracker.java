package com.ClanEventHelper.XPTracker;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.StatChanged;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class XpTracker {
    private final Client client;
    private final Map<Skill, Integer> startingXp = new HashMap<>();
    @Getter
    private int totalXpGained = 0;
    private boolean trackingActive = false;

    @Setter
    private XpUpdateListener xpUpdateListener;

    @Inject
    public XpTracker(Client client) {
        this.client = client;
    }

    public void startTracking() {
        if (client == null) {
            log.error("Client not initialized.");
            return;
        }

        startingXp.clear();
        totalXpGained = 0;
        trackingActive = true;

        // Store the initial XP values when tracking starts
        for (Skill skill : Skill.values()) {
            int currentXp = client.getSkillExperience(skill);
            startingXp.put(skill, currentXp);
        }
    }

    public void stopTracking() {
        trackingActive = false;
    }

    @Subscribe
    public void onStatChanged(StatChanged event) {
        if (!trackingActive) {
            return;
        }

        Skill skill = event.getSkill();
        int currentXp = event.getXp();

        // Get the XP value when tracking started, defaulting to 0 if not found
        int startingValue = startingXp.getOrDefault(skill, currentXp);
        int xpGained = Math.max(0, currentXp - startingValue);

        totalXpGained += xpGained;

        // Notify the listener when XP changes for this skill
        if (xpUpdateListener != null) {
            xpUpdateListener.onXpUpdated(skill, xpGained);
        }

        startingXp.putIfAbsent(skill, currentXp); // Update current XP to prevent duplicate counting
    }

    public interface XpUpdateListener {
        void onXpUpdated(Skill skill, int xp);
    }

}
