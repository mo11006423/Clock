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
import queuemanager.SortedArrayPriorityQueue;

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

        //   System.out.println(ah.setDate(time));
    }

    /**
     * Test of getAlarms method, of class AlarmHandler.
     */
    @Test
    public void testSetAlarms() throws Exception {
        ah.setAlarms("17:09");
         ah.setAlarms("18:09");
        System.out.println(ah.getAlarms());
        //System.out.println(ah.setAlarms("20:43"));
      //  ah.saveAlarms();
      //  System.out.println(ah.getAlarms());
      //  System.out.println(ah.getAlarms());
        ah.alarmTime();

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

    /**
     * Test of saveAlarms method, of class AlarmHandler.
     */
    @Test
    public void testSaveAlarms() throws Exception {
        System.out.println("saveAlarms");
        AlarmHandler instance = new AlarmHandler();
        instance.saveAlarms();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNextAlarm method, of class AlarmHandler.
     */
    @Test
    public void testGetNextAlarm() {
        System.out.println("getNextAlarm");
        AlarmHandler instance = new AlarmHandler();
        Date expResult = null;
        Date result = instance.getNextAlarm();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAlarms method, of class AlarmHandler.
     */
    @Test
    public void testGetAlarms() {
        System.out.println("getAlarms");
        AlarmHandler instance = new AlarmHandler();
        SortedArrayPriorityQueue expResult = null;
        SortedArrayPriorityQueue result = instance.getAlarms();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of alarmTime method, of class AlarmHandler.
     */
    @Test
    public void testAlarmTime() {
        System.out.println("alarmTime");
        AlarmHandler instance = new AlarmHandler();
        boolean expResult = false;
        boolean result = instance.alarmTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
