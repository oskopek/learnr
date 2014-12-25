package cz.matfyz.oskopek.learnr.data;

import cz.matfyz.oskopek.learnr.model.Answer;
import cz.matfyz.oskopek.learnr.model.Dataset;
import cz.matfyz.oskopek.learnr.model.Question;
import cz.matfyz.oskopek.learnr.tools.DatasetIO;
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

    public QuestionIterator(Dataset dataset) {
        this.dataset = dataset;
    }

    public int questionsLeft() {
        return dataset.getQuestionSet().size();
    }

    @Override
    public boolean hasNext() {
        return questionsLeft() != 0;
    }

    @Override
    public Question next() { //TODO: if save with an open question, that question doesn't get saved
        if (!hasNext()) {
            startTime = 0l;
            currentQuestion = null;
            return null;
        }

        Question prevQuestion = currentQuestion;
        currentQuestion = dataset.getQuestionSet().pollLast();
        LOGGER.debug("Loading question \'{}\'.", currentQuestion.getName());

        if (prevQuestion != null) {
            if (prevQuestion.getWeight() <= 0) {
                dataset.getFinishedSet().add(prevQuestion);
                LOGGER.debug("Moving question \'{}\' to finishedSet.", prevQuestion.getName());
            } else {
                dataset.getQuestionSet().add(prevQuestion);
            }
        }
        startTime = System.nanoTime();
        return currentQuestion;
    }

    public void submitAnswer(Answer answer) {
        if (currentQuestion != null) {
            long reactionTime = System.nanoTime() - startTime;
            answer.setReactionTime(reactionTime);
            currentQuestion.getStatistics().submitAnswer(answer);

            int lastWeight = currentQuestion.getWeight();
            currentQuestion = updateWeight(currentQuestion, answer);
            LOGGER.debug("Updating weight of \'{}\': \'{}\'->\'{}\'", currentQuestion.getName(),
                    lastWeight, currentQuestion.getWeight());
        }
        else LOGGER.warn("Called submitAnswer when currentQuestion was null.");
    }

    private static Question updateWeight(Question question, Answer answer) {
        boolean isGood = answer.checkAnswer(question);
        answer.setAccepted(isGood);
        LOGGER.info("Answer of \'{}\' with \'{}\' was {}. ReactionTime: \'{}\'.", question.getName(),
                answer.getValue(), isGood, DatasetIO.convertNanosToHMS(answer.getReactionTime()));
        if (isGood) { //TODO make these values dynamic
            question.setWeight(question.getWeight() - 3);
        } else {
            question.setWeight(question.getWeight() - 1);
        }
        return question;
    }

    public Dataset getDataset() {
        return dataset;
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
}
