package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        int i = 4 % 8;
        int i1 = 8 % 8;
        int i2 = 7 % 6;
        System.out.println("上卦    "+i);
        System.out.println("下卦    "+i1);
        System.out.println("爻      "+i2);
    }
}