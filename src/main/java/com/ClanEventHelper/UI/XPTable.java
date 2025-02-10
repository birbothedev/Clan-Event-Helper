package com.ClanEventHelper.UI;

import com.ClanEventHelper.XPCounter.XpTracker;
import net.runelite.api.Skill;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class XPTable extends JPanel {
    static final int SKILL_NAME_COLUMN_WIDTH = 60;
    static final int SKILL_XP_COUNT_COLUMN_WIDTH = 45;

    private List<Object[]> skillData;
    private JTable xpTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;


    @Inject
    public XPTable(XpTracker xpTracker) {
        skillData = new ArrayList<>();

        tableModel = new DefaultTableModel(new Object[]{"Skill", "XP"}, 0);
        xpTable = new JTable(tableModel);

        xpTable.getColumnModel().getColumn(0).setPreferredWidth(SKILL_NAME_COLUMN_WIDTH);
        xpTable.getColumnModel().getColumn(1).setPreferredWidth(SKILL_XP_COUNT_COLUMN_WIDTH);

        setLayout(new BorderLayout());

        xpTable.getTableHeader().setReorderingAllowed(false);

        scrollPane = new JScrollPane(xpTable);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);


        // Listen for XP updates
        xpTracker.setXpUpdateListener((skill, xp) -> {
            log.info("XP update for " + skill.getName() + ": " + xp + " XP");
            SwingUtilities.invokeLater(() -> {
                updateSkillData(skill, xp);
            });
        });

        for (Skill skill : Skill.values()) {
            skillData.add(new Object[]{skill.getName(), 0});
        }

        updateTableData();
        adjustTableSize();
    }

    private void updateSkillData(Skill skill, int xp) {
        // Find the skill data by name and update XP count
        for (int i = 0; i < skillData.size(); i++) {
            Object[] row = skillData.get(i);
            if (row[0].equals(skill.getName())) {
                row[1] = xp;
                break;
            }
        }
        updateTableData();
    }

    private void updateTableData() {
        // Add updated data to the table
        for (int i = 0; i < Skill.values().length; i++) {
            tableModel.addRow(skillData.get(i));
        }
    }

    private void adjustTableSize() {
        int totalHeight = xpTable.getRowHeight() * skillData.size() + xpTable.getTableHeader().getPreferredSize().height;
        xpTable.setPreferredScrollableViewportSize(new Dimension(getWidth(), totalHeight));
        xpTable.revalidate();
        xpTable.repaint();
    }
}
