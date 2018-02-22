package com.cts.interview.headers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by James on 22/02/2018.
 */
public class ColumnHeadersTest {

    private ColumnHeaders headers;

    @Before
    public void before() {
        headers = new ColumnHeaders();

    }

    @Test
    public void testAReturn() {
        Assert.assertEquals("A", headers.evaluateColumnNumber(1));
    }

    @Test
    public void testZReturn() {
        Assert.assertEquals("Z", headers.evaluateColumnNumber(26));
    }

    @Test
    public void testAHReturn() {
        Assert.assertEquals("AH", headers.evaluateColumnNumber(34));
    }

    @Test
    public void testDZReturn() {
        Assert.assertEquals("DZ", headers.evaluateColumnNumber(130));
    }

    @Test
    public void testCUZReturn() {
        Assert.assertEquals("CUZ", headers.evaluateColumnNumber(2600));
    }
}
