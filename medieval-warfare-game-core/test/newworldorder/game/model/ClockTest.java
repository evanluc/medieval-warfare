package newworldorder.game.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import newworldorder.game.model.Clock;

public class ClockTest
{

    @Test
    public void testGetNow()
    {
        Date now = Clock.getNow();
        Date now2 = new Date();
        assertTrue(now.equals(now2));
    }
    
    @Test
    public void testGetNow2()
    {
        Date now = Clock.getNow();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Date now2 = new Date();
        assertTrue(now.before(now2));
    }

}