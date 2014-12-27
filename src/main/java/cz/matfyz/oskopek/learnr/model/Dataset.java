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
package cz.matfyz.oskopek.learnr.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.TreeSet;

/**
 * <code>Dataset</code> represents the current state of questions, limits and general dataset configuration parameters.
 * <p/>
 * The questions in <code>questionSet</code> are <emph>currently</emph> available to be asked,
 * while the questions in <code>finishedSet</code> are <emph>currently</emph> not going to be asked.
 * (For example, until a weight reset is issued).
 */
@XStreamAlias("Dataset")
public class Dataset extends AbstractPersistable {

    public enum AnswerCheckType {CASE_SENSITIVE, CASE_INSENSITIVE, EXACT}

    private TreeSet<Question> questionSet;
    private TreeSet<Question> finishedSet;
    private String name;
    private String description;
    private String author;
    private long createdDate;
    private Limits limits;
    private AnswerCheckType answerCheckType;
    private int goodAnswerPenalty;
    private int badAnswerPenalty;

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

    public int getBadAnswerPenalty() {
        return badAnswerPenalty;
    }

    public void setBadAnswerPenalty(int badAnswerPenalty) {
        this.badAnswerPenalty = badAnswerPenalty;
    }

    public int getGoodAnswerPenalty() {
        return goodAnswerPenalty;
    }

    public void setGoodAnswerPenalty(int goodAnswerPenalty) {
        this.goodAnswerPenalty = goodAnswerPenalty;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(name).append(description).append(author).append(createdDate).append(limits)
                .append(answerCheckType).append(questionSet).append(finishedSet).append(goodAnswerPenalty)
                .append(badAnswerPenalty).build();
    }
}
