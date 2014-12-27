package cz.matfyz.oskopek.learnr.data;

import cz.matfyz.oskopek.learnr.model.Answer;
import cz.matfyz.oskopek.learnr.model.Dataset;
import cz.matfyz.oskopek.learnr.model.Limits;
import cz.matfyz.oskopek.learnr.model.Question;
import cz.matfyz.oskopek.learnr.tools.DatasetIO;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Created by oskopek on 11/29/14.
 */
public class QuestionIterator implements Iterator<Question> {

    final static private Logger LOGGER = LoggerFactory.getLogger(QuestionIterator.class);
    private Dataset dataset;
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

    @Override
    public boolean hasNext() {
        return questionsLeft() != 0 && limitWatcher.isValidAll();
    }

    @Override
    public void remove() {
        throw new NotImplementedException("remove() is not implemented in QuestionIterator");
    }

    @Override
    public Question next() {
        if (!limitWatcher.isValidAll()) {
            setNullQuestion();
            return null;
        }

        if (questionsLeft() == 0) {
            setNullQuestion();
            return null;
        }

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
        }
        else LOGGER.warn("Called submitAnswer when currentQuestion was null.");
    }

    private Answer checkAnswer(Question question, Answer answer) {
        boolean isGood = answer.checkAnswer(question, dataset.getAnswerCheckType());
        LOGGER.info("Answer of \'{}\' with \'{}\' was {}. ReactionTime: \'{}\'.", question.getText(),
                answer.getValue(), isGood, DatasetIO.convertNanosToHMS(answer.getReactionTime()));
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

    public void resetWeights(int newWeight) {
        if (dataset != null) {
            dataset.getQuestionSet().addAll(dataset.getFinishedSet());
            dataset.getFinishedSet().clear();
            for (Question question : dataset.getQuestionSet()) {
                question.setWeight(newWeight);
            }
        }
        else {
            LOGGER.error("Dataset is null, cannot reset weight to \'{}\'.", newWeight);
        }
    }

    private void setNullQuestion() {
        startTime = 0l;
        currentQuestion = null;
    }

    public void resetLimits(Limits limits) {
        dataset.setLimits(limits);
        limitWatcher = new LimitWatcher(limits);
        limitWatcher.resetAll();
    }
}
