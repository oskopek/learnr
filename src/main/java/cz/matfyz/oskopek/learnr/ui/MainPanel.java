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

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class MainPanel extends JPanel implements Localizable {

    protected QuestionAnswerPanel qaPanel;
    protected DatasetPanel dataPanel;
    protected LearnrPanel parentPanel;

    public MainPanel(LearnrPanel parentPanel) {
        this.parentPanel = parentPanel;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        qaPanel = new QuestionAnswerPanel(this);
        dataPanel = new DatasetPanel(this);
        add(dataPanel, BorderLayout.PAGE_START);
        add(qaPanel, BorderLayout.CENTER);
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
