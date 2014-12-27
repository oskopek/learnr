package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.tools.Localizable;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class LearnrPanel extends JPanel implements Localizable {

    protected MainPanel mainPanel;
    protected MenuPanel menuPanel;

    private ResourceBundle resourceBundle;

    public LearnrPanel() {
        init();
    }

    final private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LearnrPanel.class);

    private void init() {
        languageChange(Locale.forLanguageTag("en-US")); // default en-US
        setLayout(new BorderLayout());
        mainPanel = new MainPanel(this);
        menuPanel = new MenuPanel(this);
        add(menuPanel, BorderLayout.LINE_START);
        add(mainPanel, BorderLayout.CENTER);
    }

    public void languageChange(Locale locale) {
        try {
            resourceBundle = new PropertyResourceBundle(getClass().getResourceAsStream("/strings/messages." + locale.toLanguageTag() + ".properties"));
        } catch (IOException e) {
            LOGGER.error("No such resourceBundle found: \'{}\'", locale.toLanguageTag());
            e.printStackTrace();
        }
        LOGGER.debug("Loaded resourceBundle: \'{}\'", locale.toLanguageTag()); //TODO redraw!
        setLocale(locale); //necessary?
    }

    public HashMap<String, String> getAvailableLanguages() {
        /*
        //Old version, used while loading only from file system (wouldn't work in exec-able jar)

        File langDir = new File("./prog1/zapoctak_learnr/data/strings/");
        java.util.List<String> availableLanguages = new ArrayList<>();
        for (String filename : langDir.list()) {
            LOGGER.info("Inspecting file \'{}\' for language resources.", filename);
            if (filename.split("\\.").length != 3) continue;
            String langCode = filename.split("\\.")[1];
            LOGGER.info("Found language \'{}\'.", langCode);
            availableLanguages.add(langCode);
        }
        String[] availableLangArr = new String[availableLanguages.size()];
        availableLanguages.toArray(availableLangArr);
        return availableLangArr;
        */

        HashMap<String, String> availableLanguages = new HashMap<>();
        availableLanguages.put(localizedText("english"), "en-US");
        availableLanguages.put(localizedText("slovak"), "sk-SK");
        availableLanguages.put(localizedText("german"), "de-DE");
        availableLanguages.put(localizedText("czech"), "cs-CS");
        //Add new languages here
        return availableLanguages;
    }

    @Override
    public String localizedText(String id) {
        return resourceBundle.getString(id);
    }

}
