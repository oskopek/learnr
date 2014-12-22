package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.model.Question;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class QuestionPanel extends JPanel {

    private Question question;

    public QuestionPanel() {
        question = new Question();
        question.setName("QUESTION NAME");
        question.setDescription("QUESTION TEXT");
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        JLabel name = new JLabel(question.getName());
        name.setHorizontalAlignment(JLabel.CENTER);
        JLabel text = new JLabel(question.getDescription());
        text.setHorizontalAlignment(JLabel.CENTER);
        add(name, BorderLayout.PAGE_START);
        add(text, BorderLayout.CENTER);
    }

    public void showQuestion(Question question) {
        this.question = question;
        init();
    }

}
