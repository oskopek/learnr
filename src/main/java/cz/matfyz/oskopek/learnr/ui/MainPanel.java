package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.tools.Localizable;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class MainPanel extends JPanel implements Localizable {

    protected QuestionAnswerPanel qaPanel;
    private DatasetPanel dataPanel;
    protected LearnrPanel parentPanel;

    public MainPanel(LearnrPanel parentPanel) {
        this.parentPanel = parentPanel;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        qaPanel = new QuestionAnswerPanel(this);
        dataPanel = new DatasetPanel(this);
        add(dataPanel, BorderLayout.PAGE_START);
        add(qaPanel, BorderLayout.CENTER);
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
