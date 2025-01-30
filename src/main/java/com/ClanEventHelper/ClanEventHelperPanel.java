package com.ClanEventHelper;

import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.awt.*;

public class ClanEventHelperPanel extends PluginPanel{

    private JLabel xpLabel;

    public ClanEventHelperPanel() {
        setLayout(new BorderLayout()); // Set the layout manager


        // Create a label
        JLabel titleLabel = new JLabel("My Custom Panel");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create a button
        JButton myButton = new JButton("Start");
        myButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Button Clicked!"));

        xpLabel = new JLabel("XP Gained: 0");

        // Create a panel to hold components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 1));
        contentPanel.add(titleLabel);
        contentPanel.add(myButton);
        contentPanel.add(xpLabel);

        // Add the panel to the PluginPanel
        add(contentPanel, BorderLayout.CENTER);
    }
    private void updateXpDisplay(XpTracker xpTracker)
    {
        // Update the label with the current total XP
        xpLabel.setText("XP Gained: " + xpTracker.getTotalXpGained());
    }
}
