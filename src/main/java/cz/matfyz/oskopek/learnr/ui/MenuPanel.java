package cz.matfyz.oskopek.learnr.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by oskopek on 12/3/14.
 */
public class MenuPanel extends JPanel {

    private LearnrPane parentPane;

    public MenuPanel(LearnrPane parentPane) {
        this.parentPane = parentPane;
        init();
    }

    private void init() {
        setLayout(new GridLayout(3, 1));
        JButton btt1 = new JButton("Show data");
        btt1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                parentPane.mainPanel.viewDataset();
            }
        });
        JButton btt2 = new JButton("Show QA");
        btt2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                parentPane.mainPanel.viewQA();
            }
        });
        JButton btt3 = new JButton("Exit");
        btt3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
        add(btt1);
        add(btt2);
        add(btt3);
    }

}
