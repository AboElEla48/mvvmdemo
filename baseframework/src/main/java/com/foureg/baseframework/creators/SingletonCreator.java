package com.foureg.baseframework.creators;

import com.foureg.baseframework.types.Property;

import java.util.ArrayList;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by AboelelaA on 1/18/2018.
 * This is the class responsible for creating Singleton classes
 */

public class SingletonCreator
{
    private SingletonCreator() {
    }

    /**
     * get singleton object to creator
     *
     * @return : singleton object
     */
    public static SingletonCreator getInstance() {
        if (creator == null) {
            creator = new SingletonCreator();
        }
        return creator;
    }

    /**
     * Create object as singleton
     *
     * @param cls     : the object class type
     * @param emitter : the emitter to notify when creating object
     */
    public void createObject(final Class<?> cls, final Emitter<Object> emitter) {
        final Property<Object> singletonProperty = new Property<>();

        Observable.fromIterable(singleObjects)
                .filter(new Predicate<Object>()
                {
                    @Override
                    public boolean test(Object oneObj) throws Exception {
                        return isSameClassName(cls, oneObj);
                    }
                })
                .blockingSubscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(Object foundObj) throws Exception {
                        singletonProperty.set(foundObj);
                        emitter.onNext(singletonProperty.get());
                        emitter.onComplete();
                    }
                });

        if (singletonProperty.get() == null) {
            singletonProperty.set(FieldTypeCreator.doCreateObject(cls));
            singleObjects.add(singletonProperty.get());

            emitter.onNext(singletonProperty.get());
            emitter.onComplete();
        }
    }

    /**
     * Check if given class is the same as object class
     *
     * @param cls    : the class to compare
     * @param oneObj : the object to compare its type with given class
     * @return : boolean for comparison result
     */
    private boolean isSameClassName(Class<?> cls, Object oneObj) {
        return oneObj.getClass().getName().equals(cls.getName());
    }

    private static SingletonCreator creator;
    private ArrayList<Object> singleObjects = new ArrayList<>();
}
