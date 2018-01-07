package com.foureg.baseframework.creators;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created by aboelela on 07/01/18.
 * This class is responsible for creating field type according to its declared type
 * i.e. Field declared as String, will be created new String()
 */

class FieldTypeCreator
{
    /**
     * Try to create field object according to declaration
     * @param field : the field where it is required to create its object
     * @return : the created object
     */
    static Object createFieldObject(Field field) {
        try {
            Constructor<?> fieldObjectConstructor = field.getType().getDeclaredConstructor();
            fieldObjectConstructor.setAccessible(true);
            return fieldObjectConstructor.newInstance();
        }catch (Exception ex) {
            return null;
        }

    }
}
