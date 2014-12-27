/*
 * #%L
 * Learnr
 * %%
 * Copyright (C) 2014 Ondrej Skopek
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
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

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionAnswerPanel.class);
    final MainPanel parentPanel;
    final LimitCounterPanel limitCounterPanel;
    final QuestionPanel questionPanel;
    final AnswerPanel answerPanel;
    private QuestionIterator questionIterator;

    public QuestionAnswerPanel(MainPanel parentPanel) {
        this.parentPanel = parentPanel;

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

    public QuestionIterator getQuestionIterator() {
        return questionIterator;
    }

    public void setQuestionIterator(QuestionIterator questionIterator) {
        this.questionIterator = questionIterator;
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
