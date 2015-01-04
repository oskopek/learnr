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
package cz.matfyz.oskopek.learnr.data;

import cz.matfyz.oskopek.learnr.model.Answer;
import cz.matfyz.oskopek.learnr.model.Dataset;
import cz.matfyz.oskopek.learnr.model.Limits;
import cz.matfyz.oskopek.learnr.model.Question;
import cz.matfyz.oskopek.learnr.tools.ToolsIO;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Manages the order of asked questions and the process of updating the question and answer after a answer is submitted.
 * Also updates the <code>LimitWatcher</code> and measures the response time.
 * <p/>
 * Note: the <code>remove()</code> method is not implemented, as it has no sense.
 */
public class QuestionIterator implements Iterator<Question> {

    final static private Logger LOGGER = LoggerFactory.getLogger(QuestionIterator.class);
    private final Dataset dataset;
    private Question currentQuestion;
    private long startTime;
    private LimitWatcher limitWatcher;

    public QuestionIterator(Dataset dataset) {
        this.dataset = dataset;
        this.limitWatcher = new LimitWatcher(dataset.getLimits());
        this.limitWatcher.resetSession();
    }

    public int questionsLeft() {
        return dataset.getQuestionSet().size();
    }

    /**
     * Checks if there are any questions left and if no limits are reached.
     *
     * @return true if the next question exists and can be displayed
     */
    @Override
    public boolean hasNext() {
        return questionsLeft() != 0 && limitWatcher.isValidAll();
    }

    /**
     * <b>Not Implemented:</b> Throws a {@link org.apache.commons.lang3.NotImplementedException} every time it is invoked.
     */
    @Override
    public void remove() {
        throw new NotImplementedException("remove() is not implemented in QuestionIterator");
    }

    /**
     * Updates the current question after the answer submission and returns a new one to display.
     *
     * @return the next question to display
     */
    @Override
    public Question next() {
        if (currentQuestion != null) {
            dataset.getQuestionSet().pollLast(); //intentionally ignored (we already have it in currentQuestion)
            if (currentQuestion.getWeight() <= 0) {
                dataset.getFinishedSet().add(currentQuestion);
                LOGGER.debug("Moving question \'{}\' to finishedSet.", currentQuestion.getText());
            } else {
                dataset.getQuestionSet().add(currentQuestion);
            }
        }

        if (questionsLeft() == 0) {
            setNullQuestion();
            return null;
        }

        if (!limitWatcher.isValidAll()) {
            setNullQuestion();
            return null;
        }

        currentQuestion = dataset.getQuestionSet().last();
        LOGGER.debug("Loading question \'{}\'.", currentQuestion.getText());
        limitWatcher.incAll(); // here because of second hasNext check

        startTime = System.nanoTime();
        return currentQuestion;
    }

    public void submitAnswer(Answer answer) {
        if (currentQuestion != null) {
            long reactionTime = System.nanoTime() - startTime;
            answer.setReactionTime(reactionTime);
            answer = checkAnswer(currentQuestion, answer);
            currentQuestion = updateWeight(currentQuestion, answer);
            currentQuestion.getStatistics().submitAnswer(answer);
        } else {
            LOGGER.warn("Called submitAnswer when currentQuestion was null.");
        }
    }

    private Answer checkAnswer(Question question, Answer answer) {
        boolean isGood = answer.checkAnswer(question, dataset.getAnswerCheckType());
        LOGGER.info("Answer of \'{}\' with \'{}\' was {}. ReactionTime: \'{}\'.", question.getText(),
                answer.getValue(), isGood, ToolsIO.convertNanosToHMS(answer.getReactionTime()));
        answer.setGood(isGood);
        return answer;
    }

    private Question updateWeight(Question question, Answer answer) {
        int lastWeight = currentQuestion.getWeight();
        if (answer.isGood()) {
            question.setWeight(question.getWeight() - Math.abs(dataset.getGoodAnswerPenalty())); // absolute value in case of wrong configuration
        } else {
            question.setWeight(question.getWeight() - Math.abs(dataset.getBadAnswerPenalty())); // absolute value in case of wrong configuration
        }
        LOGGER.debug("Updating weight of \'{}\': \'{}\'->\'{}\'", currentQuestion.getText(),
                lastWeight, currentQuestion.getWeight());
        return question;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public LimitWatcher getLimitWatcher() {
        return limitWatcher;
    }

    /**
     * Re-enables all questions (even finished ones) and sets their weight.
     *
     * @param newWeight the new weight to set
     */
    public void resetWeights(int newWeight) {
        dataset.getQuestionSet().addAll(dataset.getFinishedSet());
        dataset.getFinishedSet().clear();
        for (Question question : dataset.getQuestionSet()) {
            question.setWeight(newWeight);
        }
    }

    private void setNullQuestion() {
        startTime = 0l;
        currentQuestion = null;
    }

    /**
     * Sets the dataset's limits and creates a new <code>LimitWatcher</code> to use them.
     *
     * @param limits the limits to set
     */
    public void resetLimits(Limits limits) {
        dataset.setLimits(limits);
        limitWatcher = new LimitWatcher(limits);
        limitWatcher.resetAll();
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }
}
