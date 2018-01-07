package com.foureg.baseframework.scanners;

import com.foureg.baseframework.annotations.ContentViewID;
import com.foureg.baseframework.annotations.ViewModel;
import com.foureg.baseframework.ui.interfaces.BaseView;

/**
 * Created by aboelela on 07/01/18.
 * Test code
 */
@ContentViewID(110)
class TestDummyClass implements BaseView
{
    String NoAnnotationStr = "NoAnnotationStr";

    @ViewModel
    @TestDummyFieldAnnotationClass
    String viewModel1Str = "viewModel1Str";

    @ViewModel
    String viewModel2Str = "viewModel2Str";

    @TestDummyFieldAnnotationClass
    String dummyAnnotation = "dummyAnnotation";

}
