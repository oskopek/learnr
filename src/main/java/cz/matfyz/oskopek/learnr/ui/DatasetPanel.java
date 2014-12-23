package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.data.QuestionIterator;
import cz.matfyz.oskopek.learnr.model.Dataset;
import cz.matfyz.oskopek.learnr.tools.DatasetIO;
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
public class DatasetPanel extends JPanel {

    final private Logger LOGGER = LoggerFactory.getLogger(DatasetPanel.class);

    protected MainPanel parentPane;
    final private static String currentDirectory = "./prog1/zapoctak_learnr/data/";

    public DatasetPanel(MainPanel parentPane) {
        this.parentPane = parentPane;
        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        JButton openBtt = new JButton("Open");
        openBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser(currentDirectory);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Open dataset: {}", chooser.getSelectedFile());
                    try {
                        setDatasetToPanel(DatasetIO.openDataset(chooser.getSelectedFile().getAbsolutePath()));
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });
        JButton saveBtt = new JButton("Save");
        saveBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser(currentDirectory);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Save dataset to: {}", chooser.getSelectedFile());
                    try {
                        DatasetIO.saveDataset(parentPane.qaPanel.questionIterator.getDataset(), chooser.getSelectedFile().getAbsolutePath());
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });
        JButton importJSONBtt = new JButton("Import JSON");
        importJSONBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser(currentDirectory);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", "json");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Import dataset: {}", chooser.getSelectedFile());
                    try {
                        setDatasetToPanel(DatasetIO.importJSONDataset(chooser.getSelectedFile().getAbsolutePath()));
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });
        JButton exportJSONBtt = new JButton("Export JSON");
        exportJSONBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser(currentDirectory);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", "json");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Export dataset to: {}", chooser.getSelectedFile());
                    try {
                        DatasetIO.exportJSONDataset(parentPane.qaPanel.questionIterator.getDataset(), chooser.getSelectedFile().getAbsolutePath());
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });
        JButton importTXTBtt = new JButton("Import TXT");
        importTXTBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser(currentDirectory);
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
                }
            }
        });
        JButton exportTXTBtt = new JButton("Export TXT");
        exportTXTBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser(currentDirectory);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Export dataset to: {}", chooser.getSelectedFile());
                    try {
                        DatasetIO.exportTXTDataset(parentPane.qaPanel.questionIterator.getDataset(), chooser.getSelectedFile().getAbsolutePath());
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });
        add(openBtt);
        add(saveBtt);
        add(importTXTBtt);
        add(exportTXTBtt);
        add(importJSONBtt);
        add(exportJSONBtt);
    }

    private void setDatasetToPanel(Dataset dataset) {
        parentPane.qaPanel.questionIterator = new QuestionIterator(dataset);
    }

}
