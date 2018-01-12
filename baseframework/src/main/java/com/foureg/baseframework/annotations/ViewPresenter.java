package com.foureg.baseframework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by aboelela on 10/01/18.
 * This annotation for fields declared in view to represent object to ViewPresenter that will receive
 * UI calls/actions
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ViewPresenter
{
}
