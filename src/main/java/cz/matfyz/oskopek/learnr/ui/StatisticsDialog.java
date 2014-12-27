/*
 * #%L
 * Learnr
 * %%
 * Copyright (C) 2014 Ondrej Skopek
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.data.StatisticsAggregator;
import cz.matfyz.oskopek.learnr.tools.Localizable;

import javax.swing.*;
import java.awt.*;

/**
 * <code>StatisticsDialog</code> displays the statistics provided by a {@link cz.matfyz.oskopek.learnr.data.StatisticsAggregator}.
 */
public class StatisticsDialog extends JDialog implements Localizable {

    final LearnrPanel parentPanel;
    private final JTabbedPane tabbedPane;
    private final StatisticsAggregator statisticsAggregator;

    public StatisticsDialog(LearnrPanel parentPanel, StatisticsAggregator statisticsAggregator) {
        this.parentPanel = parentPanel;
        this.statisticsAggregator = statisticsAggregator;

        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();

        initTables();

        add(tabbedPane, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(200, 200);
        setContentPane(tabbedPane);
        pack();
        setMinimumSize(new Dimension(500, 400));
        setVisible(true);
    }

    private void initTables() {
        String[] totalTabColumns = statisticsAggregator.totalColumns();
        Object[][] totalTabData = statisticsAggregator.totalData();

        String[] generalTabColumns = statisticsAggregator.generalColumns();
        Object[][] generalTabData = statisticsAggregator.generalData();

        JTable totalTab = new JTable(totalTabData, totalTabColumns);
        totalTab.setEnabled(false);
        totalTab.setShowVerticalLines(true);
        totalTab.setShowHorizontalLines(true);
        JScrollPane totalScrollPane = new JScrollPane(totalTab);
        tabbedPane.addTab(localizedText("total"), totalScrollPane);

        JTable generalTab = new JTable(generalTabData, generalTabColumns);
        generalTab.setEnabled(false);
        generalTab.setShowVerticalLines(true);
        generalTab.setShowHorizontalLines(true);
        JScrollPane generalScrollPane = new JScrollPane(generalTab);
        tabbedPane.addTab(localizedText("general"), generalScrollPane);
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
