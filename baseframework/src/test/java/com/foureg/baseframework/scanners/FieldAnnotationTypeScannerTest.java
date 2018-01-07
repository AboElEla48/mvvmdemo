package com.foureg.baseframework.scanners;

import com.foureg.baseframework.annotations.ViewModel;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 07/01/18.
 * Test code for field annotation scanner by type
 */
public class FieldAnnotationTypeScannerTest
{
    @Test
    public void test_extractFieldsAnnotatedBy() throws Exception {

        final TestDummyClass testDummyClass = new TestDummyClass();
        FieldAnnotationTypeScanner.extractFieldsAnnotatedBy(testDummyClass,
                ViewModel.class,
                new Consumer<Field>()
                {
                    @Override
                    public void accept(Field field) throws Exception {
                        Assert.assertTrue(field.getName().equals(testDummyClass.viewModel1Str)
                         || field.getName().equals(testDummyClass.viewModel2Str));
                    }
                });
    }

}