package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by oskopek on 12/3/14.
 */
public class AnswerPanel extends JPanel {
    private SubmitAnswerListener answerListener;

    protected QuestionAnswerPanel parentPane;
    final protected JTextField textField;

    public AnswerPanel(QuestionAnswerPanel parentPane) {
        this.parentPane = parentPane;
        answerListener = new SubmitAnswerListener(this);

        setLayout(new BorderLayout());
        textField = new JTextField();
        textField.addActionListener(answerListener);
        add(textField, BorderLayout.CENTER);

        JButton submitBtt = new JButton("Submit");
        submitBtt.addActionListener(answerListener);
        add(submitBtt, BorderLayout.LINE_END);
    }

    private class SubmitAnswerListener implements ActionListener {

        private AnswerPanel parentPanel;

        final private Logger LOGGER = LoggerFactory.getLogger(AnswerPanel.class);

        private SubmitAnswerListener(AnswerPanel parentPanel) {
            this.parentPanel = parentPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LOGGER.debug("Submitted \'{}\' in answer text field.", parentPanel.textField.getText());

            Answer answer = new Answer();
            answer.setValue(parentPanel.textField.getText());
            if (parentPanel.parentPane.questionIteratorManager != null) {
                parentPanel.parentPane.questionIteratorManager.submitAnswer(answer);
            } else {
                LOGGER.warn("Submitting answer to null questionIteratorManager.");
            }
        }
    }

}
