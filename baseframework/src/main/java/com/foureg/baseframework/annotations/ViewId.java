package com.foureg.baseframework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by aboelela on 07/01/18.
 * This annotation to be declared over views in activities and fragments to associate it with its
 * xml views
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ViewId
{
    int value();
}
