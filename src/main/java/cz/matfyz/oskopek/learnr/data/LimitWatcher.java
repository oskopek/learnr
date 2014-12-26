package cz.matfyz.oskopek.learnr.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import cz.matfyz.oskopek.learnr.model.Limits;

/**
 * Created by oskopek on 12/25/14.
 */
@XStreamAlias("LimitWatcher")
public class LimitWatcher {

    private Limits limits;

    private int sessionCounter;
    private int dailyCounter; //TODO add day checking

    public LimitWatcher(Limits limits) {
        this.limits = limits;
        this.sessionCounter = 0;
        this.dailyCounter = 0;
    }

    public boolean isValidAll() {
        return isValidSession() && isValidDaily();
    }

    public void incDaily() {
        dailyCounter++;
    }

    public void incSession() {
        sessionCounter++;
    }

    public void incAll() {
        incDaily();
        incSession();
    }

    public boolean isValidSession() {
        return sessionCounter < limits.getSession();
    }

    public boolean isValidDaily() {
        return dailyCounter < limits.getDaily();
    }


}
