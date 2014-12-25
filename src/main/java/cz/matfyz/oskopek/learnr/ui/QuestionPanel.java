package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/3/14.
 */
public class QuestionPanel extends JPanel {

    final private static Logger LOGGER = LoggerFactory.getLogger(QuestionPanel.class);

    protected QuestionAnswerPanel parentPane;
    private JLabel text;

    public QuestionPanel(QuestionAnswerPanel parentPane) {
        this.parentPane = parentPane;
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
            JOptionPane.showMessageDialog(this, "You have finished all the questions!");
        }
    }

}
