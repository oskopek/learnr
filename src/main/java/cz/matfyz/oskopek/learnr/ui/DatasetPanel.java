package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.tools.DatasetIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by oskopek on 12/3/14.
 */
public class DatasetPanel extends JPanel {

    final private Logger LOGGER = LoggerFactory.getLogger(DatasetPanel.class);

    public DatasetPanel() {
        init();
    }

    private void init() {
        setLayout(new GridLayout(1, 4));
        JButton openBtt = new JButton("Open");
        openBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Open dataset: {}", chooser.getSelectedFile());
                }
            }
        });
        JButton saveBtt = new JButton("Save");
        saveBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Save dataset to: {}", chooser.getSelectedFile());
                }
            }
        });
        JButton importBtt = new JButton("Import");
        importBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", "json");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Import dataset: {}", chooser.getSelectedFile());
                    //TODO Implement: DatasetIO.importJSONDataset(chooser.getSelectedFile().toString());
                }
            }
        });
        JButton exportBtt = new JButton("Export");
        exportBtt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", "json");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    LOGGER.debug("Export dataset to: {}", chooser.getSelectedFile());
                }
            }
        });
        add(openBtt);
        add(saveBtt);
        add(importBtt);
        add(exportBtt);
    }

}
