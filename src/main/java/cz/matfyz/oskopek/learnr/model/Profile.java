package cz.matfyz.oskopek.learnr.model;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Profile")
public class Profile extends AbstractPersistable {

    private String name;

}
