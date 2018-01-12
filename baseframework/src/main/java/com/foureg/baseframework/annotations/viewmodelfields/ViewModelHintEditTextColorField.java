package com.foureg.baseframework.annotations.viewmodelfields;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by aboelela on 12/01/18.
 * Annotation for fields declared in view Model to control hint text color of editor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ViewModelHintEditTextColorField
{
    int value();
    String fieldName();
}
