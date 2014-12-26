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
    private LimitWatcher limitWatcher;

    public QuestionIterator(Dataset dataset) {
        this.dataset = dataset;
        this.limitWatcher = new LimitWatcher(dataset.getLimits());
    }

    public int questionsLeft() {
        return dataset.getQuestionSet().size();
    }

    @Override
    public boolean hasNext() {
        return questionsLeft() != 0 && limitWatcher.isValidAll();
    }

    @Override
    public Question next() {
        if (!limitWatcher.isValidAll()) { // TODO Limit reached dialog
            setNullQuestion();
            return null;
        }

        if (questionsLeft() == 0) { // TODO end of question dialog
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

        if (questionsLeft() == 0) { //TODO End of question dialog
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
            currentQuestion.getStatistics().submitAnswer(answer);

            int lastWeight = currentQuestion.getWeight();
            currentQuestion = updateWeight(currentQuestion, answer, dataset.getAnswerCheckType());
            LOGGER.debug("Updating weight of \'{}\': \'{}\'->\'{}\'", currentQuestion.getText(),
                    lastWeight, currentQuestion.getWeight());
        }
        else LOGGER.warn("Called submitAnswer when currentQuestion was null.");
    }

    private static Question updateWeight(Question question, Answer answer, Dataset.AnswerCheckType answerCheckType) {
        boolean isGood = answer.checkAnswer(question, answerCheckType);
        answer.setAccepted(isGood);
        LOGGER.info("Answer of \'{}\' with \'{}\' was {}. ReactionTime: \'{}\'.", question.getText(),
                answer.getValue(), isGood, DatasetIO.convertNanosToHMS(answer.getReactionTime()));
        if (isGood) { //TODO make these values dynamic?
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

    private void setNullQuestion() {
        startTime = 0l;
        currentQuestion = null;
    }
}
