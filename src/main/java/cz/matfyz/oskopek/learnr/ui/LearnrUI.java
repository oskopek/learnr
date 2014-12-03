package cz.matfyz.oskopek.learnr.ui;

import javax.swing.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class LearnrUI {

    private LearnrPane learnrPane;

    public LearnrUI() {
        learnrPane = new LearnrPane();
    }

    private void createAndShowGUI() {
        JFrame learnrFrame = new JFrame("Learnr");

        learnrFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        learnrFrame.setContentPane(learnrPane);
        learnrFrame.setLocation(100, 100);

        learnrFrame.pack();
        learnrFrame.setVisible(true);
    }

    public void startUI() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
