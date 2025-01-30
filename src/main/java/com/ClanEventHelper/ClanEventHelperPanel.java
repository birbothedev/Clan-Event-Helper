package com.ClanEventHelper;

import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ClanEventHelperPanel extends PluginPanel{

    private JLabel xpLabel;

    public ClanEventHelperPanel() {
        setLayout(new BorderLayout()); // Set the layout manager

        // labels
        JLabel titleLabel = new JLabel("My Custom Panel");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        xpLabel = new JLabel("XP Gained: 0");

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
        JButton myButton = new JButton("Start");
        myButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Button Clicked!"));
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
        contentPanel.setLayout(new GridLayout(0, 1));
        contentPanel.add(titleLabel);
        contentPanel.add(myButton);
        contentPanel.add(codeGenerator);
        contentPanel.add(generatedCodeField);
        contentPanel.add(enterCode);
        contentPanel.add(linkCode);
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
