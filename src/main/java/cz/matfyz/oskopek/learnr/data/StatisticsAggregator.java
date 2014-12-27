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

import cz.matfyz.oskopek.learnr.model.Dataset;
import cz.matfyz.oskopek.learnr.model.Question;
import cz.matfyz.oskopek.learnr.tools.DatasetIO;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by oskopek on 12/26/14.
 */
public class StatisticsAggregator { //TODO: Localize this

    private final static String[] totalColumns = {"Statistic", "Value"};
    private final static String[] generalColumns = {"Question", "# Good Ans.", "# Bad Ans.", "# Total Ans.",
            "% Good Ans.", "Avg. Reaction Time"};
    private final Dataset dataset;

    public StatisticsAggregator(Dataset dataset) {
        this.dataset = dataset;
    }

    /**
     * Generates data into total table from questionSet, writes into data[][] from index onwards (including).
     *
     * @param data        the data into which to write
     * @param questionSet the set from which to gather question data
     */
    private void generateTotalFromSet(Object[][] data, Set<Question> questionSet) {
        Object[] answerStats = new Object[5];
        int total = 0;
        int timesAsked = 0;
        int timesGood = 0;
        long reactionTimeSum = 0;
        for (Question question : questionSet) {
            timesGood += question.getStatistics().getGoodAnswerCount();
            reactionTimeSum += question.getStatistics().getReactionTimeSum();
            timesAsked += question.getStatistics().getAnsweredList().size();
            total++;
        }

        answerStats[0] = total;
        answerStats[1] = timesAsked;
        answerStats[2] = (double) timesAsked / total;
        answerStats[3] = ((double) timesGood / timesAsked) * 100;
        answerStats[4] = DatasetIO.convertNanosToHMS(Math.round((double) reactionTimeSum / timesAsked));

        for (int j = 0; j < answerStats.length; j++) {
            data[j][1] = answerStats[j];
        }
    }

    public Object[][] totalData() {
        String[] statColumn = {"# Total questions", "# Times asked total", "# Times repeated average",
                "% Average good answer", "Total average reaction time"};
        Object[][] data = new Object[statColumn.length][2];
        for (int i = 0; i < statColumn.length; i++) {
            data[i][0] = statColumn[i];
        }

        Set<Question> totalSet = new TreeSet<>(dataset.getQuestionSet());
        totalSet.addAll(dataset.getFinishedSet());
        generateTotalFromSet(data, totalSet);
        return data;
    }


    /**
     * Generates stats according to generalColumns for question (from column 1 onwards)
     *
     * @param question the question from which to gather data
     * @return stats according to generalColumns
     */
    private Object[] generalAnswerStats(Question question) {
        Object[] stats = new Object[5];

        int good = question.getStatistics().getGoodAnswerCount();
        int bad = question.getStatistics().getAnsweredList().size() - good;

        stats[0] = good;
        stats[1] = bad;
        stats[2] = good + bad;
        stats[3] = ((double) good / (good + bad)) * 100;
        stats[4] = DatasetIO.convertNanosToHMS(Math.round((double) question.getStatistics().getReactionTimeSum() / (good + bad)));
        return stats;
    }

    /**
     * Generates data into general table from questionSet, writes into data[][] from index onwards (including).
     *
     * @param data        the data into which to write
     * @param index       the index (in data[]) from which onwards (including) to write
     * @param questionSet the set from which to gather question data
     * @return New index in data[] that is writable (or is out of bounds)
     */
    private int generateGeneralFromSet(Object[][] data, int index, Set<Question> questionSet) {
        for (Question q : questionSet) {
            data[index][0] = q.getText();
            Object[] answerStats = generalAnswerStats(q);
            for (int j = 1; j - 1 < answerStats.length; j++) {
                data[index][j] = answerStats[j - 1];
            }
            index++;
        }
        return index;
    }

    public Object[][] generalData() {
        Object[][] data = new Object[dataset.getQuestionSet().size() + dataset.getFinishedSet().size()][generalColumns.length];
        int index = generateGeneralFromSet(data, 0, dataset.getQuestionSet());
        generateGeneralFromSet(data, index, dataset.getFinishedSet());
        return data;
    }

    public String[] totalColumns() {
        return totalColumns;
    }

    public String[] generalColumns() {
        return generalColumns;
    }

}
