package com.ClanEventHelper;


import com.google.inject.Inject;
import net.runelite.api.Skill;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class BirboClanEventPanel extends PluginPanel{

    @Inject
    private XpTracker xpTracker;

    private JLabel xpLabel;
    private Map<Skill, JLabel> skillXpLabels;

    public BirboClanEventPanel(XpTracker xpTracker) {
        setLayout(new BorderLayout()); // Set the layout manager
        skillXpLabels = new HashMap<>();

        // labels
        JLabel titleLabel = new JLabel("Birbo's Clan Events");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        xpLabel = new JLabel("XP Gained: 0");
        xpLabel.setHorizontalAlignment(SwingConstants.CENTER);

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
        myButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Started Tracking!");
            xpTracker.startTracking();
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
        contentPanel.setLayout(new GridLayout(0, 1));
        contentPanel.add(titleLabel);
        contentPanel.add(myButton);
        contentPanel.add(codeGenerator);
        contentPanel.add(generatedCodeField);
        contentPanel.add(enterCode);
        contentPanel.add(linkCode);


        for (Skill skill : Skill.values()) {
            JLabel skillLabel = new JLabel(skill.getName() + ": 0 XP");
            skillLabel.setHorizontalAlignment(SwingConstants.LEFT);
            skillXpLabels.put(skill, skillLabel);  // Store the label in the map
            contentPanel.add(skillLabel);  // Add the label to the panel
        }

        // Add the panel to the PluginPanel
        add(contentPanel, BorderLayout.CENTER);

        xpTracker.setXpUpdateListener((skill, xp) -> {
            log.info("XP update for " + skill.getName() + ": " + xp + " XP");
            SwingUtilities.invokeLater(() -> {
                JLabel skillLabel = skillXpLabels.get(skill);
                if (skillLabel != null) {
                    skillLabel.setText(skill.getName() + ": " + xp + " XP");
                    skillLabel.revalidate();
                    skillLabel.repaint();
                }
            });
        });
    }
}
