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
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Limits")
public class Limits extends AbstractPersistable {

    private int daily;
    private int session;

    private int dailyCounter;
    private int sessionCounter;
    private long loggedDay;

    public int getDaily() {
        return daily;
    }

    public void setDaily(int daily) {
        this.daily = daily;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
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
