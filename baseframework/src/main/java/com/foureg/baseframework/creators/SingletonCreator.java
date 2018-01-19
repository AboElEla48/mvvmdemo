package com.foureg.baseframework.creators;

import java.util.ArrayList;

import io.reactivex.Emitter;
import io.reactivex.Observable;

/**
 * Created by AboelelaA on 1/18/2018.
 * This is the class responsible for creating Singleton classes
 */

public class SingletonCreator
{
    private SingletonCreator() {}

    /**
     * get singleton object to creator
     * @return : singleton object
     */
    public static SingletonCreator getInstance() {
        if(creator == null) {
            creator = new SingletonCreator();
        }
        return creator;
    }

    /**
     * Create object as singleton
     * @param cls : the object class type
     * @param emitter : the emitter to notify when creating object
     */
    public void createObject(Class<?> cls, Emitter<Object> emitter) {
        createdObj = null;
        Observable.fromIterable(singleObjects)
                .filter(oneObj -> isSameClassName(cls, oneObj))
                .blockingSubscribe(foundObj -> {
                    createdObj = foundObj;
                    emitter.onNext(createdObj);
                    emitter.onComplete();
                });

        if(createdObj == null) {
            createdObj = FieldTypeCreator.doCreateObject(cls);
            singleObjects.add(createdObj);

            emitter.onNext(createdObj);
            emitter.onComplete();
        }
    }

    /**
     * Check if given class is the same as object class
     * @param cls : the class to compare
     * @param oneObj : the object to compare its type with given class
     * @return : boolean for comparison result
     */
    private boolean isSameClassName(Class<?> cls, Object oneObj) {
        return oneObj.getClass().getName().equals(cls.getName());
    }

    private static SingletonCreator creator;
    private ArrayList<Object> singleObjects = new ArrayList<>();
    private Object createdObj = null;
}
