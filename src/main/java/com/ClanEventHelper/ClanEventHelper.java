package com.ClanEventHelper;

import com.google.inject.Provides;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.InputStream;


@Slf4j
@PluginDescriptor(
	name = "Birbo's Clan Events"
)
public class ClanEventHelper extends Plugin
{
	private ClanEventHelperPanel panel;

	@Inject
	private Client client;

	@Inject
	private ClientToolbar clientToolbar;

	private NavigationButton navButton;

	@Inject
	private ExampleConfig config;

	private XpTracker xpTracker;

	@Override
	protected void startUp() throws Exception
	{
		panel = new ClanEventHelperPanel();
		InputStream iconStream = getClass().getResourceAsStream("/icon.png");
        assert iconStream != null;
        BufferedImage icon = ImageIO.read(iconStream);
		// Create the navigation button
		navButton = NavigationButton.builder()
				.tooltip("Clan Event Helper") // Tooltip on hover
				.icon(icon) // Sidebar icon
				.priority(13)
				.panel(panel) // Attach the custom panel
				.build();
		// Add the button to the RuneLite sidebar
		clientToolbar.addNavigation(navButton);
		xpTracker = new XpTracker();

	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navButton);
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
	ExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExampleConfig.class);
	}
}
