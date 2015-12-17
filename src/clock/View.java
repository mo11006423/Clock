package clock;

import Jical.components.Ical;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    Ical ical = new Ical();
    JLabel nextAlarm = new JLabel();
    JFrame frame = new JFrame();
    boolean runOnce = false;

    public View(Model model) {

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

        // pane.add(button, BorderLayout.LINE_START);
        // End of borderlayout code
        frame.pack();
        frame.setVisible(true);

    }

    public void update(Observable o, Object arg) {
        panel.repaint();
        //checks to see if the alarm is due to go off based on head of the queue then deletes it from the queue and the ical file if stored
        if (ah.alarmTime()) {
            JOptionPane.showMessageDialog(panel, "Your alarm for " + ah.getNextAlarm() + " is going off");
            try {
                ical.deleteEvent(ah.getNextAlarm());
                ah.getAlarms().remove();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(panel, "Cannot delete the alarm");
            } catch (QueueUnderflowException ex) {
                JOptionPane.showMessageDialog(panel, "No alarm to delete");
            }
        }
        //This checks if the add has been clicked in order to display the next alarm time on screen It prevents updating every tingle second
        if (runOnce == false) {
            if (!ah.getAlarms().isEmpty()) {
                nextAlarm = new JLabel("Next alarm: " + ah.getNextAlarm());
                pane.remove(nextAlarm);
                pane.add(nextAlarm, BorderLayout.PAGE_END);
                frame.pack();
                runOnce = true;

            } else {
                //code for the next alarm when not set
                nextAlarm = new JLabel("No alarm has been set");
                pane.remove(nextAlarm);
                pane.add(nextAlarm, BorderLayout.PAGE_END);
                frame.pack();
                runOnce = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addAlarm) {
            Date date = new Date();
            //Create spinner instance for easier user interaction
            SpinnerDateModel sdm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
            JSpinner spinner = new JSpinner(sdm);
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "HH:mm");
            spinner.setEditor(dateEditor);
            int option = JOptionPane.showOptionDialog(pane, spinner, "Please select a time for the alarm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.OK_OPTION) {
                String alarmTime = dateEditor.getFormat().format(spinner.getValue());
                ah.setAlarms(alarmTime);
            }
            runOnce = false;

        } else if (e.getSource() == saveAlarm) {
            try {
                ah.saveAlarms();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(panel, "IO exception: could not write to file?");
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(panel, "Could not parse date");
            }
            JOptionPane.showMessageDialog(panel, "Your alarms have been saved to MyCalendar.ics");

        } else if (e.getSource() == btnEdit) {
            //Show user a message telling them alarms have to be saved before being modified (reason being it 
            //made coding much easier to edit from file than it did from this instance of the priority queue
            int confirm = JOptionPane.showConfirmDialog(panel, "Warning!\n By poceeding your current alarms and settings\n will need be saved\n Press OK to continue or cancel to abort ", "", JOptionPane.OK_CANCEL_OPTION);
            //If user selects ok and alarms actually ecist populate the options and display them to the user
            if (confirm == JOptionPane.OK_OPTION) {
                if (!ah.getAlarms().isEmpty()) {
                    Object[] options = new Object[ah.getAlarms().toList().size()];
                    for (int i = 0; i < ah.getAlarms().toList().size(); i++) {
                        options[i] = ah.getAlarms().toList().get(i);
                    }
                    //set a date equal to the selected date (if its null nothing clicked)
                    Date date = (Date) JOptionPane.showInputDialog(panel, "Select an alarm to Edit\n", "Edit Alarms", JOptionPane.PLAIN_MESSAGE, null, options, ah.getNextAlarm());
                    if (date != null) {
                        SpinnerDateModel sdm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
                        JSpinner spinner = new JSpinner(sdm);
                        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "HH:mm");
                        spinner.setEditor(dateEditor);
                        int option = JOptionPane.showOptionDialog(pane, spinner, "Please select a time for the alarm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                        //This should all not really go here in this file but time is ticking till deadlin, no pun intended
                        if (option == JOptionPane.OK_OPTION) {
                            try {
                                //save the alamrs check for errors
                                ah.saveAlarms();
                            } catch (IOException ex) {
                                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ParseException ex) {
                                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //delete old event/alarm
                            try {
                                ical.deleteEvent(date);
                            } catch (IOException ex) {
                                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //set new alarm time and below convert to date and add event
                            String alarmTime = dateEditor.getFormat().format(spinner.getValue());

                            try {
                                ical.addEvent(ah.setDate(alarmTime));
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //this is needed so the new file is read in and not keeping the old ical file and pq values, basically it's a refresh
                            ah = new AlarmHandler();
                            //set to false so next alarm updates, resizing may be required if previous labels not removed
                            runOnce = false;

                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(panel, "There are no alarms to edit");
            }

        } else if (e.getSource() == btnView) {
            //Gets all the alarms as a string and displays them
            String alarms = "";
            if (!ah.getAlarms().isEmpty()) {
                for (int i = 0; i < ah.getAlarms().toList().size(); i++) {
                    alarms = alarms + "\n" + ah.getAlarms().toList().get(i);
                }
                JOptionPane.showMessageDialog(panel, alarms);
            } else {
                JOptionPane.showMessageDialog(panel, "There are no alarms to show");
            }
        }
    }
}
