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
package cz.matfyz.oskopek.learnr.data;

import cz.matfyz.oskopek.learnr.model.Limits;

import java.util.Calendar;

/**
 * Created by oskopek on 12/25/14.
 */
public class LimitWatcher {

    private Limits limits;

    public LimitWatcher(Limits limits) {
        this.limits = limits;
    }

    private static boolean isSameDay(long loggedDay, long currentDay) {
        Calendar loggedCal = Calendar.getInstance();
        Calendar currentCal = Calendar.getInstance();
        loggedCal.setTimeInMillis(loggedDay);
        currentCal.setTimeInMillis(currentDay);
        return (loggedCal.get(Calendar.YEAR) == currentCal.get(Calendar.YEAR)) &&
                (loggedCal.get(Calendar.MONTH) == currentCal.get(Calendar.MONTH)) &&
                (loggedCal.get(Calendar.DAY_OF_MONTH) == currentCal.get(Calendar.DAY_OF_MONTH));
    }

    public void resetAll() {
        resetSession();
        resetDaily();
        resetLoggedDay();
    }

    public void resetSession() {
        this.limits.setSessionCounter(0);
    }

    public void resetDaily() {
        this.limits.setDailyCounter(0);
    }

    public void resetLoggedDay() {
        this.limits.setLoggedDay(System.currentTimeMillis());
    }

    public boolean isValidAll() {
        return isValidSession() && isValidDaily();
    }

    public void incDaily() {
        limits.setDailyCounter(limits.getDailyCounter() + 1);
    }

    public void incSession() {
        limits.setSessionCounter(limits.getSessionCounter() + 1);
    }

    public void incAll() {
        incDaily();
        incSession();
    }

    public boolean isValidSession() {
        return limits.getSessionCounter() < limits.getSession();
    }

    public boolean isValidDaily() {
        long currentDay = System.currentTimeMillis();
        if (!isSameDay(limits.getLoggedDay(), currentDay)) {
            limits.setLoggedDay(currentDay);
            limits.setDailyCounter(0);
        }
        return limits.getDailyCounter() < limits.getDaily();
    }

    public int getSessionCounter() {
        return limits.getSessionCounter();
    }

    public int getDailyCounter() {
        return limits.getDailyCounter();
    }

}
