package com.foureg.baseframework.creators;

import android.app.Activity;
import android.view.View;

import com.foureg.baseframework.annotations.ViewId;
import com.foureg.baseframework.annotations.ViewModel;
import com.foureg.baseframework.ui.interfaces.BaseView;

/**
 * Created by aboelela on 07/01/18.
 * test code for class holding objects
 */

class TestFieldsHolderDummy implements BaseView
{
    @ViewModel
    TestFieldTypeDummy fieldTypeDummy;

    @ViewId(15)
    View fieldVal;

    @Override
    public Activity getActivity() {
        return new Activity();
    }
}
