package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.data.QuestionIteratorManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class QuestionAnswerPanel extends JPanel {

    protected MainPanel parentPane;
    protected QuestionIteratorManager questionIteratorManager;

    public QuestionAnswerPanel(MainPanel parentPane) {
        this.parentPane = parentPane;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        QuestionPanel questionPanel = new QuestionPanel(this);
        AnswerPanel answerPanel = new AnswerPanel(this);
        add(questionPanel, BorderLayout.CENTER);
        add(answerPanel, BorderLayout.PAGE_END);
    }

}
