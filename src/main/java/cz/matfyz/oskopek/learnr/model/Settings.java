package cz.matfyz.oskopek.learnr.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Settings")
public class Settings extends AbstractPersistable {

    private Limits defaultLimits;

    private String filename;

    private String languageCode;

    public Limits getDefaultLimits() {
        return defaultLimits;
    }

    public void setDefaultLimits(Limits defaultLimits) {
        this.defaultLimits = defaultLimits;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(defaultLimits).append(filename).append(languageCode).build();
    }
}
