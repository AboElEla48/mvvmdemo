package com.foureg.baseframework.creators;

import com.foureg.baseframework.annotations.Singleton;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by aboelela on 07/01/18.
 * This class is responsible for creating field type according to its declared type
 * i.e. Field declared as String, will be created new String()
 */

public class FieldTypeCreator
{
    /**
     * Try to create field object according to declaration
     *
     * @param field : the field where it is required to create its object
     * @return : the created object
     */
    public static Object createFieldObject(final Field field) {
        createdObject = null;

        Observable.fromIterable(Arrays.asList(field.getType().getDeclaredAnnotations()))
                .filter(new Predicate<Annotation>()
                {
                    @Override
                    public boolean test(Annotation annotation) throws Exception {
                        return annotation.annotationType().getName().equals(Singleton.class.getName());
                    }
                })
                .blockingSubscribe(new Consumer<Annotation>()
                {
                    @Override
                    public void accept(Annotation annotation) throws Exception {
                        createSingletonObject(field.getType());
                    }
                });

        if (createdObject == null) {
            createdObject = doCreateObject(field.getType());
        }

        return createdObject;
    }

    /**
     * create object as singleton
     *
     * @param cls : the type of the class to create
     */
    private static void createSingletonObject(Class<?> cls) {
        SingletonCreator.getInstance().createObject(cls, new Emitter<Object>()
        {
            @Override
            public void onNext(Object value) {
                createdObject = value;
            }

            @Override
            public void onError(Throwable error) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * Do create object according to given type
     *
     * @param clsType : the type to create class
     * @return : return the created object
     */
    static Object doCreateObject(Class<?> clsType) {
        try {
            Constructor<?> fieldObjectConstructor = clsType.getDeclaredConstructor();
            fieldObjectConstructor.setAccessible(true);
            return fieldObjectConstructor.newInstance();
        } catch (Exception ex) {
            return null;
        }
    }

    private static Object createdObject = null;
}
