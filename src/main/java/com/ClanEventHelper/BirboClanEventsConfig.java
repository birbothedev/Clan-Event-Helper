package com.ClanEventHelper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface BirboClanEventsConfig extends Config
{
	@ConfigItem(
		keyName = "greeting",
		name = "Welcome Greeting",
		description = "The message to show to the user when they login"
	)
	default String greeting()
	{
		return "Hello";
	}
}

// have toggles for:
//		-xp tracking, kills tracking, drop tracking, timers, etc
//      -check boxes to choose if they want to scan for caskets, keys, etc