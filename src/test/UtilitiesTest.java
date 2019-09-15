package test;

import org.logic.Utilities;

import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest
{

    @org.junit.jupiter.api.Test
    void testDateConversion()
    {
        boolean actual1 = Utilities.isDate("21-03-2019");
        assertTrue(actual1);
        boolean actual2 = Utilities.isDate("03-03-2018");
        assertTrue(actual2);
        boolean actual3 = Utilities.isDate("21-33-2019");
        assertFalse(actual3);
        boolean actual4 = Utilities.isDate("32-01-2019");
        assertFalse(actual4);
        boolean actual5 = Utilities.isDate("30-02-2019");
        assertFalse(actual5);
        boolean actual6 = Utilities.isDate("21/04/2019");
        assertFalse(actual6);
    }
}