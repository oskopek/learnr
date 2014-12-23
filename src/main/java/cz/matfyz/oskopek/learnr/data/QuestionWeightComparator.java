package cz.matfyz.oskopek.learnr.data;

import cz.matfyz.oskopek.learnr.model.Question;

import java.util.Comparator;

/**
 * Created by oskopek on 12/23/14.
 */
public class QuestionWeightComparator implements Comparator<Question> {

    @Override
    public int compare(Question o1, Question o2) {
        return Integer.compare(o1.getWeight(), o2.getWeight());
    }
}
