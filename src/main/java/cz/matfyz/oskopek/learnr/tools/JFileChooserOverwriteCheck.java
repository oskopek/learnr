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
