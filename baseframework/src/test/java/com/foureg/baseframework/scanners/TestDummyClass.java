package com.foureg.baseframework.scanners;

import com.foureg.baseframework.annotations.ViewModel;

/**
 * Created by aboelela on 07/01/18.
 * Test code
 */

class TestDummyClass
{
    String NoAnnotationStr = "NoAnnotationStr";

    @ViewModel
    @TestDummyAnnotationClass
    String viewModel1Str = "viewModel1Str";

    @ViewModel
    String viewModel2Str = "viewModel2Str";

    @TestDummyAnnotationClass
    String dummyAnnotation = "dummyAnnotation";

}
