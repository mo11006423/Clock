/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clock;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jamie Simpson
 */
public class AlarmHandlerTest {

    public AlarmHandlerTest() {
    }

    /**
     * Test of setDate method, of class AlarmHandler.
     */
    @Test
    public void testSetDate() {
        String time = "13:33";
        AlarmHandler ah = new AlarmHandler();
        System.out.println(ah.setDate(time));
    }

    /**
     * Test of getAlarms method, of class AlarmHandler.
     */
    @Test
    public void testGetAlarms() throws Exception {
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
