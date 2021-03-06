package com.foureg.baseframework.creators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by aboelela on 19/01/18.
 * dummy annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface TestDummyAnnotation
{
}
