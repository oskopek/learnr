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

    private Answer answer;
    private SubmitAnswerListener answerListener;

    final protected JTextField textField;

    public AnswerPanel() {
        answerListener = new SubmitAnswerListener(this);

        setLayout(new GridLayout(1, 2));
        textField = new JTextField();
        textField.addActionListener(answerListener);
        add(textField);

        JButton submitBtt = new JButton("Submit");
        submitBtt.addActionListener(answerListener);
        add(submitBtt);
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

            answer = new Answer();
            answer.setValue(parentPanel.textField.getText());
            // TODO SUBMIT ANSWER SOMEWHERE (QuestionIteratorManager)
        }
    }

}
