package cz.matfyz.oskopek.learnr.data;

import cz.matfyz.oskopek.learnr.model.Dataset;

/**
 * Created by oskopek on 11/29/14.
 */
public class QuestionIteratorFactory {

    private Dataset dataset;

    private int N;

    public QuestionIteratorFactory(Dataset dataset, int N) {
        this.dataset = dataset;
        this.N = N;
    }
}
