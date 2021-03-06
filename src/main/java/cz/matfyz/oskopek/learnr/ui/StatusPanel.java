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

import cz.matfyz.oskopek.learnr.data.LimitWatcher;
import cz.matfyz.oskopek.learnr.tools.Localizable;

import javax.swing.*;
import java.awt.*;

/**
 * Simple panel for displaying limit counter and last answer status.
 */
public class StatusPanel extends JPanel implements Localizable {

    final QuestionAnswerPanel parentPanel;
    private final JLabel sessionCount;
    private final JLabel dailyCount;
    private final JLabel sessionText;
    private final JLabel dailyText;
    private final JLabel lastAnswerText;
    private final JLabel lastAnswerCorectness;
    private boolean lastAnswerWasGood;

    public StatusPanel(QuestionAnswerPanel parentPanel) {
        this.parentPanel = parentPanel;

        setLayout(new FlowLayout());
        sessionText = new JLabel(localizedText("session") + ":");
        dailyText = new JLabel(localizedText("daily") + ":");
        sessionCount = new JLabel("0");
        dailyCount = new JLabel("0");
        lastAnswerText = new JLabel(localizedText("last-answer") + ":");
        lastAnswerCorectness = new JLabel("-");
        add(sessionText);
        add(sessionCount);
        add(dailyText);
        add(dailyCount);
        add(lastAnswerText);
        add(lastAnswerCorectness);
    }

    public void updateLimitCounters(LimitWatcher limitWatcher) {
        sessionCount.setText(limitWatcher.getSessionCounter() + "");
        dailyCount.setText(limitWatcher.getDailyCounter() + "");
        if (limitWatcher.isValidSession()) {
            sessionCount.setForeground(Color.BLACK);
        } else {
            sessionCount.setForeground(Color.RED);
        }
        if (limitWatcher.isValidDaily()) {
            dailyCount.setForeground(Color.BLACK);
        } else {
            dailyCount.setForeground(Color.RED);
        }
    }

    public void updateLastAnswerCorectness(boolean lastAnswerWasGood) {
        this.lastAnswerWasGood = lastAnswerWasGood;
        if (lastAnswerWasGood) {
            lastAnswerCorectness.setText(localizedText("good"));
            lastAnswerCorectness.setForeground(Color.GREEN);
        } else {
            lastAnswerCorectness.setText(localizedText("bad"));
            lastAnswerCorectness.setForeground(Color.RED);
        }
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }

    @Override
    public void localizationChanged() {
        sessionText.setText(localizedText("session") + ":");
        dailyText.setText(localizedText("daily") + ":");
        lastAnswerText.setText(localizedText("last-answer") + ":");
        updateLastAnswerCorectness(lastAnswerWasGood); // translates the good/bad string
    }
}
