package com.ClanEventHelper.UI;

import com.ClanEventHelper.EventUtility.CodeGenerator;
import com.ClanEventHelper.LootCounter.LootCounter;
import com.ClanEventHelper.XPTracker.XpTracker;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BirboClanEventPanel extends PluginPanel{

//    private final CasketCounterTable casketTable;

    public BirboClanEventPanel(XpTracker xpTracker, LootCounter lootCounter) {
//        this.casketTable = new CasketCounterTable(casketCounter);
        setLayout(new BorderLayout()); // Set the layout manager

        // labels
        JLabel titleLabel = new JLabel("Birbo's Clan Events");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(60, 0));


        // buttons
        JButton startButton = new JButton("Start");
        startButton.setSize(150, 50);
        startButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Started Tracking!");
            xpTracker.startTracking();
            lootCounter.startTracking();
        });

        // Create a panel to hold components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        XPTable xpTable = new XPTable(xpTracker);
        contentPanel.add(xpTable);

        LootDropTable lootDropTable = new LootDropTable(lootCounter);
        contentPanel.add(lootDropTable);

        contentPanel.add(titleLabel);
        contentPanel.add(startButton);

        // Add the panel to the PluginPanel
        add(contentPanel, BorderLayout.CENTER);

    }
}
