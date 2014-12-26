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

    final private Logger LOGGER = LoggerFactory.getLogger(DatasetPanel.class);

    protected MainPanel parentPane;
    final private static String currentDirectory = "./prog1/zapoctak_learnr/data/"; // TODO fix hardcoding file path

    public DatasetPanel(MainPanel parentPane) {
        this.parentPane = parentPane;
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
                    parentPane.updateUI();
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
                        DatasetIO.exportXMLDataset(parentPane.qaPanel.questionIterator.getDataset(), chooser.getSelectedFile().getAbsolutePath());
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
                    parentPane.updateUI();
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
                        DatasetIO.exportTXTDataset(parentPane.qaPanel.questionIterator.getDataset(), chooser.getSelectedFile().getAbsolutePath());
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
        parentPane.qaPanel.questionIterator = new QuestionIterator(dataset);
        parentPane.qaPanel.nextQuestion();
    }

    @Override
    public String localizedText(String id) {
        return parentPane.localizedText(id);
    }
}
