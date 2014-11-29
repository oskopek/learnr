package cz.matfyz.oskopek.learnr.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Statistics")
public class Statistics extends AbstractPersistable {

    public Statistics(Long id, long lastAsked) {
        super(id);
        this.lastAsked = lastAsked;
    }

    private long lastAsked;
    private List<Answer> answeredList;
}
