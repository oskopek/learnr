/*
 * #%L
 * Learnr
 * %%
 * Copyright (C) 2014 Ondrej Skopek
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.tools.Localizable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * The base panel (inside of <code>LearnrFrame</code>). Also manages localization (bottom-up).
 */
public class LearnrPanel extends JPanel implements Localizable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LearnrPanel.class);
    final MainPanel mainPanel;
    final MenuPanel menuPanel;
    private ResourceBundle resourceBundle;

    public LearnrPanel() {
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
