package com.ClanEventHelper.UI;

import com.ClanEventHelper.EventUtility.CodeGenerator;
import com.ClanEventHelper.EventUtility.CreateGame;
import com.ClanEventHelper.LootCounter.LootCounter;
import com.ClanEventHelper.XPTracker.XpTracker;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class StarterPanel extends PluginPanel {

    private JPanel currentPanel;

    private final BirboClanEventPanel inGamePanel;
    private final CreateGameUI createGamePanel;

    public StarterPanel(CreateGame createGame, XpTracker xpTracker, LootCounter lootCounter){

        setLayout(new BorderLayout());

        createGamePanel = new CreateGameUI(createGame);
        inGamePanel = new BirboClanEventPanel(xpTracker, lootCounter);

        // Create a panel to hold components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // buttons
        JButton createGameButton = new JButton("Create Game");
        createGameButton.setSize(150, 50);
        createGameButton.addActionListener(e -> {
            switchPanel(createGamePanel);
        });

        // text fields
        JTextField generatedCodeField = new JTextField();
        generatedCodeField.setEditable(false);
        JTextField enterCode = new JTextField("Enter Clan Event Code");
        enterCode.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (enterCode.getText().equals("Enter Clan Event Code")) {
                    enterCode.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (enterCode.getText().isEmpty()) {
                    enterCode.setText("Enter Clan Event Code");
                }
            }
        });

        JButton codeGenerator = new JButton("Generate Code");
        codeGenerator.addActionListener(e -> {
            String eventCode = CodeGenerator.generateEventCode();
            generatedCodeField.setText(eventCode);
        });
        JButton linkCode = new JButton("Link Code");
        linkCode.addActionListener(e -> {
            String eventCode = enterCode.getText();
            CodeGenerator.linkCodes(eventCode);
        });

        JButton joinGameButton = new JButton("Join Game");
        joinGameButton.setSize(150, 50);
        joinGameButton.addActionListener(e -> {
            switchPanel(inGamePanel);
        });

        contentPanel.add(createGameButton);
        contentPanel.add(joinGameButton);

        contentPanel.add(codeGenerator);
        contentPanel.add(generatedCodeField);
        contentPanel.add(enterCode);
        contentPanel.add(linkCode);

        currentPanel = contentPanel;
        add(currentPanel, BorderLayout.CENTER);
    }

    private void switchPanel(JPanel newPanel) {
        // Remove the current panel
        remove(currentPanel);

        // Set the new panel
        currentPanel = newPanel;

        // Add the new panel and revalidate the layout
        add(currentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}


//add button for create game and one for join game, change which ui is shown based on which button is clicked
//make sure plugin automatically opens to current game panel if user is currently in a game
