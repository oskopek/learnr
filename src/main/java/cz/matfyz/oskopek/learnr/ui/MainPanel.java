package cz.matfyz.oskopek.learnr.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by oskopek on 12/3/14.
 */
public class MainPanel extends JPanel implements ActionListener {

    private QuestionAnswerPanel qaPanel;
    private DatasetPanel dataPanel;
    private LearnrPane parentPane;

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
        setLayout(new GridLayout(1, 1));

        qaPanel = new QuestionAnswerPanel();
        dataPanel = new DatasetPanel();

        viewDataset();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("DATA")) {
            viewDataset();
        } else if(e.getActionCommand().equals("QA")) {
            viewQA();
        }
    }
}
