package com.ClanEventHelper;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PluginLauncher
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BirboClanEventsPlugin.class);
		RuneLite.main(args);
	}
}