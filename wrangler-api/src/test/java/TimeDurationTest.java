package io.cdap.wrangler.api.parser;

import org.junit.Assert;
import org.junit.Test;

public class TimeDurationTest {

    @Test
    public void testMilliseconds() {
        TimeDuration t = new TimeDuration("1500ms");
        Assert.assertEquals(1500000000L, t.getNanoseconds());
    }

    @Test
    public void testSeconds() {
        TimeDuration t = new TimeDuration("2.5s");
        Assert.assertEquals(2500000000L, t.getNanoseconds());
    }

    @Test
    public void testNanoseconds() {
        TimeDuration t = new TimeDuration("500ns");
        Assert.assertEquals(500L, t.getNanoseconds());
    }

    @Test
    public void testMinutes() {
        TimeDuration t = new TimeDuration("1.5min");
        Assert.assertEquals(90000000000L, t.getNanoseconds());
    }
}