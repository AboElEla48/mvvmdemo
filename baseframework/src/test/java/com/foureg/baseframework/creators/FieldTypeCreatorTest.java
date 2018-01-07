package com.foureg.baseframework.creators;

import com.foureg.baseframework.annotations.ViewModel;
import com.foureg.baseframework.scanners.FieldAnnotationTypeScanner;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by aboelela on 07/01/18.
 * Test code for field creator
 */
public class FieldTypeCreatorTest
{
    @Test
    public void test_createFieldObject() throws Exception {
        TestFieldsHolderDummy fieldsHolderDummy = new TestFieldsHolderDummy();
        ArrayList<Field> viewModels = new ArrayList<>();
        FieldAnnotationTypeScanner.extractFieldsAnnotatedBy(fieldsHolderDummy, ViewModel.class, viewModels);

        // create first view model
        Object o = FieldTypeCreator.createFieldObject(viewModels.get(0));

        Assert.assertTrue(o != null);
        Assert.assertTrue(o.getClass().getName().equals(TestFieldTypeDummy.class.getName()));
    }

}