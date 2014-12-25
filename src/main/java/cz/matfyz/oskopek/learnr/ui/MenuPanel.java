package cz.matfyz.oskopek.learnr.ui;

import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by oskopek on 12/3/14.
 */
public class MenuPanel extends JPanel {

    final private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MenuPanel.class);

    private LearnrPane parentPane;

    public MenuPanel(LearnrPane parentPane) {
        this.parentPane = parentPane;
        init();
    }

    private void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JButton bttStats = new JButton("Show stats");
        bttStats.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // TODO Stats UI
            }
        });
        JButton bttExit = new JButton("Exit");
        bttExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
        JButton bttWeights = new JButton("Reset weights");
        bttWeights.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String resStr = JOptionPane.showInputDialog(parentPane, "Reset weights:");
                if (resStr == null) return;
                try {
                    int res = Integer.parseInt(resStr);
                    if (parentPane.mainPanel.qaPanel.questionIterator != null) {
                        parentPane.mainPanel.qaPanel.questionIterator.resetWeights(res);
                        parentPane.mainPanel.qaPanel.nextQuestion();
                    } else {
                        LOGGER.debug("Trying to set weights with a null questionIterator.");
                        JOptionPane.showMessageDialog(parentPane, "No dataset loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException nfe) {
                    LOGGER.debug("Wrong input in reset weights dialog: \'{}\'", resStr);
                    JOptionPane.showMessageDialog(parentPane, "Wrong weight value: \'" + resStr + "\'.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridy = 0;
        add(bttStats, gbc);
        gbc.gridy = 2;
        add(bttExit, gbc);
        gbc.gridy = 1;
        add(bttWeights, gbc);
    }

}
