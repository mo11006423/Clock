package clock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import queuemanager.QueueOverflowException;
import queuemanager.QueueUnderflowException;

public class View implements Observer, ActionListener {

    ClockPanel panel;
    JMenuBar menuBar;
    JMenu alarmOptions;
    JMenuItem addAlarm, saveAlarm;
    Container pane;
    AlarmHandler ah = new AlarmHandler();

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

        panel.setPreferredSize(new Dimension(200, 200));
        pane.add(panel, BorderLayout.CENTER);

        button = new JButton("Button 3 (LINE_START)");
        pane.add(button, BorderLayout.LINE_START);

        button = new JButton("Long-Named Button 4 (PAGE_END)");
        pane.add(button, BorderLayout.PAGE_END);

        button = new JButton("5 (LINE_END)");
        pane.add(button, BorderLayout.LINE_END);

        // End of borderlayout code
        frame.pack();
        frame.setVisible(true);
    }

    public void update(Observable o, Object arg) {
        panel.repaint();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(calendar.getTime());

        try {
            if (currentTime.compareTo(ah.getAlarms().head().toString()) == 1 || currentTime.compareTo(ah.getAlarms().head().toString()) == 0) {
                System.out.println("The head:" + ah.getAlarms().head());
                JOptionPane.showMessageDialog(panel, "Your alarm is going off");

            }
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (QueueOverflowException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        } catch (QueueUnderflowException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AlarmHandler alarmHandler = new AlarmHandler();
        if (e.getSource() == addAlarm) {
            Date date = new Date();
            SpinnerDateModel sdm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
            JSpinner spinner = new JSpinner(sdm);
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "HH:mm");
            spinner.setEditor(dateEditor);
            int option = JOptionPane.showOptionDialog(pane, spinner, "Please select a time for the alarm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.OK_OPTION) {
                String alarmTime = dateEditor.getFormat().format(spinner.getValue());
                try {
                    alarmHandler.saveAlarm(alarmTime);
                } catch (IOException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                } catch (QueueOverflowException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {

            }
        } else if (e.getSource() == saveAlarm) {
            try {
                alarmHandler.getAlarms();
            } catch (IOException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            } catch (QueueOverflowException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
