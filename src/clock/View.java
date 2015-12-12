package clock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;

public class View implements Observer, ActionListener, ItemListener {

    ClockPanel panel;
    JMenuBar menuBar;
    JMenu addMenu, saveMenu;
    Container pane;

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
        addMenu = new JMenu("Add Alarm");
        saveMenu = new JMenu("Save Alarms");
        menuBar.add(addMenu);
        menuBar.add(saveMenu);
        addMenu.addItemListener(this);
        saveMenu.addItemListener(this);

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

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == addMenu && e.getStateChange() == 1) {
            Date date = new Date();
            SpinnerDateModel sdm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
            JSpinner spinner = new JSpinner(sdm);
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner, "hh:mm");
            spinner.setEditor(dateEditor);
            int option = JOptionPane.showOptionDialog(pane, spinner, "Please select a time for the alarm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (option == JOptionPane.OK_OPTION) {

            } else {

            }
        } else if (e.getSource() == saveMenu) {
            //TO DO CODE FOR SAVING
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
