package com.foureg.baseframework.scanners;

import org.junit.Assert;
import org.junit.Test;

import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 07/01/18.
 * Test code for ContentViewId annotation scanner
 */
public class ContentViewIdScannerTest
{
    @Test
    public void test_extractViewContentID() throws Exception {
        ContentViewIDScanner.extractViewContentID(new TestDummyClass(),
                new Consumer<Integer>()
                {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Assert.assertTrue(integer.intValue() == 110);
                    }
                });
    }

}