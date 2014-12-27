package cz.matfyz.oskopek.learnr.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class LearnrFrame {

    private LearnrPanel learnrPanel;

    public LearnrFrame() {
        learnrPanel = new LearnrPanel();
    }

    private void createAndShowGUI() {
        JFrame learnrFrame = new JFrame("Learnr");

        learnrFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        learnrFrame.setContentPane(learnrPanel);
        learnrFrame.setLocation(100, 100);

        learnrFrame.pack();
        learnrFrame.setMinimumSize(new Dimension(500, 200));
        learnrFrame.setVisible(true);
    }

    public void showFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
