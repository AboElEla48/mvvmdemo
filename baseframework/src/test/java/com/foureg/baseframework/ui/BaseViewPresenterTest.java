package com.foureg.baseframework.ui;

import com.foureg.baseframework.annotations.DataModel;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aboelela on 12/01/18.
 * Sample presenter
 */
public class BaseViewPresenterTest extends BaseViewPresenter<BaseActivity>
{
    @DataModel
    DataModelExample dataModelExample;

    @Test
    public void test_createFieldsAnnotatedAsDataModels() throws Exception {
        createFieldsAnnotatedAsDataModels();;

        Assert.assertTrue(dataModelExample != null);
    }

}