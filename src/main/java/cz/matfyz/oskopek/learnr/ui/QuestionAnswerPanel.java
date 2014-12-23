package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.data.QuestionIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class QuestionAnswerPanel extends JPanel {

    final private Logger LOGGER = LoggerFactory.getLogger(QuestionAnswerPanel.class);

    protected MainPanel parentPane;
    protected QuestionIterator questionIterator;
    protected QuestionPanel questionPanel;
    protected AnswerPanel answerPanel;

    public QuestionAnswerPanel(MainPanel parentPane) {
        this.parentPane = parentPane;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        questionPanel = new QuestionPanel(this);
        answerPanel = new AnswerPanel(this);
        add(questionPanel, BorderLayout.CENTER);
        add(answerPanel, BorderLayout.PAGE_END);
    }

    public void nextQuestion() {
        if (questionIterator != null) questionPanel.showQuestion(questionIterator.next());
        else LOGGER.warn("Called nextQuestion() when questionIteratorManager was null.");
    }

}
