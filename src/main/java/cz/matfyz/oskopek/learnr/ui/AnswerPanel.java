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

import cz.matfyz.oskopek.learnr.model.Answer;
import cz.matfyz.oskopek.learnr.model.Question;
import cz.matfyz.oskopek.learnr.tools.Localizable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Contains the answer text field and the submit button and manages them.
 */
public class AnswerPanel extends JPanel implements Localizable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerPanel.class);
    final QuestionAnswerPanel parentPanel;
    private final JTextField textField;
    private final JButton submitBtt;
    private boolean showGoodAnswersDialog;

    public AnswerPanel(QuestionAnswerPanel parentPanel) {
        this.parentPanel = parentPanel;
        SubmitAnswerListener answerListener = new SubmitAnswerListener(this);

        setLayout(new BorderLayout(0, 5));
        add(Box.createHorizontalStrut(0), BorderLayout.PAGE_START);
        textField = new JTextField();
        textField.addActionListener(answerListener);
        add(textField, BorderLayout.CENTER);

        submitBtt = new JButton(localizedText("submit"));
        submitBtt.addActionListener(answerListener);
        add(submitBtt, BorderLayout.LINE_END);

        showGoodAnswersDialog = false;
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }

    @Override
    public void localizationChanged() {
        submitBtt.setText(localizedText("submit"));
        repaint();
    }

    public void toggleShowGoodAnswersDialog() {
        this.showGoodAnswersDialog = !this.showGoodAnswersDialog;
    }

    public void showGoodAnswersDialog(Question question) {
        String message = localizedText("good-answers") + ": ";
        for (Answer answer : question.getAnswerList()) {
            message += answer.getValue() + ", ";
        }
        if (question.getAnswerList().size() > 0) message = message.substring(0, message.length() - 2) + ".";
        JOptionPane.showMessageDialog(parentPanel, message, localizedText("good-answers-title"), JOptionPane.PLAIN_MESSAGE);
    }

    private class SubmitAnswerListener implements ActionListener {

        private final AnswerPanel answerPanel;

        private SubmitAnswerListener(AnswerPanel answerPanel) {
            this.answerPanel = answerPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LOGGER.debug("Submitted \'{}\' in answer text field.", answerPanel.textField.getText());

            Answer answer = new Answer();
            answer.setValue(answerPanel.textField.getText());
            if (answerPanel.parentPanel.getQuestionIterator() != null) {
                answerPanel.parentPanel.getQuestionIterator().submitAnswer(answer);
                answerPanel.parentPanel.statusPanel.updateLastAnswerCorectness(answer.isGood()); // TODO move last answer correctness FROM HERE
                if (showGoodAnswersDialog && !answer.isGood()) { // if the question was badly answered
                    showGoodAnswersDialog(answerPanel.parentPanel.getQuestionIterator().getCurrentQuestion());
                }
                answerPanel.parentPanel.nextQuestion();
            } else {
                LOGGER.warn("Submitting answer to null questionIteratorManager.");
            }
            answerPanel.textField.setText("");
        }
    }
}
