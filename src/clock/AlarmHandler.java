/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clock;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

/**
 *
 * @author Jamie Simpson
 */
public class AlarmHandler {

    public void saveAlarm(String alarmTime) {

        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Jamie Simpson//JamieAlarmClock 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
    }

}
