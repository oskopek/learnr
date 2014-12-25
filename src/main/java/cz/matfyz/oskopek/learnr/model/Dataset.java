package cz.matfyz.oskopek.learnr.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.TreeSet;

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Dataset")
public class Dataset extends AbstractPersistable {

    private TreeSet<Question> questionSet;
    private TreeSet<Question> finishedSet;
    private String name;
    private String description;
    private String author;
    private long createdDate;
    private Limits limits;
    public enum AnswerCheckType {CASE_SENSITIVE, CASE_INSENSITIVE, EXACT}
    private AnswerCheckType answerCheckType;

    public TreeSet<Question> getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(TreeSet<Question> questionSet) {
        this.questionSet = questionSet;
    }

    public TreeSet<Question> getFinishedSet() {
        return finishedSet;
    }

    public void setFinishedSet(TreeSet<Question> finishedSet) {
        this.finishedSet = finishedSet;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public Limits getLimits() {
        return limits;
    }

    public void setLimits(Limits limits) {
        this.limits = limits;
    }

    public AnswerCheckType getAnswerCheckType() {
        return answerCheckType;
    }

    public void setAnswerCheckType(AnswerCheckType answerCheckType) {
        this.answerCheckType = answerCheckType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(name).append(description).append(author).append(createdDate).append(limits)
                .append(answerCheckType).append(questionSet).append(finishedSet).build();
    }
}
