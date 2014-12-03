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
        setLayout(new GridLayout(2, 1));
        JLabel name = new JLabel(question.getName());
        JLabel text = new JLabel(question.getDescription());
        add(name);
        add(text);
    }

    public void showQuestion(Question question) {
        this.question = question;
        init();
    }

}
