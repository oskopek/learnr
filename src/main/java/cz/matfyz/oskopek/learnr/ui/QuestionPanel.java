package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.model.Question;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class QuestionPanel extends JPanel {

    private Question question;
    protected QuestionAnswerPanel parentPane;
    private JLabel name;
    private JLabel text;

    public QuestionPanel(QuestionAnswerPanel parentPane) {
        this.parentPane = parentPane;
        question = new Question();
        question.setName("QUESTION NAME");
        question.setDescription("QUESTION TEXT");
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        name = new JLabel(question.getName());
        name.setHorizontalAlignment(JLabel.CENTER);
        text = new JLabel(question.getDescription());
        text.setHorizontalAlignment(JLabel.CENTER);
        add(name, BorderLayout.PAGE_START);
        add(text, BorderLayout.CENTER);
    }

    public void showQuestion(Question question) {
        this.question = question;
        name.setText(question.getName());
        text.setText(question.getDescription());
    }

}
