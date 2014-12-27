package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.data.StatsCalculator;
import cz.matfyz.oskopek.learnr.model.Limits;
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

    private LearnrPanel parentPanel;

    public MenuPanel(LearnrPanel parentPanel) {
        this.parentPanel = parentPanel;
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
                if (parentPanel.mainPanel.qaPanel.questionIterator == null) {
                    JOptionPane.showMessageDialog(parentPanel, localizedText("error-show-stats"), localizedText("error"),
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    new StatsUI(parentPanel, new StatsCalculator(parentPanel.mainPanel.qaPanel.questionIterator.getDataset()));
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
                String resStr = JOptionPane.showInputDialog(parentPanel, localizedText("reset-weights") + ":");
                if (resStr == null) return;
                try {
                    int res = Integer.parseInt(resStr);
                    if (parentPanel.mainPanel.qaPanel.questionIterator != null) {
                        parentPanel.mainPanel.qaPanel.questionIterator.resetWeights(res);
                        parentPanel.mainPanel.qaPanel.nextQuestion();
                    } else {
                        LOGGER.debug("Trying to set weights with a null questionIterator.");
                        JOptionPane.showMessageDialog(parentPanel, localizedText("no-dataset-loaded"), localizedText("error"),
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException nfe) {
                    LOGGER.debug("Wrong input in reset weights dialog: \'{}\'", resStr);
                    JOptionPane.showMessageDialog(parentPanel, localizedText("wrong-weight-val") + ": \'" + resStr + "\'.",
                            localizedText("error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton bttLang = new JButton(localizedText("change-lang"));
        bttLang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String[] languages = LearnrPanel.getAvailableLanguages();
                String result = (String) JOptionPane.showInputDialog(parentPanel, localizedText("choose-lang") + ":", localizedText("change-lang"),
                        JOptionPane.QUESTION_MESSAGE, null, languages, languages[0]);
                LOGGER.debug("Chose language: \'{}\'", result);
                if (result == null) return; // Cancel button
                parentPanel.languageChange(Locale.forLanguageTag(result));
            }
        });
        JButton bttLimits = new JButton(localizedText("change-lim"));
        bttLimits.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (parentPanel.mainPanel.qaPanel.questionIterator == null) {
                    LOGGER.debug("Trying to set limits with a null questionIterator.");
                    JOptionPane.showMessageDialog(parentPanel, localizedText("no-dataset-loaded"), localizedText("error"),
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LimitsDialog limitsDialog = new LimitsDialog(parentPanel);
                Limits limits = limitsDialog.showDialog(parentPanel.mainPanel.qaPanel.questionIterator.getDataset().getLimits());
                if (limits == null) {
                    LOGGER.debug("Cancelled setting limits.");
                    return;
                } else {
                    LOGGER.debug("Setting limits to: \'{}\'.", limits);
                    //This will also reset any progress to today's or this session's limit
                    parentPanel.mainPanel.qaPanel.questionIterator.resetLimits(limits);
                }
            }
        });

        gbc.gridy = 0;
        add(bttStats, gbc);
        gbc.gridy = 1;
        add(bttWeights, gbc);
        gbc.gridy = 2;
        add(bttLimits, gbc);
        gbc.gridy = 3;
        add(bttLang, gbc);
        gbc.gridy = 4;
        add(bttExit, gbc);
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
