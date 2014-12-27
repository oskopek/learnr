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

    private static boolean isSameDay(long loggedDay, long currentDay) {
        Calendar loggedCal = Calendar.getInstance();
        Calendar currentCal = Calendar.getInstance();
        loggedCal.setTimeInMillis(loggedDay);
        currentCal.setTimeInMillis(currentDay);
        return  (loggedCal.get(Calendar.YEAR) == currentCal.get(Calendar.YEAR)) &&
                (loggedCal.get(Calendar.MONTH) == currentCal.get(Calendar.MONTH)) &&
                (loggedCal.get(Calendar.DAY_OF_MONTH) == currentCal.get(Calendar.DAY_OF_MONTH));
    }

    public int getSessionCounter() {
        return limits.getSessionCounter();
    }

    public int getDailyCounter() {
        return limits.getDailyCounter();
    }

}
