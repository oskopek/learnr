package cz.matfyz.oskopek.learnr.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Question")
public class Question extends AbstractPersistable implements Comparable<Question> {

    private String name;
    private String description;
    private List<Answer> answerList;
    private Statistics statistics;
    public enum AnswerCheckType {CASE_SENSITIVE, CASE_INSENSITIVE, EXACT}
    private AnswerCheckType answerCheckType;
    private int weight;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

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

    public AnswerCheckType getAnswerCheckType() {
        return answerCheckType;
    }

    public void setAnswerCheckType(AnswerCheckType answerCheckType) {
        this.answerCheckType = answerCheckType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(name).append(description).append(weight).
                append(answerCheckType).append(statistics).append(answerList).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question = (Question) o;
        return new EqualsBuilder().append(name, question.name).append(description, question.description)
                .append(answerList, question.answerList).append(statistics, question.statistics)
                .append(answerCheckType, question.answerCheckType)
                .append(weight, question.weight).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(description).append(weight).
                append(answerList).append(statistics).append(answerCheckType).toHashCode();
    }

    @Override
    public int compareTo(Question o) {
        int weightCompare = Integer.compare(getWeight(), o.getWeight());
        if (weightCompare != 0) return weightCompare;

        return new CompareToBuilder().append(name, o.name).append(description, o.description)
                .append(answerList, o.answerList).append(statistics, o.statistics)
                .append(answerCheckType, o.answerCheckType).toComparison();
    }
}
