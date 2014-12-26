package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.data.StatsCalculator;
import cz.matfyz.oskopek.learnr.tools.Localizable;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

/**
 * Created by oskopek on 12/3/14.
 */
public class MenuPanel extends JPanel implements Localizable {

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

        JButton bttStats = new JButton(localizedText("show-stats"));
        bttStats.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (parentPane.mainPanel.qaPanel.questionIterator == null) {
                    JOptionPane.showMessageDialog(parentPane, localizedText("error-show-stats"), localizedText("error"),
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    new StatsUI(parentPane, new StatsCalculator(parentPane.mainPanel.qaPanel.questionIterator.getDataset()));
                }
            }
        });
        JButton bttExit = new JButton(localizedText("exit"));
        bttExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
        JButton bttWeights = new JButton(localizedText("reset-weights"));
        bttWeights.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String resStr = JOptionPane.showInputDialog(parentPane, localizedText("reset-weights") + ":");
                if (resStr == null) return;
                try {
                    int res = Integer.parseInt(resStr);
                    if (parentPane.mainPanel.qaPanel.questionIterator != null) {
                        parentPane.mainPanel.qaPanel.questionIterator.resetWeights(res);
                        parentPane.mainPanel.qaPanel.nextQuestion();
                    } else {
                        LOGGER.debug("Trying to set weights with a null questionIterator.");
                        JOptionPane.showMessageDialog(parentPane, localizedText("no-dataset-loaded"), localizedText("error"),
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException nfe) {
                    LOGGER.debug("Wrong input in reset weights dialog: \'{}\'", resStr);
                    JOptionPane.showMessageDialog(parentPane, localizedText("wrong-weight-val") + ": \'" + resStr + "\'.",
                            localizedText("error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton bttLang = new JButton(localizedText("change-lang"));
        bttLang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String[] languages = LearnrPane.getAvailableLanguages();
                String result = (String) JOptionPane.showInputDialog(parentPane, localizedText("choose-lang") + ":", localizedText("change-lang"),
                        JOptionPane.QUESTION_MESSAGE, null, languages, languages[0]);
                LOGGER.debug("Chose language: \'{}\'", result);
                if (result == null) return; // Cancel button
                parentPane.languageChange(Locale.forLanguageTag(result));
            }
        });

        gbc.gridy = 0;
        add(bttStats, gbc);
        gbc.gridy = 3;
        add(bttExit, gbc);
        gbc.gridy = 1;
        add(bttWeights, gbc);
        gbc.gridy = 2;
        add(bttLang, gbc);
    }

    @Override
    public String localizedText(String id) {
        return parentPane.localizedText(id);
    }
}
