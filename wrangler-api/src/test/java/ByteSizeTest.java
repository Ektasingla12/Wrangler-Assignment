package io.cdap.wrangler.api.parser;

import org.junit.Assert;
import org.junit.Test;

public class ByteSizeTest {

    @Test
    public void testBytes() {
        ByteSize b = new ByteSize("100B");
        Assert.assertEquals(100L, b.getBytes());
    }

    @Test
    public void testKBtoBytes() {
        ByteSize b = new ByteSize("2KB");
        Assert.assertEquals(2048L, b.getBytes());
    }

    @Test
    public void testMBtoBytes() {
        ByteSize b = new ByteSize("1.5MB");
        Assert.assertEquals(1572864L, b.getBytes()); // 1.5 * 1024 * 1024
    }

    @Test
    public void testGBtoBytes() {
        ByteSize b = new ByteSize("1GB");
        Assert.assertEquals(1073741824L, b.getBytes());
    }

    @Test
    public void testTBtoBytes() {
        ByteSize b = new ByteSize("1TB");
        Assert.assertEquals(1099511627776L, b.getBytes());
    }
}