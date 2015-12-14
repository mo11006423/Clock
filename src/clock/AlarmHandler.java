/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clock;

import biweekly.Biweekly;
import biweekly.ICalVersion;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.DateStart;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.commons.lang3.ArrayUtils;
import queuemanager.QueueOverflowException;
import queuemanager.SortedArrayPriorityQueue;

/**
 *
 * @author Jamie Simpson
 */
public class AlarmHandler {

    private File file = new File("C:\\Users\\Jamie Simpson\\Desktop\\Alarms.ics");

    public void saveAlarm(String alarmTime) throws IOException, ParseException {

        String hour = alarmTime.charAt(0) + "" + alarmTime.charAt(1);
        String minutes = alarmTime.charAt(3) + "" + alarmTime.charAt(4);

        if (!file.exists()) {
            ICalendar ical = new ICalendar();

            VEvent event = new VEvent();

            ical.setVersion(ICalVersion.V2_0);
            ical.setProductId("-//Jamie Simpson//Alarm Clock//EN");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, Integer.parseInt(minutes));
            calendar.set(Calendar.SECOND, 00);
            Date start = calendar.getTime();

            event.setDateStart(new DateStart(start, true));
            ical.addEvent(event);
            Biweekly.write(ical).go(file);
        } else {
            VEvent event = new VEvent();
            ICalendar ical = Biweekly.parse(file).first();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, Integer.parseInt(minutes));
            calendar.set(Calendar.SECOND, 00);
            Date start = calendar.getTime();
            event.setDateStart(new DateStart(start, true));
            ical.addEvent(event);
            Biweekly.write(ical).go(file);

        }

    }

    public SortedArrayPriorityQueue getAlarms() throws IOException, QueueOverflowException {
        ICalendar ical = Biweekly.parse(file).first();
        SortedArrayPriorityQueue sorted = new SortedArrayPriorityQueue(ical.getEvents().size());
        Calendar calendar = Calendar.getInstance();
        Date date;
        long[] times = new long[ical.getEvents().size()];
        //Add the alarms to a linked list
        for (int i = 0; i < ical.getEvents().size(); i++) {
            date = ical.getEvents().get(i).getDateStart().getValue();
            times[i] = date.getTime();
        }

        Arrays.sort(times);
        //  ArrayUtils.reverse(times);
        Date dateForOrder = new Date();
        int priority = ical.getEvents().size();
        for (int i = 0; i < times.length; i++) {
            dateForOrder.setTime(times[i]);
            calendar.setTime(dateForOrder);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            sorted.add(sdf.format(calendar.getTime()), priority);
            System.out.println(sorted.toString());
            priority--;
        }
        return sorted;
    }

    public void testTimes() throws IOException {
        ICalendar ical = Biweekly.parse(file).first();
        Date today = new Date();
        Date date;
        long currentTime = today.getTime();
        HashMap<Long, Long> map = new HashMap<>();
        //Add the alarms to a linked list
        for (int i = 0; i < ical.getEvents().size(); i++) {
            date = ical.getEvents().get(i).getDateStart().getValue();
            long key = date.getTime() - currentTime;
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        //    map.put(key, sdf.format(cal.getTime()));
           System.out.println(sdf.format(cal.getTime()));
        }

    }

}
