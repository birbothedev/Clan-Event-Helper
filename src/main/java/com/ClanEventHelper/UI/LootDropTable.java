package com.ClanEventHelper.UI;

import com.ClanEventHelper.EventUtility.WorldTimer;
import com.ClanEventHelper.LootCounter.LootClass;
import com.ClanEventHelper.LootCounter.LootCounter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;

@Slf4j
public class LootDropTable extends JPanel {

    static final int LOOT_NAME_COLUMN_WIDTH = 60;
    static final int LOOT_QTY_COUNT_COLUMN_WIDTH = 15;
    static final int TIMESTAMP_COLUMN_WIDTH = 60;
    static final int TIMESTAMP_COLUMN_INDEX = 2;

    private final JTable lootTable;
    private final DefaultTableModel tableModel;


    public LootDropTable(LootCounter lootCounter){
        tableModel = new DefaultTableModel(new Object[]{"Item", "QTY", "TIMESTAMP"}, 0);
        lootTable = new JTable(tableModel) {
            // set hover text so user can see full timestamp
            @Override
            public String getToolTipText(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int column = columnAtPoint(e.getPoint());

                if (column == TIMESTAMP_COLUMN_INDEX) {
                    Object value = getValueAt(row, column);
                    return (value != null) ? value.toString() : null;
                }
                return null;
            }
        };

        lootTable.getColumnModel().getColumn(0).setPreferredWidth(LOOT_NAME_COLUMN_WIDTH);
        lootTable.getColumnModel().getColumn(1).setPreferredWidth(LOOT_QTY_COUNT_COLUMN_WIDTH);
        lootTable.getColumnModel().getColumn(2).setPreferredWidth(TIMESTAMP_COLUMN_WIDTH);

        setLayout(new BorderLayout());

        lootTable.getTableHeader().setReorderingAllowed(true);

        JScrollPane scrollPane = new JScrollPane(lootTable);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        lootCounter.addLootListener(this::updateLootTable);
    }

    public void updateLootTable(HashMap<LootClass, String> lootClassStringHashMap) {
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);
            for (LootClass key : lootClassStringHashMap.keySet()) {
                WorldTimer timeStamp = key.getTimestamp();
                tableModel.addRow(new Object[]{key.getName(), key.getQuantity(), timeStamp});
            }

            lootTable.revalidate();
            lootTable.repaint();
        });

    }
}