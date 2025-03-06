package com.ClanEventHelper;

import com.ClanEventHelper.LootCounter.CasketCounter;
import com.ClanEventHelper.LootCounter.LootCounter;
import com.ClanEventHelper.UI.BirboClanEventPanel;
import com.ClanEventHelper.XPTracker.XpTracker;
import com.google.inject.Provides;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;

import java.awt.image.BufferedImage;
import java.io.InputStream;


@Slf4j
@PluginDescriptor(
	name = "Birbo's Clan Events"
)
public class BirboClanEventsPlugin extends Plugin
{

    private Client client;

	@Inject
	private ClientToolbar clientToolbar;

	private NavigationButton navButton;

	@Inject
	private BirboClanEventsConfig config;

	@Inject
	private XpTracker xpTracker;

	@Inject
	private LootCounter lootCounter;

//	@Inject
//	private CasketCounter casketCounter;

	@Inject
	private EventBus eventBus;

	@Override
	protected void startUp() throws Exception
	{

//		casketCounter = new CasketCounter(client);

		eventBus.register(xpTracker);
		eventBus.register(lootCounter);

		BirboClanEventPanel panel = new BirboClanEventPanel(xpTracker, lootCounter);

		if (lootCounter == null) {
			log.error("LootCounter is null!");
		}

		InputStream iconStream = getClass().getResourceAsStream("/icon.png");
		if (iconStream == null) {
			log.error("Icon resource not found! Check the file path.");
			return;
		}
		BufferedImage icon = ImageIO.read(iconStream);

		navButton = NavigationButton.builder()
				.tooltip("Clan Event Helper")
				.icon(icon)
				.priority(13)
				.panel(panel)
				.build();

		clientToolbar.addNavigation(navButton);
		log.info("Navigation button added successfully.");


	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navButton);
		xpTracker.stopTracking();
		eventBus.unregister(xpTracker);
		eventBus.unregister(lootCounter);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	BirboClanEventsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BirboClanEventsConfig.class);
	}
}
