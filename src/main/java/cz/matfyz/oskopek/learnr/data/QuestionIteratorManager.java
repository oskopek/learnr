package cz.matfyz.oskopek.learnr.data;

import cz.matfyz.oskopek.learnr.model.Question;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by oskopek on 11/29/14.
 */
public class QuestionIteratorManager implements Iterator<Question> {

    public int questionsLeft() {
        return 0;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Question next() {
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
}
