package cz.matfyz.oskopek.learnr.model;

import java.io.Serializable;

/**
 * Created by oskopek on 11/29/14.
 */
public abstract class AbstractPersistable implements Serializable/*, Comparable<AbstractPersistable>*/ {

    protected Long id;

    protected AbstractPersistable(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
