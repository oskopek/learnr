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
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * <code>Question</code> is an object mostly associated with the <code>Dataset</code> it belongs to.
 * <p/>
 * Only the <code>statistics</code> (internally) and <code>weight</code> fields change during usage.
 * <p/>
 * <b>WARNING</b>: Do not mistake {@link #getAnswerList()} for {@link Statistics#getAnsweredList()}!
 */
@XStreamAlias("Question")
public class Question extends AbstractPersistable implements Comparable<Question> {

    private final String text;
    private final List<Answer> answerList;
    private final Statistics statistics;
    private int weight;

    public Question(String text, Statistics statistics, List<Answer> answerList) {
        this.answerList = answerList;
        this.text = text;
        this.statistics = statistics;
    }

    public Question(String text, Statistics statistics, List<Answer> answerList, int weight) {
        this.answerList = answerList;
        this.text = text;
        this.statistics = statistics;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public Statistics getStatistics() {
        return statistics;
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

    /**
     * Compares two questions by <code>weight</code> in decreasing order, or in case of equality, by their <code>text</code>s.
     * <p/>
     * <b>WARNING</b>: Doesn't conform to the output of <code>equals</code> and <code>hashCode</code>!
     *
     * @param o the other question
     * @return -1, 0, or 1, in decreasing order by <code>weight</code>, or if <code>weight</code>s are equal, by <code>text</code>
     */
    @Override
    public int compareTo(Question o) {
        int weightCompare = Integer.compare(getWeight(), o.getWeight()); // comparing by weight, for TreeSet comparing
        if (weightCompare != 0) return weightCompare;

        return new CompareToBuilder().append(text, o.text).toComparison(); //TODO rethink this (especially in small datasets)
    }
}
