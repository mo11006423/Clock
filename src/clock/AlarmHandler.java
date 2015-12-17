/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clock;

import Jical.components.Ical;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import queuemanager.QueueOverflowException;
import queuemanager.QueueUnderflowException;
import queuemanager.SortedArrayPriorityQueue;

/**
 *
 * @author Jamie Simpson
 */
public class AlarmHandler {

    Ical ical = new Ical();
    private SortedArrayPriorityQueue alarms;

    //The constructor reads alarms from the file so the user does not have to
    public AlarmHandler() {
        this.alarms = new SortedArrayPriorityQueue(1000);
        try {
            int priority = ical.getCountEvents();
            for (int i = 0; i < ical.getCountEvents(); i++) {
                this.alarms.add(ical.getAllEventDates().get(i), priority);
            }
        } catch (IOException ex) {
            System.out.println("An ioexception");
        } catch (QueueOverflowException ex) {
            System.out.println("A Queue overflow exceptions");
        } catch (ParseException ex) {
            System.out.println("A parse Exception");
        }
    }

    /**
     * Takes a time in the format 12:34 and converts it to a date. Basically the
     * characters at the 0, 1 indicies are the hour and the 3 and 4 are always
     * te minutes an hour minute calander file can be created based on these
     * values (converted to integers) and then a date created from this
     * calender.
     *
     * @param alarmTime
     * @return
     */
    public Date setDate(String alarmTime) {
        String hour = alarmTime.charAt(0) + "" + alarmTime.charAt(1);
        String minutes = alarmTime.charAt(3) + "" + alarmTime.charAt(4);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minutes));
        calendar.set(Calendar.SECOND, 00);
        Date start = calendar.getTime();

        return start;

    }

    /**
     * This method takes the alarm time, converts it to the date format and also
     * compares a list of all the stored alarms with the current date/time. This
     * is so any expired alarms are removed and the queue never hits deadlock
     *
     * @param alarmTime
     */
    public void setAlarms(String alarmTime) {
        Date date = setDate(alarmTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        dateFormat.format(date);
        LinkedList<Date> events = alarms.toList();
        LinkedList<Date> currentEvents = new LinkedList<>();
        events.add(date);
        //Sorts the  dates in order
        Collections.sort(events);
        Date today = new Date();
        dateFormat.format(today);
        //add only events that are after "the now"
        for (Date event : events) {
            //dateFormat.format(event);
            if (!event.before(today)) {
                currentEvents.add(event);
            } else {
                try {
                    //delete any expired events
                    ical.deleteEvent(event);
                } catch (IOException ex) {
                    System.out.println("The old event/alarm could not be deleted");
                }
            }
        }
        //  System.out.println(currentEvents);
        alarms = new SortedArrayPriorityQueue(currentEvents.size());
        int priority = currentEvents.size();
        for (Date currentEvent : currentEvents) {
            try {
                alarms.add(currentEvent, priority);
            } catch (QueueOverflowException ex) {
                System.out.println("could not add alarm, Queue is full" + ex.getMessage());
            }
            priority--;
        }
    }

    /**
     * saves the alarm to file. Firstly all dates are deleted from the file as
     * some may have expired, this is done by a for loop and calling the ical
     * deleteEvent method. the addEvent method is then called for every event in
     * the current priority queue
     *
     * @throws IOException
     * @throws ParseException
     */
    public void saveAlarms() throws IOException, ParseException {
        for (int i = 0; i < ical.getCountEvents(); i++) {
            for (Date date : ical.getAllEventDates()) {
                ical.deleteEvent(date);
            }
        }
        for (int i = 0; i < alarms.size(); i++) {
            ical.addEvent((Date) alarms.toList().get(i));
        }

    }

    /**
     * Returns the date value of the alarm stored in the priority queue
     *
     * @return
     */
    public Date getNextAlarm() {
        try {
            return (Date) alarms.head();
        } catch (QueueUnderflowException ex) {
            Logger.getLogger(AlarmHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * returns the alarms priority queue
     *
     * @return
     */
    public SortedArrayPriorityQueue getAlarms() {
        return alarms;
    }

    /**
     * Compares the current date to the next alarm time, checking for an
     * underflow and returns isTime (true) if the hour and minutes match. if it
     * is false that means the alarm is still pending
     *
     * @return
     */
    public boolean alarmTime() {
        Date today = new Date();
        Date nextAlarm = null;
        boolean isTime = false;
        if (!alarms.isEmpty()) {
            try {
                nextAlarm = (Date) alarms.head();
            } catch (QueueUnderflowException ex) {

            }
            DateFormat df = new SimpleDateFormat("HH:mm");
            String todaysTime = df.format(today);
            String alarmTime = df.format(nextAlarm);
            isTime = todaysTime.equals(alarmTime);

        }
        return isTime;
    }

}
