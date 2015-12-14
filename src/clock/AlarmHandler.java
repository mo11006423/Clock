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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
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
        Date today = new Date();
        Date date;
        long currentTime = today.getTime();
        Map<Long, String> currentAlarmMap = new TreeMap<>();
        Map<Long, String> pastAlarmsMap = new TreeMap<>();
        //Add the alarms to a linked list
        for (int i = 0; i < ical.getEvents().size(); i++) {
            date = ical.getEvents().get(i).getDateStart().getValue();
            long key = date.getTime() - currentTime;
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            if (key < 0) {
                pastAlarmsMap.put(key, sdf.format(cal.getTime()));
            } else {
                currentAlarmMap.put(key, sdf.format(cal.getTime()));
            }
        }
        String[] currentAlarms = new String[currentAlarmMap.size()];
        currentAlarms = currentAlarmMap.values().toArray(new String[0]);
        String[] pastAlarms = new String[pastAlarmsMap.size()];
        pastAlarms = pastAlarmsMap.values().toArray(new String[0]);
        int priority = ical.getEvents().size();

        for (String currentAlarm : currentAlarms) {
            sorted.add(currentAlarm, priority);
            priority--;
        }
        for (String pastAlarm : pastAlarms) {
            sorted.add(pastAlarm, priority);
            priority--;
        }

        return sorted;
    }

}
