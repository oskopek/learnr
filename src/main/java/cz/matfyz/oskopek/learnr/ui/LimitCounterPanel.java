package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.data.LimitWatcher;
import cz.matfyz.oskopek.learnr.tools.Localizable;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/27/14.
 */
public class LimitCounterPanel extends JPanel implements Localizable {

    private QuestionAnswerPanel parentPanel;
    private JLabel sessionCount;
    private JLabel dailyCount;

    public LimitCounterPanel(QuestionAnswerPanel parentPanel) {
        this.parentPanel = parentPanel;
        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        JLabel sessionText = new JLabel(localizedText("session") + ":");
        JLabel dailyText = new JLabel(localizedText("daily") + ":");
        sessionCount = new JLabel("0");
        dailyCount = new JLabel("0");
        add(sessionText);
        add(sessionCount);
        add(dailyText);
        add(dailyCount);
    }

    public void updateLimitCounters(LimitWatcher limitWatcher) {
        sessionCount.setText(limitWatcher.getSessionCounter() + "");
        dailyCount.setText(limitWatcher.getDailyCounter() + "");
        if (limitWatcher.isValidSession()) {
            sessionCount.setForeground(Color.BLACK);
        } else {
            sessionCount.setForeground(Color.RED);
        }
        if (limitWatcher.isValidDaily()) {
            dailyCount.setForeground(Color.BLACK);
        } else {
            dailyCount.setForeground(Color.RED);
        }
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
