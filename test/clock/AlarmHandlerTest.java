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
     * Test of saveAlarm method, of class AlarmHandler.
     */
    @Test
    public void testSaveAlarm() throws Exception {
    }

    /**
     * Test of getAlarms method, of class AlarmHandler.
     */
    @Test
    public void testGetAlarms() throws Exception {
    }

    /**
     * Test of testTimes method, of class AlarmHandler.
     */
    @Test
    public void testTestTimes() throws Exception {
        AlarmHandler ah = new AlarmHandler();

        System.out.println(ah.getAlarms().toString());
        ah.RemoveAlarm();
      //  System.out.println(ah.getAlarms().head());

    }

}
