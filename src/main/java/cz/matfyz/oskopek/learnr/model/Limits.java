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

/**
 * A <code>Limits</code> objects that tracks limits for the number of asked questions.
 * <p/>
 * Also keeps counters of the current numbers of questions asked.
 * <p/>
 * Supports these limits:
 * <ul>
 * <li>daily</li>
 * <li>session</li>
 * </ul>
 * <p/>
 * (So the <code>LimitWatcher</code> and <code>QuestionIterator</code> can use the same object instance).
 */
@XStreamAlias("Limits")
public class Limits extends AbstractPersistable {

    // LIMITS
    final private int daily;
    final private int session;

    // COUNTERS
    private int dailyCounter;
    private int sessionCounter;
    private long loggedDay;

    /**
     * Constructs a new <code>Limits</code> object with the default limits set.
     *
     * @param daily   the maximum amount of questions asked daily
     * @param session the maximum amount of questions asked in a single session
     */
    public Limits(int daily, int session) {
        this.daily = daily;
        this.session = session;
    }

    public int getDaily() {
        return daily;
    }

    public int getSession() {
        return session;
    }

    public int getDailyCounter() {
        return dailyCounter;
    }

    public void setDailyCounter(int dailyCounter) {
        this.dailyCounter = dailyCounter;
    }

    public int getSessionCounter() {
        return sessionCounter;
    }

    public void setSessionCounter(int sessionCounter) {
        this.sessionCounter = sessionCounter;
    }

    public long getLoggedDay() {
        return loggedDay;
    }

    public void setLoggedDay(long loggedDay) {
        this.loggedDay = loggedDay;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("daily", daily).append("session", session).append("dailyCounter", dailyCounter)
                .append("sessionCounter", sessionCounter).append("loggedDay", loggedDay).build();
    }
}
