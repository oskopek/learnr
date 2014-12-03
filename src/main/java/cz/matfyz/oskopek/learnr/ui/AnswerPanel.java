package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.model.Answer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by oskopek on 12/3/14.
 */
public class AnswerPanel extends JPanel {

    private Answer answer;

    public AnswerPanel() {
        init();
    }

    private void init() {
        setLayout(new GridLayout(1, 2));
        final JTextField textField = new JTextField();
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("TF: " + textField.getText()); // TODO LOGGING & PUT INTO ONE ACTIONLISTENER
                answer = new Answer();
                answer.setValue(textField.getText());
                // TODO SUBMIT ANSWER SOMEWHERE (QuestionIteratorManager)
            }
        });
        add(textField);

        JButton submitBtt = new JButton("Submit");
        submitBtt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("BTT:" + textField.getText()); // TODO LOGGING
            }
        });
    }

}
