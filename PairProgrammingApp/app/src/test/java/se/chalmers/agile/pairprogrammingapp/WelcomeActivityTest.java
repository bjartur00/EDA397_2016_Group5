package se.chalmers.agile.pairprogrammingapp;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Omar on 27.4.2016.
 */
public class WelcomeActivityTest extends TestCase {

    @Test
    public void testPlus() throws Exception {
        assertEquals(4, WelcomeActivity.plus(2,2));
    }
}