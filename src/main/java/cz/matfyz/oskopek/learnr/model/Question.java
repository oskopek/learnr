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

    private String text;
    private List<Answer> answerList;
    private Statistics statistics;
    private int weight;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        return new ToStringBuilder(this).append(text).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question = (Question) o;
        return new EqualsBuilder().append(text, question.text).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(text).toHashCode();
    }

    @Override
    public int compareTo(Question o) {
        int weightCompare = Integer.compare(getWeight(), o.getWeight()); // comparing by weight, for TreeSet comparing
        if (weightCompare != 0) return weightCompare;

        return new CompareToBuilder().append(text, o.text).toComparison();
    }
}
