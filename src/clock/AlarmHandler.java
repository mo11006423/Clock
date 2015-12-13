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
import java.util.Calendar;
import java.util.Date;

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

    public void readAlarm() throws IOException {
        ICalendar ical = Biweekly.parse(file).first();
        System.out.println(ical.getEvents().size());

    }

}
