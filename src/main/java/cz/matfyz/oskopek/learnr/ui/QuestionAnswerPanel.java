package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.data.QuestionIterator;
import cz.matfyz.oskopek.learnr.tools.Localizable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class QuestionAnswerPanel extends JPanel implements Localizable {

    final private Logger LOGGER = LoggerFactory.getLogger(QuestionAnswerPanel.class);

    protected MainPanel parentPanel;
    protected QuestionIterator questionIterator;
    protected LimitCounterPanel limitCounterPanel;
    protected QuestionPanel questionPanel;
    protected AnswerPanel answerPanel;

    public QuestionAnswerPanel(MainPanel parentPanel) {
        this.parentPanel = parentPanel;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        limitCounterPanel = new LimitCounterPanel(this);
        questionPanel = new QuestionPanel(this);
        answerPanel = new AnswerPanel(this);
        add(limitCounterPanel, BorderLayout.PAGE_START);
        add(questionPanel, BorderLayout.CENTER);
        add(answerPanel, BorderLayout.PAGE_END);
    }

    public void nextQuestion() {
        if (questionIterator != null) {
            limitCounterPanel.updateLimitCounters(questionIterator.getLimitWatcher());
            questionPanel.showQuestion(questionIterator.next());
        } else {
            LOGGER.warn("Called nextQuestion() when questionIteratorManager was null.");
        }
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
