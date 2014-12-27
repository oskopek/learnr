package cz.matfyz.oskopek.learnr.data;

import cz.matfyz.oskopek.learnr.model.Answer;
import cz.matfyz.oskopek.learnr.model.Dataset;
import cz.matfyz.oskopek.learnr.model.Question;
import cz.matfyz.oskopek.learnr.tools.DatasetIO;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by oskopek on 12/26/14.
 */
public class StatsCalculator { //TODO: Localize this

    private Dataset dataset;

    private final static String[] totalColumns = {"Statistic", "Value"};

    private final static String[] generalColumns = {"Question", "# Good Ans.", "# Bad Ans.", "# Total Ans.",
            "% Good Ans.", "Avg. Reaction Time"};

    public StatsCalculator(Dataset dataset) {
        this.dataset = dataset;
    }


    /**
     * Generates data into total table from questionSet, writes into data[][] from index onwards (including).
     *
     * @param data
     * @param questionSet
     * @return New index that is writable (or is out of bounds)
     */
    private void generateTotalFromSet(Object[][] data, Set<Question> questionSet) {
        Object[] answerStats = new Object[5];
        int total = 0;
        int timesAsked = 0;
        int timesGood = 0;
        long reactionTimeSum = 0;
        for (Question question : questionSet) {
            for (Answer answer : question.getStatistics().getAnsweredList()) {
                if (answer.isGood()) {
                    timesGood++;
                }
                reactionTimeSum += answer.getReactionTime();
            }

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
     * @param question
     * @return stats according to generalColumns
     */
    private Object[] generalAnswerStats(Question question) {
        Object[] stats = new Object[5];

        int good = 0;
        int bad = 0;
        long sumReactionTime = 0;
        for (Answer answer : question.getStatistics().getAnsweredList()) {
            if (answer.isGood()) {
                good++;
            } else {
                bad++;
            }
            sumReactionTime += answer.getReactionTime();
        }

        stats[0] = good;
        stats[1] = bad;
        stats[2] = good + bad;
        //stats[2] = question.getStatistics().getAnsweredList().size(); //Total answers (should be equal to good+bad)
        stats[3] = ((double) good / (good + bad)) * 100;
        stats[4] = DatasetIO.convertNanosToHMS(Math.round((double) sumReactionTime / (good + bad)));
        return stats;
    }

    /**
     * Generates data into general table from questionSet, writes into data[][] from index onwards (including).
     *
     * @param data
     * @param index
     * @param questionSet
     * @return New index that is writable (or is out of bounds)
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
        index = generateGeneralFromSet(data, index, dataset.getFinishedSet());
        return data;
    }

    public String[] totalColumns() {
        return totalColumns;
    }

    public String[] generalColumns() {
        return generalColumns;
    }

}
