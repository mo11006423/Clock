package clock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import queuemanager.QueueUnderflowException;

public class View implements Observer, ActionListener {

    ClockPanel panel;
    JMenuBar menuBar;
    JMenu alarmOptions;
    JMenuItem addAlarm, saveAlarm;
    Container pane;
    AlarmHandler ah = new AlarmHandler();
    JButton btnEdit, btnView;
    JOptionPane testPane = new JOptionPane();
    JFrame testFrame = new JFrame();

    public View(Model model) {
        JFrame frame = new JFrame();
        panel = new ClockPanel(model);
        //frame.setContentPane(panel);
        frame.setTitle("Java Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Start of border layout code
        // I've just put a single button in each of the border positions:
        // PAGE_START (i.e. top), PAGE_END (bottom), LINE_START (left) and
        // LINE_END (right). You can omit any of these, or replace the button
        // with something else like a label or a menu bar. Or maybe you can
        // figure out how to pack more than one thing into one of those
        // positions. This is the very simplest border layout possible, just
        // to help you get started.
        pane = frame.getContentPane();
        JButton button;

        menuBar = new JMenuBar();
        alarmOptions = new JMenu("Alarm Options");
        addAlarm = new JMenuItem("Add new Alarm");
        saveAlarm = new JMenuItem("Save Current Alarms");
        menuBar.add(alarmOptions);
        alarmOptions.add(addAlarm);
        alarmOptions.add(saveAlarm);
        addAlarm.addActionListener(this);
        saveAlarm.addActionListener(this);

        pane.add(menuBar, BorderLayout.PAGE_START);

        panel.setPreferredSize(new Dimension(300, 300));
        pane.add(panel, BorderLayout.CENTER);

        // pane.add(button, BorderLayout.LINE_START);
        // pane.add(button, BorderLayout.PAGE_END);
        JPanel rightPanel = new JPanel();

        pane.add(rightPanel, BorderLayout.LINE_END);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        btnView = new JButton("View Alarms");
        btnView.addActionListener(this);
        rightPanel.add(btnView);
        Dimension D = btnView.getMaximumSize();
        btnEdit = new JButton("Edit Alarms");
        btnEdit.addActionListener(this);
        btnEdit.setMaximumSize(D);
        rightPanel.add(btnEdit);
     //   menuBar.add(new JLabel("Next Alarm: " + ah.getAlarms().head().toString()));

        // End of borderlayout code
        frame.pack();
        frame.setVisible(true);
    }

    public void update(Observable o, Object arg) {
        panel.repaint();
        if (ah.alarmTime()) {
            JOptionPane.showMessageDialog(panel, "Your alarm for " + ah.getNextAlarm() + " is going off");
            try {
                ah.getAlarms().remove();
            } catch (QueueUnderflowException ex) {

            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addAlarm) {
            Date date = new Date();
            SpinnerDateModel sdm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
            JSpinner spinner = new JSpinner(sdm);
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "HH:mm");
            spinner.setEditor(dateEditor);
            int option = JOptionPane.showOptionDialog(pane, spinner, "Please select a time for the alarm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.OK_OPTION) {
                String alarmTime = dateEditor.getFormat().format(spinner.getValue());

            } else if (e.getSource() == saveAlarm) {

            } else if (e.getSource() == btnEdit) {

            } else if (e.getSource() == btnView) {

            }
        }
    }
}
