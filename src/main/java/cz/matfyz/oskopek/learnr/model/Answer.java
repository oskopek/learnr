package cz.matfyz.oskopek.learnr.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Answer")
public class Answer extends AbstractPersistable {

    private String value;

    public Answer(Long id, String value) {
        super(id);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return new EqualsBuilder().append(getValue(), answer.getValue()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("value", value).build();
    }
}
