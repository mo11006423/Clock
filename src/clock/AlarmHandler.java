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

    public void setAlarms(String alarmTime) throws IOException, ParseException {
        Date date = setDate(alarmTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        dateFormat.format(date);
        LinkedList<Date> events = new LinkedList<>();
        LinkedList<Date> currentEvents = new LinkedList<>();
        events = ical.getAllEventDates();
        events.add(date);

        Collections.sort(events);
        Date today = new Date();

        dateFormat.format(today);
        for (Date event : events) {
            //dateFormat.format(event);
            if (!event.before(today)) {
                currentEvents.add(event);
            } else {
                ical.deleteEvent(event);
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

    public void saveAlarms() throws IOException, ParseException {
        for (int i = 0; i < alarms.size(); i++) {
            ical.addEvent((Date) alarms.toList().get(i));
        }

    }

    public Date getNextAlarm() {
        try {
            return (Date) alarms.head();
        } catch (QueueUnderflowException ex) {
            Logger.getLogger(AlarmHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public SortedArrayPriorityQueue getAlarms() {
        return alarms;
    }

    public boolean alarmTime() {
        Date today = new Date();
        Date nextAlarm = null;
        boolean isTime = false;
        if (!alarms.isEmpty()) {
            try {
                nextAlarm = (Date) alarms.head();
                System.out.println(today + "\n" + nextAlarm);
            } catch (QueueUnderflowException ex) {

            }
            if (today == nextAlarm) {
                isTime = true;
            } else if (alarms.isEmpty()) {
                isTime = false;
            }
        }
        return isTime;
    }

}
