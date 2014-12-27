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

    private long lastAsked;
    private List<Answer> answeredList;
    private int goodAnswerCount;

    public Statistics() {
        answeredList = new ArrayList<>();
        goodAnswerCount = 0;
        lastAsked = 0;
    }

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
