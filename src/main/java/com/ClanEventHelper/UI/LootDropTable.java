package com.ClanEventHelper.UI;

import com.ClanEventHelper.LootCounter.LootClass;
import com.ClanEventHelper.LootCounter.LootCounter;
import net.runelite.api.events.ItemSpawned;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LootDropTable extends JPanel {

    static final int LOOT_NAME_COLUMN_WIDTH = 60;
    static final int LOOT_XP_COUNT_COLUMN_WIDTH = 45;

    private final JTable lootTable;
    private final DefaultTableModel tableModel;


    public LootDropTable(LootCounter lootCounter){
        tableModel = new DefaultTableModel(new Object[]{"Item", "QTY"}, 0);
        lootTable = new JTable(tableModel);

        lootTable.getColumnModel().getColumn(0).setPreferredWidth(LOOT_NAME_COLUMN_WIDTH);
        lootTable.getColumnModel().getColumn(1).setPreferredWidth(LOOT_XP_COUNT_COLUMN_WIDTH);

        setLayout(new BorderLayout());

        lootTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(lootTable);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        lootCounter.addLootListener(this::updateLootTable);
    }

    public void updateLootTable(List<LootClass> droppedItems) {
        tableModel.setRowCount(0);

        for (LootClass loot : droppedItems){
            tableModel.addRow(new Object[]{loot.getName(), loot.getQuantity()});
        }

        //update loot table in the panel to reflect drops
        lootTable.revalidate();
        lootTable.repaint();
    }
}
