package cz.matfyz.oskopek.learnr.tools;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * Created by oskopek on 12/24/14.
 */
public class JFileChooserOverwriteCheck extends JFileChooser {

    public JFileChooserOverwriteCheck() {
        super();
    }

    public JFileChooserOverwriteCheck(String currentDirectoryPath) {
        super(currentDirectoryPath);
    }

    public JFileChooserOverwriteCheck(File currentDirectory) {
        super(currentDirectory);
    }

    public JFileChooserOverwriteCheck(FileSystemView fsv) {
        super(fsv);
    }

    public JFileChooserOverwriteCheck(File currentDirectory, FileSystemView fsv) {
        super(currentDirectory, fsv);
    }

    public JFileChooserOverwriteCheck(String currentDirectoryPath, FileSystemView fsv) {
        super(currentDirectoryPath, fsv);
    }

    @Override
    public void approveSelection() {
        File f = getSelectedFile();
        if(f.exists() && getDialogType() == SAVE_DIALOG) {
            int result = JOptionPane.showConfirmDialog(this, "File already exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                super.approveSelection();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                cancelSelection();
            }
        } else {
            super.approveSelection();
        }
    }

}
