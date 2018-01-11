package com.foureg.baseframework.types;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by aboelela on 11/01/18.
 * This is a holder class for data types
 */

public class Property<T>
{
    /**
     * set the value of the data object
     *
     * @param object : the new value
     */
    public void set(T object) {
        data = object;

        if(dataPubSubject != null) {
            dataPubSubject.onNext(data);
        }
    }

    /**
     * get the value of the data object
     *
     * @return : the data object
     */
    public T get() {
        return data;
    }

    /**
     * Get value as observable
     * @param onNext : the consumer object to emit at setting value
     * @return : the created observable
     */
    public Observable asObservable(Consumer<T> onNext) {
        dataPubSubject = PublishSubject.create();
        dataPubSubject.subscribe(onNext);

        return dataPubSubject;
    }

    /**
     * Complete the subscription of data publish subject
     */
    public void completeSubscription() {
        if(dataPubSubject != null) {
            dataPubSubject.onComplete();
            dataPubSubject = null;
        }

    }

    private T data;
    private PublishSubject<T> dataPubSubject;
}
