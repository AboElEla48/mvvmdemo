package com.foureg.baseframework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by aboelela on 06/01/18.
 * This annotation to collect the content view ID of activities and fragments
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentViewID
{
    int value();
}
