package cz.matfyz.oskopek.learnr.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class QuestionAnswerPanel extends JPanel {

    public QuestionAnswerPanel() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        QuestionPanel questionPanel = new QuestionPanel();
        AnswerPanel answerPanel = new AnswerPanel();
        add(questionPanel, BorderLayout.CENTER);
        add(answerPanel, BorderLayout.PAGE_END);
    }

}
