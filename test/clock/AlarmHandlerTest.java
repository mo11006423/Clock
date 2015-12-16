/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clock;

import Jical.components.Ical;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jamie Simpson
 */
public class AlarmHandlerTest {

    AlarmHandler ah = new AlarmHandler();

    public AlarmHandlerTest() {
    }

    /**
     * Test of setDate method, of class AlarmHandler.
     */
    @Test
    public void testSetDate() {
        String time = "13:33";

        System.out.println(ah.setDate(time));
    }

    /**
     * Test of getAlarms method, of class AlarmHandler.
     */
    @Test
    public void testSetAlarms() throws Exception {
        System.out.println(ah.setAlarms("19:43"));
        //  System.out.println(ah.setAlarms("20:43"));
          ah.saveAlarms();

    }

    /**
     * Test of RemoveAlarm method, of class AlarmHandler.
     */
    @Test
    public void testRemoveAlarm() throws Exception {
    }

    /**
     * Test of getStringAlarms method, of class AlarmHandler.
     */
    @Test
    public void testGetStringAlarms() throws Exception {
    }

    /**
     * Test of getListAlarms method, of class AlarmHandler.
     */
    @Test
    public void testGetListAlarms() {
    }

}
