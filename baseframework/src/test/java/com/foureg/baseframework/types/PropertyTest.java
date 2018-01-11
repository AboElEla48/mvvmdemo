package com.foureg.baseframework.types;

import org.junit.Assert;
import org.junit.Test;

import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 11/01/18.
 * Test class for Property
 */
public class PropertyTest
{
    private static final String testText = "Test Text";
    private static final int testVal = 25;


    @Test
    public void test_setNormalVal() throws Exception {
        Property<String> str = new Property<>();
        str.set(testText);

        Assert.assertTrue(str.get().equals(testText));
    }

    @Test
    public void test_Observable() throws Exception {
        Property<Integer> val = new Property<>();

        val.asObservable(new Consumer<Integer>()
        {
            @Override
            public void accept(Integer integer) throws Exception {
                Assert.assertTrue(integer.intValue() == testVal);
            }
        });

        val.set(testVal);
    }

    @Test
    public void test_Complete() throws Exception {
        final Property<String> str = new Property<>();

        str.asObservable(new Consumer<String>()
        {
            @Override
            public void accept(String s) throws Exception {
                Assert.assertTrue(s.equals(testText));

                str.completeSubscription();
                Assert.assertTrue(str.get().equals(testText));
            }
        });

        str.set(testText);
    }
}