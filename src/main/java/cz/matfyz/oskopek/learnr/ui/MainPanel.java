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

    public MainPanel() {
        init();
    }

    public void viewQA() {
        remove(dataPanel);
        add(qaPanel);
    }

    public void viewDataset() {
        remove(qaPanel);
        add(dataPanel);
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
