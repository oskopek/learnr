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

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Answer")
public class Answer extends AbstractPersistable {

    private String value;
    private long reactionTime;
    private boolean good;

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean isAccepted) {
        this.good = isAccepted;
    }

    public long getReactionTime() {
        return reactionTime;
    }

    public void setReactionTime(long reactionTime) {
        this.reactionTime = reactionTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean checkAnswer(Question question, Dataset.AnswerCheckType type) {
        switch(type) {
            case CASE_INSENSITIVE: {
                String val = value.toLowerCase().trim();
                for (Answer a : question.getAnswerList()) {
                    if (a.getValue().toLowerCase().trim().equals(val)) return true;
                }
                return false;
            }
            case CASE_SENSITIVE: {
                String val = value.trim();
                for (Answer a : question.getAnswerList()) {
                    if (a.getValue().trim().equals(val)) return true;
                }
                return false;
            }
            case EXACT:
            default: {
                String val = value;
                for (Answer a : question.getAnswerList()) {
                    if (a.getValue().equals(val)) return true;
                }
                return false;
            }

        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("value", value).append("reactionTime", reactionTime).append("good", good).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;

        Answer answer = (Answer) o;
        return new EqualsBuilder().append(value, answer.value).append(reactionTime, answer.reactionTime).append(good, answer.good).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).append(reactionTime).append(good).toHashCode();
    }
}
