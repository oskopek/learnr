package cz.matfyz.oskopek.learnr.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class LearnrPane extends JPanel {

    public LearnrPane() {
        init();
    }

    private void init() {
        // TODO use a splitpane?
        setLayout(new GridLayout(1, 2));
        MainPanel mainPanel = new MainPanel();
        MenuPanel menuPanel = new MenuPanel();
        // TODO menuPanel.addListener
        add(menuPanel);
        add(mainPanel);
    }

}
