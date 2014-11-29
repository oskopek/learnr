package cz.matfyz.oskopek.learnr.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Answer")
public class Answer extends AbstractPersistable {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean checkAnswer() {
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("value", value).build();
    }
}
