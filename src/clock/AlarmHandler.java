/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clock;

import biweekly.ICalVersion;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Jamie Simpson
 */
public class AlarmHandler {

    public void saveAlarm(String alarmTime) throws IOException, ParseException {

        ICalendar ical = new ICalendar();

        VEvent event = new VEvent();

        ical.setVersion(ICalVersion.V2_0);
        ical.setProductId("-//Jamie Simpson//Alarm Clock//EN");
        String hour = alarmTime.charAt(0) + "" + alarmTime.charAt(1);
        String minutes = alarmTime.charAt(3) + "" + alarmTime.charAt(4);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minutes));
        Date date = calendar.getTime();

        //   event.setDateStart(new DateStart(start, false));
        //   ical.addEvent(event);
        //  File file = new File("C:\\Users\\Jamie Simpson\\Desktop\\Alarms.ics");
        //  Biweekly.write(ical).go(file);
    }

    public void readAlarm() {

    }

}
