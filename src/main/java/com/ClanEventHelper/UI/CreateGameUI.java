package com.ClanEventHelper.UI;

import com.ClanEventHelper.EventUtility.CreateGame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CreateGameUI extends JPanel {

    //create dropdowns for each item type to be added to tracker, separated by boss, mini-game, etc.
    //have an input field for qty needed
    //add items to table

    static final int LOOT_NAME_COLUMN_WIDTH = 60;
    static final int LOOT_XP_COUNT_COLUMN_WIDTH = 45;

    private final JTable createGameTable;
    private final DefaultTableModel tableModel;

    public CreateGameUI(CreateGame createGame){
        tableModel = new DefaultTableModel(new Object[]{"Item", "QTY"}, 0);
        createGameTable = new JTable(tableModel);

        createGameTable.getColumnModel().getColumn(0).setPreferredWidth(LOOT_NAME_COLUMN_WIDTH);
        createGameTable.getColumnModel().getColumn(1).setPreferredWidth(LOOT_XP_COUNT_COLUMN_WIDTH);

        setLayout(new BorderLayout());

        createGameTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(createGameTable);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

    }

}

