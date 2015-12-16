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
import queuemanager.SortedArrayPriorityQueue;

/**
 *
 * @author Jamie Simpson
 */
public class AlarmHandler {

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

    public SortedArrayPriorityQueue setAlarms(String alarmTime) throws IOException, ParseException {
        Date date = setDate(alarmTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        dateFormat.format(date);
        LinkedList<Date> events = new LinkedList<>();
        LinkedList<Date> currentEvents = new LinkedList<>();
        Ical ical = new Ical();
        events = ical.getAllEventDates();
        events.add(date);
        Collections.sort(events);
        Date today = new Date();

        dateFormat.format(today);
        for (Date event : events) {
            dateFormat.format(event);
            if (!event.before(today)) {
                currentEvents.add(event);
            }
        }
        //  System.out.println(currentEvents);
        SortedArrayPriorityQueue alarms = new SortedArrayPriorityQueue(currentEvents.size());
        int priority = currentEvents.size();
        for (Date currentEvent : currentEvents) {
            try {
                alarms.add(currentEvent, priority);
            } catch (QueueOverflowException ex) {
                System.out.println("could not add alarm, Queue is full" + ex.getMessage());
            }
            priority--;
        }

        return alarms;
    }

}
