package cz.matfyz.oskopek.learnr.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Question")
public class Question extends AbstractPersistable {

    private String name;

    private String description;

    private List<Answer> answerList;

    private Statistics statistics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(name).append(description).append(statistics).append(answerList).build();
    }
}
