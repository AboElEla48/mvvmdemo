package com.foureg.baseframework.scanners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by aboelela on 07/01/18.
 * Test code
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface TestDummyFieldAnnotationClass
{
}
