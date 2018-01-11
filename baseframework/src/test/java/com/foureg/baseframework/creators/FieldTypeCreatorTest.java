package com.foureg.baseframework.creators;

import com.foureg.baseframework.annotations.ViewModel;
import com.foureg.baseframework.scanners.FieldAnnotationTypeScanner;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
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

    @Test
    public void test_temp() throws Exception {
        List<Integer> arr1 = Arrays.asList(1, 2, 3);
        List<Integer> arr2 = Arrays.asList(4, 5, 6);
        ArrayList<List<Integer>> complexArrays = new ArrayList<>();
        complexArrays.add(arr1);
        complexArrays.add(arr2);

        acc = 0;
        Observable.fromIterable(complexArrays)
                .subscribe(arrInt -> Observable.fromIterable(arrInt)
                .blockingSubscribe(val -> accumulateVal(val)));

        Assert.assertTrue(acc == (1 + 2 + 3 + 4 + 5 + 6));
    }

    private void accumulateVal(int v) {
        acc += v;
    }

    private int acc;

}