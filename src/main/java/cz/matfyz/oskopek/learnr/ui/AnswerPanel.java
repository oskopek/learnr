package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.model.Answer;
import cz.matfyz.oskopek.learnr.tools.Localizable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by oskopek on 12/3/14.
 */
public class AnswerPanel extends JPanel implements Localizable {

    final static private Logger LOGGER = LoggerFactory.getLogger(AnswerPanel.class);

    private SubmitAnswerListener answerListener;

    protected QuestionAnswerPanel parentPanel;
    final protected JTextField textField;

    public AnswerPanel(QuestionAnswerPanel parentPanel) {
        this.parentPanel = parentPanel;
        answerListener = new SubmitAnswerListener(this);

        setLayout(new BorderLayout());
        textField = new JTextField();
        textField.addActionListener(answerListener);
        add(textField, BorderLayout.CENTER);

        JButton submitBtt = new JButton(localizedText("submit"));
        submitBtt.addActionListener(answerListener);
        add(submitBtt, BorderLayout.LINE_END);
    }

    private class SubmitAnswerListener implements ActionListener {

        private AnswerPanel answerPanel;

        private SubmitAnswerListener(AnswerPanel answerPanel) {
            this.answerPanel = answerPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LOGGER.debug("Submitted \'{}\' in answer text field.", answerPanel.textField.getText());

            Answer answer = new Answer();
            answer.setValue(answerPanel.textField.getText());
            if (answerPanel.parentPanel.questionIterator != null) {
                answerPanel.parentPanel.questionIterator.submitAnswer(answer);
                answerPanel.parentPanel.nextQuestion();
            } else {
                LOGGER.warn("Submitting answer to null questionIteratorManager.");
            }
            answerPanel.textField.setText("");
        }
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
