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

import cz.matfyz.oskopek.learnr.model.Limits;
import cz.matfyz.oskopek.learnr.tools.Localizable;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/27/14.
 */
public class LimitsDialog implements Localizable {

    private final LearnrPanel parentPanel;

    public LimitsDialog(LearnrPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    public Limits showDialog(Limits current) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        SpinnerNumberModel session = new SpinnerNumberModel();
        session.setMinimum(1);
        session.setValue(current.getSession());
        SpinnerNumberModel daily = new SpinnerNumberModel();
        daily.setMinimum(1);
        daily.setValue(current.getDaily());

        inputPanel.add(new JLabel(localizedText("session") + ":"));
        inputPanel.add(new JSpinner(session));
        inputPanel.add(new JLabel(localizedText("daily") + ":"));
        inputPanel.add(new JSpinner(daily));

        int result = JOptionPane.showConfirmDialog(parentPanel, inputPanel, "", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Limits limits = new Limits();
            limits.setDaily(daily.getNumber().intValue());
            limits.setSession(session.getNumber().intValue());
            return limits;
        } else {
            return null;
        }
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
