package com.foureg.baseframework.scanners;

import com.foureg.baseframework.annotations.ViewModel;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by aboelela on 07/01/18.
 * Test code for field annotation scanner by type
 */
public class FieldAnnotationTypeScannerTest
{
    @Test
    public void test_extractFieldsAnnotatedBy() throws Exception {
        ArrayList<Field> resultFields = new ArrayList<>();
        TestDummyClass testDummyClass = new TestDummyClass();
        FieldAnnotationTypeScanner.extractFieldsAnnotatedBy(testDummyClass,
                ViewModel.class,
                resultFields);

        Assert.assertTrue(resultFields.size() == 2);
        Assert.assertTrue(resultFields.get(0).getName().equals(testDummyClass.viewModel1Str));
        Assert.assertTrue(resultFields.get(1).getName().equals(testDummyClass.viewModel2Str));
    }

}