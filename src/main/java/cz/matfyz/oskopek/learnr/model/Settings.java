package cz.matfyz.oskopek.learnr.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Settings")
public class Settings extends AbstractPersistable {

    private String filename;

}
