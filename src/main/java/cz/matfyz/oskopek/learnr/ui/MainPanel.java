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
    protected LearnrPane parentPane;

    public MainPanel(LearnrPane parentPane) {
        this.parentPane = parentPane;
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
        return parentPane.localizedText(id);
    }
}
