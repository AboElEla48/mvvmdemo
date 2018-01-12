package com.foureg.baseframework.viewmodel;

import com.foureg.baseframework.annotations.DataModel;
import com.foureg.baseframework.ui.BaseActivity;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aboelela on 12/01/18.
 * Test code for view model
 */
public class BaseViewModelTest extends BaseViewModel<BaseActivity>
{
    @DataModel
    private DataModelExample dataModelExample;

    @Test
    public void test_createFieldsAnnotatedAsDataModels() throws Exception {

        createFieldsAnnotatedAsDataModels();

        Assert.assertTrue(dataModelExample != null);
    }

}