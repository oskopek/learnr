package cz.matfyz.oskopek.learnr.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class MainPanel extends JPanel {

    protected QuestionAnswerPanel qaPanel;
    private DatasetPanel dataPanel;
    protected LearnrPane parentPane;

    public MainPanel(LearnrPane parentPane) {
        this.parentPane = parentPane;
        init();
    }

    public void viewQA() {
        remove(dataPanel);
        add(qaPanel);
        updateUI();
    }

    public void viewDataset() {
        remove(qaPanel);
        add(dataPanel);
        updateUI();
    }

    private void init() {
        setLayout(new BorderLayout());

        qaPanel = new QuestionAnswerPanel(this);
        dataPanel = new DatasetPanel(this);

        viewDataset();
    }
}
