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
 * Created by oskopek on 12/27/14.
 */
public class LimitCounterPanel extends JPanel implements Localizable {

    private final QuestionAnswerPanel parentPanel;
    private final JLabel sessionCount;
    private final JLabel dailyCount;

    public LimitCounterPanel(QuestionAnswerPanel parentPanel) {
        this.parentPanel = parentPanel;

        setLayout(new FlowLayout());
        JLabel sessionText = new JLabel(localizedText("session") + ":");
        JLabel dailyText = new JLabel(localizedText("daily") + ":");
        sessionCount = new JLabel("0");
        dailyCount = new JLabel("0");
        add(sessionText);
        add(sessionCount);
        add(dailyText);
        add(dailyCount);
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

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
