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
        setLayout(new BorderLayout());
        mainPanel = new MainPanel(this);
        menuPanel = new MenuPanel(this);
        add(menuPanel, BorderLayout.LINE_START);
        add(mainPanel, BorderLayout.CENTER);
    }

}
