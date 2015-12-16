/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clock;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import queuemanager.QueueOverflowException;
import queuemanager.QueueUnderflowException;
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

    public SortedArrayPriorityQueue setAlarms()  {
        
        return null;
    }

}
