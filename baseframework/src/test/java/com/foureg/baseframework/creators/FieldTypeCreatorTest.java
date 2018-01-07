package com.foureg.baseframework.creators;

import com.foureg.baseframework.annotations.ViewModel;
import com.foureg.baseframework.scanners.FieldAnnotationTypeScanner;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 07/01/18.
 * Test code for field creator
 */
public class FieldTypeCreatorTest
{
    @Test
    public void test_createFieldObject() throws Exception {
        TestFieldsHolderDummy fieldsHolderDummy = new TestFieldsHolderDummy();

        FieldAnnotationTypeScanner.extractFieldsAnnotatedBy(fieldsHolderDummy, ViewModel.class,
                new Consumer<Field>()
                {
                    @Override
                    public void accept(Field field) throws Exception {
                        // create first view model
                        Object o = FieldTypeCreator.createFieldObject(field);

                        Assert.assertTrue(o != null);
                        Assert.assertTrue(o.getClass().getName().equals(TestFieldTypeDummy.class.getName()));
                    }
                });


    }

}