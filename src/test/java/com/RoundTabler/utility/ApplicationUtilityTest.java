package com.RoundTabler.utility;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static com.RoundTabler.utility.ApplicationUtility.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationUtilityTest {

    @Test
    public void isNumericNegativeTest() {
        boolean result = isNumeric("testing");
        assertFalse(result);

        boolean result2 = isNumeric("");
        assertFalse(result2);

        boolean result3 = isNumeric("4235testing1234");
        assertFalse(result3);
    }

    @Test
    public void isNumericPositiveTest() {
        boolean result = isNumeric("123");
        assertTrue(result);

        boolean result2 = isNumeric("22");
        assertTrue(result2);

        boolean result3 = isNumeric("0");
        assertTrue(result3);
    }

    @Test
    public void charToIntPositiveTest() {
        int result = charToInt('1');
        assertEquals(1, result);

        int result2 = charToInt('2');
        assertEquals(2, result2);

        int result3 = charToInt('3');
        assertEquals(3, result3);
    }


    @Test
    public void readFileTest() {
        int expectedNumber = 19521;
        HashSet<String> abaNumbers = getABANumbersFromFile();
        assertEquals(expectedNumber, abaNumbers.size());
    }
}
