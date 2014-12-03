package cz.matfyz.oskopek.learnr.app;

import com.thoughtworks.xstream.XStream;
import cz.matfyz.oskopek.learnr.model.Profile;
import cz.matfyz.oskopek.learnr.ui.LearnrUI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by oskopek on 11/29/14.
 */
public class LearnrApp {

    private List<Profile> profileList;

    public void saveProfile(Profile profile) throws IOException {
        File outFile = new File(profile.getSettings().getFilename());
        if(!outFile.createNewFile()) {
            // TODO Are you sure you want to overwrite?
        }
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);

        XStream xstream = new XStream();
        xstream.toXML(profile, fileOutputStream);
        fileOutputStream.close();
    }

    public void saveAllProfiles() throws IOException {
        for (Profile p : profileList) {
            saveProfile(p);
        }
    }

    public void loadProfile(String filename) {
        XStream xstream = new XStream();
        Profile profile = (Profile) xstream.fromXML(new File(filename));
    }

    public static void main(String[] args) {
        LearnrUI learnrUI = new LearnrUI();
        learnrUI.startUI();
    }

}
