package cz.matfyz.oskopek.learnr.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Statistics")
public class Statistics extends AbstractPersistable {

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
}
