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
        gbc.gridy = 0;
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
        JButton btt4 = new JButton("Reset weights");
        btt4.addMouseListener(new MouseAdapter() {
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
                        JOptionPane.showMessageDialog(parentPane, "Trying to set weights without a loaded dataset.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException nfe) {
                    LOGGER.debug("Wrong input in reset weights dialog: \'{}\'", resStr);
                    JOptionPane.showMessageDialog(parentPane, "Wrong weight value: \'" + resStr + "\'.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btt1, gbc);
        gbc.gridy = 1;
        add(btt2, gbc);
        gbc.gridy = 3;
        add(btt3, gbc);
        gbc.gridy = 2;
        add(btt4, gbc);
    }

}
