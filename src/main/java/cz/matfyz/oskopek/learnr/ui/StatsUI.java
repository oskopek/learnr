package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.data.StatsCalculator;
import cz.matfyz.oskopek.learnr.tools.Localizable;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/24/14.
 */
public class StatsUI extends JFrame implements Localizable {

    private JTabbedPane tabbedPane;
    private LearnrPane parentPane;

    private StatsCalculator statsCalculator;

    public StatsUI(LearnrPane parentPane, StatsCalculator statsCalculator) {
        this.parentPane = parentPane;
        this.statsCalculator = statsCalculator;
        init();
    }

    private void init() {
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
        String[] totalTabColumns = statsCalculator.totalColumns();
        Object[][] totalTabData = statsCalculator.totalData();

        String[] generalTabColumns = statsCalculator.generalColumns();
        Object[][] generalTabData = statsCalculator.generalData();

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
        return parentPane.localizedText(id);
    }
}
