package cz.matfyz.oskopek.learnr.data;

import cz.matfyz.oskopek.learnr.model.Answer;
import cz.matfyz.oskopek.learnr.model.Dataset;
import cz.matfyz.oskopek.learnr.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Created by oskopek on 11/29/14.
 */
public class QuestionIterator implements Iterator<Question> {

    final private Logger LOGGER = LoggerFactory.getLogger(QuestionIterator.class);
    private Dataset dataset;
    private Question currentQuestion;

    public QuestionIterator(Dataset dataset) {
        this.dataset = dataset;
    }

    public int questionsLeft() {
        return dataset.getQuestionSet().size();
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    @Override
    public boolean hasNext() {
        return dataset.getQuestionSet().isEmpty();
    }

    @Override
    public Question next() {
        if (!hasNext()) return null;

        Question prevQuestion = currentQuestion;
        currentQuestion = dataset.getQuestionSet().pollLast();

        if (prevQuestion != null) {
            if (prevQuestion.getWeight() <= 0) {
                dataset.getFinishedSet().add(prevQuestion);
            } else {
                dataset.getQuestionSet().add(prevQuestion);
            }
        }
        return currentQuestion;
    }

    public void submitAnswer(Answer answer) {
        if (currentQuestion != null) {
            currentQuestion.getStatistics().submitAnswer(answer);
            currentQuestion = score(currentQuestion, answer);
        }
        else LOGGER.warn("Called submitAnswer when currentQuestion was null.");
    }

    private Question score(Question question, Answer answer) {
        if (answer.checkAnswer(question)) {
            question.setWeight(question.getWeight() - 3);
        } else {
            question.setWeight(question.getWeight() - 1);
        }
        return question;
    }

    public Dataset getDataset() {
        return dataset;
    }
}
