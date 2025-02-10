package com.ClanEventHelper.UI;

import com.ClanEventHelper.LootCounter.CasketCounter;
import lombok.extern.slf4j.Slf4j;
import javax.swing.*;
import java.awt.*;

@Slf4j
public class CasketCounterTable extends JPanel {
    private final CasketCounter casketCounter;
    private JLabel casketLabel;
    private JLabel initialCasketLabel;

    public CasketCounterTable(CasketCounter casketCounter) {
        this.casketCounter = casketCounter;

        setLayout(new BorderLayout());

        int initialCaskets = casketCounter.getInitialCasketCount();
        initialCasketLabel = new JLabel("Caskets Started With: " + initialCaskets);
        casketLabel = new JLabel("Caskets Gained: 0");
        casketLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(initialCasketLabel, BorderLayout.CENTER);
        add(casketLabel, BorderLayout.CENTER);
    }

    public void updateCasketCount() {
        int casketsGained = casketCounter.getCasketsGained();
        casketLabel.setText("Caskets Gained: " + casketsGained);
    }
}
