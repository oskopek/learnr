/*
 * #%L
 * Learnr
 * %%
 * Copyright (C) 2014 Ondrej Skopek
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.data.StatisticsAggregator;
import cz.matfyz.oskopek.learnr.model.Limits;
import cz.matfyz.oskopek.learnr.tools.Localizable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Locale;

/**
 * Panel for managing the configuration of the application and basic navigation.
 */
public class MenuPanel extends JPanel implements Localizable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuPanel.class);

    final LearnrPanel parentPanel;

    public MenuPanel(LearnrPanel learnrPanel) {
        this.parentPanel = learnrPanel;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JButton bttStats = new JButton(localizedText("show-stats"));
        bttStats.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (parentPanel.mainPanel.qaPanel.getQuestionIterator() == null) {
                    JOptionPane.showMessageDialog(parentPanel, localizedText("error-show-stats"), localizedText("error"),
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    new StatisticsDialog(parentPanel, new StatisticsAggregator(parentPanel.mainPanel.qaPanel.getQuestionIterator().getDataset()));
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
                    if (parentPanel.mainPanel.qaPanel.getQuestionIterator() != null) {
                        parentPanel.mainPanel.qaPanel.getQuestionIterator().resetWeights(res);
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
                HashMap<String, String> languages = parentPanel.getAvailableLanguages();
                String[] langArr = new String[languages.size()];
                languages.keySet().toArray(langArr);
                String result = (String) JOptionPane.showInputDialog(parentPanel, localizedText("choose-lang") + ":", localizedText("change-lang"),
                        JOptionPane.QUESTION_MESSAGE, null, langArr, langArr[0]);
                LOGGER.debug("Chose language: \'{}\'", result);
                if (result == null) return; // Cancel button
                parentPanel.languageChange(Locale.forLanguageTag(languages.get(result)));
            }
        });
        JButton bttLimits = new JButton(localizedText("change-lim"));
        bttLimits.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (parentPanel.mainPanel.qaPanel.getQuestionIterator() == null) {
                    LOGGER.debug("Trying to set limits with a null questionIterator.");
                    JOptionPane.showMessageDialog(parentPanel, localizedText("no-dataset-loaded"), localizedText("error"),
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LimitsDialog limitsDialog = new LimitsDialog(parentPanel);
                Limits limits = limitsDialog.showDialog(parentPanel.mainPanel.qaPanel.getQuestionIterator().getDataset().getLimits());
                if (limits == null) {
                    LOGGER.debug("Cancelled setting limits.");
                } else {
                    LOGGER.debug("Setting limits to: \'{}\'.", limits);
                    //This will also reset any progress to today's or this session's limit
                    parentPanel.mainPanel.qaPanel.getQuestionIterator().resetLimits(limits);
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
