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
