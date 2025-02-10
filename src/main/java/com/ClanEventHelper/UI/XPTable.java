package com.ClanEventHelper.UI;

import com.ClanEventHelper.XPCounter.XpTracker;
import net.runelite.api.Skill;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class XPTable extends JPanel {
    static final int SKILL_NAME_COLUMN_WIDTH = 60;
    static final int SKILL_XP_COUNT_COLUMN_WIDTH = 45;
    static final int SKILL_ICON_COLUMN_WIDTH = 15;

    private List<Object[]> skillData;
    private JTable xpTable;
    private DefaultTableModel tableModel;

    @Inject
    public XPTable(XpTracker xpTracker) {
        skillData = new ArrayList<>();

        tableModel = new DefaultTableModel(new Object[]{"Icon", "Skill", "XP"}, 0);
        xpTable = new JTable(tableModel);

        xpTable.getColumnModel().getColumn(0).setPreferredWidth(SKILL_ICON_COLUMN_WIDTH);
        xpTable.getColumnModel().getColumn(1).setPreferredWidth(SKILL_NAME_COLUMN_WIDTH);
        xpTable.getColumnModel().getColumn(2).setPreferredWidth(SKILL_XP_COUNT_COLUMN_WIDTH);

        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(xpTable);
        add(scrollPane, BorderLayout.CENTER);


        // Listen for XP updates
        xpTracker.setXpUpdateListener((skill, xp) -> {
            log.info("XP update for " + skill.getName() + ": " + xp + " XP");
            SwingUtilities.invokeLater(() -> {
                updateSkillData(skill, xp);
            });
        });

        for (Skill skill : Skill.values()) {
            skillData.add(new Object[]{skill.getIcon(), skill.getName(), 0});
        }
        updateTableData();

    }

    private void updateTableData() {
        // Find the skill data by name and update XP count
        for (int i = 0; i < skillData.size(); i++) {
            Object[] row = skillData.get(i);
            if (row[1].equals(skill.getName())) {
                row[2] = xp;
                break;
            }
        }
        updateTableData();
    }

    private void updateSkillData(Skill skill, int xp) {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Add updated data to the table
        for (Object[] row : skillData) {
            tableModel.addRow(row);
        }
    }
}
