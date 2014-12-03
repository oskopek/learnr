package cz.matfyz.oskopek.learnr.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by oskopek on 12/3/14.
 */
public class MenuPanel extends JPanel {

    public MenuPanel() {
        init();
    }

    private void init() {
        // TODO Buttons or lists?

        setLayout(new GridLayout(3, 1));
        JButton btt1 = new JButton("Show data");
        btt1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // TODO btt1.
            }
        });
        JButton btt2 = new JButton("Show QA");
        btt2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // TODO mainPanel.viewQA();
            }
        });
        JButton btt3 = new JButton("Btt3");
        add(btt1);
        add(btt2);
        add(btt3);

        /*
        setLayout(new GridLayout(1,1));
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.add(0, "1");
        listModel.add(1, "2");
        listModel.add(2, "3");
        JList<String> menuList = new JList<>(listModel);
        menuList.setFixedCellWidth(100);
        menuList.setFixedCellHeight(25);

        add(menuList);
        */


    }

}
