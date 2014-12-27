package cz.matfyz.oskopek.learnr.ui;

import cz.matfyz.oskopek.learnr.model.Limits;
import cz.matfyz.oskopek.learnr.tools.Localizable;

import javax.swing.*;
import java.awt.*;

/**
 * Created by oskopek on 12/27/14.
 */
public class LimitsDialog implements Localizable {

    private LearnrPanel parentPanel;

    public LimitsDialog(LearnrPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    public Limits showDialog(Limits current) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        SpinnerNumberModel session = new SpinnerNumberModel();
        session.setMinimum(1);
        session.setValue(current.getSession());
        SpinnerNumberModel daily = new SpinnerNumberModel();
        daily.setMinimum(1);
        daily.setValue(current.getDaily());

        inputPanel.add(new JLabel(localizedText("session") + ":"));
        inputPanel.add(new JSpinner(session));
        inputPanel.add(new JLabel(localizedText("daily") + ":"));
        inputPanel.add(new JSpinner(daily));

        int result = JOptionPane.showConfirmDialog(parentPanel, inputPanel, "", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Limits limits = new Limits();
            limits.setDaily(daily.getNumber().intValue());
            limits.setSession(session.getNumber().intValue());
            return limits;
        } else {
            return null;
        }
    }

    @Override
    public String localizedText(String id) {
        return parentPanel.localizedText(id);
    }
}
