package cz.matfyz.oskopek.learnr.data;

import cz.matfyz.oskopek.learnr.model.Answer;
import cz.matfyz.oskopek.learnr.model.Dataset;
import cz.matfyz.oskopek.learnr.model.Question;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by oskopek on 11/29/14.
 */
public class QuestionIteratorManager implements Iterator<Question> {

    private Dataset dataset;

    private Question currentQuestion;

    public QuestionIteratorManager(Dataset dataset) {
        this.dataset = dataset;
    }

    public int questionsLeft() {
        return 0;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Question next() {
        // set currentQuestion TODO implement algorithm
        return null;
    }

    @Override
    public void remove() {
        throw new NotImplementedException("");
    }

    @Override
    public void forEachRemaining(Consumer<? super Question> action) {
        throw new NotImplementedException("");
    }

    public void submitAnswer(Answer answer) {
        if (currentQuestion != null) currentQuestion.getStatistics().submitAnswer(answer);
    }

    public Dataset getDataset() {
        return dataset;
    }
}
