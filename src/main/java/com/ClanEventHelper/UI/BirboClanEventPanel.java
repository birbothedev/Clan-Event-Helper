package com.ClanEventHelper.UI;

import com.ClanEventHelper.EventCode.CodeGenerator;
import com.ClanEventHelper.LootCounter.CasketCounter;
import com.ClanEventHelper.XPTracker.XpTracker;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BirboClanEventPanel extends PluginPanel{

    private final CasketCounterTable casketTable;

    public BirboClanEventPanel(XpTracker xpTracker, CasketCounter casketCounter) {
        this.casketTable = new CasketCounterTable(casketCounter);
        setLayout(new BorderLayout()); // Set the layout manager

        // labels
        JLabel titleLabel = new JLabel("Birbo's Clan Events");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(60, 0));

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

        // buttons
        JButton startButton = new JButton("Start");
        startButton.setSize(150, 50);
        startButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Started Tracking!");
            xpTracker.startTracking();
            casketCounter.scanBankForCaskets();
            casketTable.updateCasketCount();
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

        // Create a panel to hold components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        XPTable xpTable = new XPTable(xpTracker);
        contentPanel.add(xpTable);

        contentPanel.add(casketTable);
        contentPanel.add(titleLabel);
        contentPanel.add(startButton);
        contentPanel.add(codeGenerator);
        contentPanel.add(generatedCodeField);
        contentPanel.add(enterCode);
        contentPanel.add(linkCode);

        // Add the panel to the PluginPanel
        add(contentPanel, BorderLayout.CENTER);

    }
}
