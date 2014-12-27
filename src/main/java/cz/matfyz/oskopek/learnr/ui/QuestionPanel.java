package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.model.Question;
import cz.matfyz.oskopek.learnr.tools.Localizable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class QuestionPanel extends JPanel implements Localizable {

    final private static Logger LOGGER = LoggerFactory.getLogger(QuestionPanel.class);

    protected QuestionAnswerPanel parentPanel;
    private JLabel text;

    public QuestionPanel(QuestionAnswerPanel parentPanel) {
        this.parentPanel = parentPanel;
        init();
        setNullQuestion(false);
    }

    private void init() {
        setLayout(new BorderLayout());
        text = new JLabel();
        text.setHorizontalAlignment(JLabel.CENTER);
        add(text, BorderLayout.CENTER);
    }

    public void showQuestion(Question question) {
        if (question != null) {
            LOGGER.debug("Loaded question \'{}\'.", question.getText());
            text.setText(question.getText());
        } else {
            setNullQuestion(true);
        }
    }

    private void setNullQuestion(boolean isEnd) {
        LOGGER.debug("Loaded null question. At end? {}.", isEnd);
        text.setText("");
        if (isEnd) {
            JOptionPane.showMessageDialog(this, localizedText("finished-questions"));
        }
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
