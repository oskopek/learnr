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
import cz.matfyz.oskopek.learnr.tools.Localizable;
import cz.matfyz.oskopek.learnr.tools.ToolsIO;

import java.util.Set;
import java.util.TreeSet;

/**
 * A simple class that collects statistics from the dataset and returns column and data arrays to display in a {@link cz.matfyz.oskopek.learnr.ui.StatisticsDialog}
 */
public class StatisticsAggregator implements Localizable {

    private final Dataset dataset;
    private final Localizable localizationProvider;

    public StatisticsAggregator(Dataset dataset, Localizable localizationProvider) {
        this.dataset = dataset;
        this.localizationProvider = localizationProvider;
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
        answerStats[4] = ToolsIO.convertNanosToHMS(Math.round((double) reactionTimeSum / timesAsked));

        for (int j = 0; j < answerStats.length; j++) {
            data[j][1] = answerStats[j];
        }
    }

    /**
     * Aggregate data for the <emph>total</emph> tab in <code>StatisticsDialog</code>.
     * Contains summary data from the dataset.
     *
     * @return total summary data
     */
    public Object[][] totalData() {
        String[] statColumn = totalStatistics();
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
     * Returns localized statistic names for the total tab.
     *
     * @return total statistic names
     */
    private String[] totalStatistics() {
        String[] statColumn = new String[5];
        statColumn[0] = "# " + localizedText("of-total-questions");
        statColumn[1] = "# " + localizedText("of-times-asked-total");
        statColumn[2] = "# " + localizedText("of-times-repeated-average");
        statColumn[3] = "% " + localizedText("of-good-answer-average");
        statColumn[4] = localizedText("total-average-reaction-time");
        return statColumn;
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
        stats[4] = ToolsIO.convertNanosToHMS(Math.round((double) question.getStatistics().getReactionTimeSum() / (good + bad)));
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

    /**
     * Aggregate data for the <emph>general</emph> tab in <code>StatisticsDialog</code>.
     * Contains individual question data from the dataset.
     *
     * @return data for the individual questions
     */
    public Object[][] generalData() {
        String[] generalColumns = generalColumns();
        Object[][] data = new Object[dataset.getQuestionSet().size() + dataset.getFinishedSet().size()][generalColumns.length];
        int index = generateGeneralFromSet(data, 0, dataset.getQuestionSet());
        generateGeneralFromSet(data, index, dataset.getFinishedSet());
        return data;
    }

    /**
     * Returns localized header strings for the total (summary) data tab in <code>StatisticsDialog</code>.
     *
     * @return headers for the total data
     */
    public String[] totalColumns() {
        String[] totalColumns = new String[2];
        totalColumns[0] = localizedText("statistic");
        totalColumns[1] = localizedText("value");
        return totalColumns;
    }

    /**
     * Returns localized header strings for the general (individual question) data tab in <code>StatisticsDialog</code>.
     *
     * @return headers for the individual question data
     */
    public String[] generalColumns() {
        String[] generalColumns = new String[6];
        generalColumns[0] = localizedText("question");
        generalColumns[1] = "# " + localizedText("of-good-answers");
        generalColumns[2] = "# " + localizedText("of-bad-answers");
        generalColumns[3] = "# " + localizedText("of-total-answers");
        generalColumns[4] = "% " + localizedText("of-good-answers");
        generalColumns[5] = localizedText("average-reaction-time");
        return generalColumns;
    }

    @Override
    public void localizationChanged() {
        // intentionally empty
    }

    @Override
    public String localizedText(String id) {
        return localizationProvider.localizedText(id);
    }
}
