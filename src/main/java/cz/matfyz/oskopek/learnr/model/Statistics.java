package cz.matfyz.oskopek.learnr.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Statistics")
public class Statistics extends AbstractPersistable {

    public Statistics() {
        answeredList = new ArrayList<>();
        goodAnswerCount = 0;
        lastAsked = 0;
    }

    private long lastAsked;

    private List<Answer> answeredList;

    private int goodAnswerCount;

    public void submitAnswer(Answer answer) {
        answeredList.add(answer);
    }

    public long getLastAsked() {
        return lastAsked;
    }

    public void setLastAsked(long lastAsked) {
        this.lastAsked = lastAsked;
    }

    public List<Answer> getAnsweredList() {
        return answeredList;
    }

    public void setAnsweredList(List<Answer> answeredList) {
        this.answeredList = answeredList;
    }

    public int getGoodAnswerCount() {
        return goodAnswerCount;
    }

    public void setGoodAnswerCount(int goodAnswerCount) {
        this.goodAnswerCount = goodAnswerCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistics)) return false;

        Statistics that = (Statistics) o;

        return new EqualsBuilder().append(lastAsked, that.lastAsked).append(goodAnswerCount, that.goodAnswerCount)
                .append(answeredList, that.answeredList).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(lastAsked).append(goodAnswerCount).append(answeredList).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(lastAsked).append(goodAnswerCount).append(answeredList).build();
    }
}
