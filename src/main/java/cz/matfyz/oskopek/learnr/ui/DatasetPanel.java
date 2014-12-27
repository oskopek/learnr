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

import cz.matfyz.oskopek.learnr.data.QuestionIterator;
import cz.matfyz.oskopek.learnr.model.Dataset;
import cz.matfyz.oskopek.learnr.tools.DatasetIO;
import cz.matfyz.oskopek.learnr.tools.JFileChooserOverwriteCheck;
import cz.matfyz.oskopek.learnr.tools.Localizable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by oskopek on 12/3/14.
 */
public class DatasetPanel extends JPanel implements Localizable {

    final private static String currentDirectory = "."; //"./prog1/zapoctak_learnr/data/";
    final private Logger LOGGER = LoggerFactory.getLogger(DatasetPanel.class);
    protected MainPanel parentPanel;

    public DatasetPanel(MainPanel parentPanel) {
        this.parentPanel = parentPanel;
        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        JButton loadBtt = new JButton(localizedText("load"));
        loadBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooserOverwriteCheck(currentDirectory);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Import dataset: {}", chooser.getSelectedFile());
                    try {
                        setDatasetToPanel(DatasetIO.importXMLDataset(chooser.getSelectedFile().getAbsolutePath()));
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    parentPanel.updateUI();
                }
            }
        });
        JButton saveBtt = new JButton(localizedText("save"));
        saveBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooserOverwriteCheck(currentDirectory);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Export dataset to: {}", chooser.getSelectedFile());
                    try {
                        DatasetIO.exportXMLDataset(parentPanel.qaPanel.questionIterator.getDataset(), chooser.getSelectedFile().getAbsolutePath());
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });
        JButton importBtt = new JButton(localizedText("import"));
        importBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooserOverwriteCheck(currentDirectory);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Import dataset: {}", chooser.getSelectedFile());
                    try {
                        setDatasetToPanel(DatasetIO.importTXTDataset(chooser.getSelectedFile().getAbsolutePath()));
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    parentPanel.updateUI();
                }
            }
        });
        JButton exportBtt = new JButton(localizedText("export"));
        exportBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooserOverwriteCheck(currentDirectory);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Export dataset to: {}", chooser.getSelectedFile());
                    try {
                        DatasetIO.exportTXTDataset(parentPanel.qaPanel.questionIterator.getDataset(), chooser.getSelectedFile().getAbsolutePath());
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });
        add(importBtt);
        add(exportBtt);
        add(loadBtt);
        add(saveBtt);
    }

    private void setDatasetToPanel(Dataset dataset) {
        parentPanel.qaPanel.questionIterator = new QuestionIterator(dataset);
        parentPanel.qaPanel.questionIterator.getLimitWatcher().resetSession();
        parentPanel.qaPanel.nextQuestion();
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
