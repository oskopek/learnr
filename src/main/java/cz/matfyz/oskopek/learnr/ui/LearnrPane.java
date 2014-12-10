package cz.matfyz.oskopek.learnr.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class LearnrPane extends JPanel {

    protected MainPanel mainPanel;
    protected MenuPanel menuPanel;

    public LearnrPane() {
        init();
    }

    private void init() {
        // TODO use a splitpane?
        setLayout(new GridLayout(1, 2));
        mainPanel = new MainPanel(this);
        menuPanel = new MenuPanel(this);
        add(menuPanel);
        add(mainPanel);
    }

}
