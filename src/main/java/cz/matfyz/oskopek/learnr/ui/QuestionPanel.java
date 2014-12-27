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

import cz.matfyz.oskopek.learnr.model.Question;
import cz.matfyz.oskopek.learnr.tools.Localizable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class QuestionPanel extends JPanel implements Localizable {

    final private static Logger LOGGER = LoggerFactory.getLogger(QuestionPanel.class);

    protected QuestionAnswerPanel parentPanel;
    private JLabel text;

    public QuestionPanel(QuestionAnswerPanel parentPanel) {
        this.parentPanel = parentPanel;
        init();
        showEmptyQuestion(false);
    }

    private void init() {
        setLayout(new BorderLayout());
        text = new JLabel();
        text.setHorizontalAlignment(JLabel.CENTER);
        add(text, BorderLayout.CENTER);
    }

    public void showQuestion(Question question) {
        if (question != null) {
            LOGGER.debug("Loaded question \'{}\'.", question.getText());
            text.setText(question.getText());
        } else {
            showEmptyQuestion(true);
        }
    }

    private void showEmptyQuestion(boolean showDialog) {
        LOGGER.debug("Loaded null question. Show dialog? {}.", showDialog);
        text.setText("");
        if (showDialog) {
            // Dialog cause guessing:
            if (parentPanel.questionIterator.questionsLeft() == 0) {
                JOptionPane.showMessageDialog(this, localizedText("finished-questions"));
            } else if (!parentPanel.questionIterator.getLimitWatcher().isValidSession()) {
                JOptionPane.showMessageDialog(this, localizedText("reached-session-lim"));
            } else if (!parentPanel.questionIterator.getLimitWatcher().isValidDaily()) {
                JOptionPane.showMessageDialog(this, localizedText("reached-daily-lim"));
            } else {
                LOGGER.error("Unknown end-of-dataset status!");
                JOptionPane.showMessageDialog(this, localizedText("unknown-error"), localizedText("error"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
