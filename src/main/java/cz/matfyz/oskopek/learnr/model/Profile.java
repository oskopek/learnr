package cz.matfyz.oskopek.learnr.model;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by oskopek on 11/29/14.
 */
@XStreamAlias("Profile")
public class Profile extends AbstractPersistable {

    private String name;

    private List<Dataset> datasetList;

    private Settings settings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dataset> getDatasetList() {
        return datasetList;
    }

    public void setDatasetList(List<Dataset> datasetList) {
        this.datasetList = datasetList;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void addDataset(Dataset dataset) {

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(name).append(datasetList).append(settings).build();
    }
}
