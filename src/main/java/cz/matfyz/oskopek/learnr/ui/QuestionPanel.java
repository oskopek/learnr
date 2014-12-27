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
        showEmptyQuestion(false);
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
            showEmptyQuestion(true);
        }
    }

    private void showEmptyQuestion(boolean showDialog) {
        LOGGER.debug("Loaded null question. Show dialog? {}.", showDialog);
        text.setText("");
        if (showDialog) {
            // Dialog cause guessing:
            if (parentPanel.questionIterator.questionsLeft() == 0) {
                JOptionPane.showMessageDialog(this, localizedText("finished-questions"));
            }
            else if (!parentPanel.questionIterator.getLimitWatcher().isValidSession()) {
                JOptionPane.showMessageDialog(this, localizedText("reached-session-lim"));
            }
            else if (!parentPanel.questionIterator.getLimitWatcher().isValidDaily()) {
                JOptionPane.showMessageDialog(this, localizedText("reached-daily-lim"));
            }
            else {
                LOGGER.error("Unknown end-of-dataset status!");
                JOptionPane.showMessageDialog(this, localizedText("unknown-error"), localizedText("error"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
