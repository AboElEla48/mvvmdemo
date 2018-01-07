package com.foureg.baseframework.scanners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by aboelela on 07/01/18.
 * This class will scan specific object to extract list of fields annotated with specific annotation
 */

public final class FieldAnnotationTypeScanner
{
    /**
     * Extract all fields annotated with given annotation in the new result fields array
     *
     * @param objectToScan        : the object to extract fields from
     * @param requiredAnnotation  : the required annotation type to search for it
     * @param resultFieldConsumer : The consumer to be notified when the field in found as required annotation
     */
    public static void extractFieldsAnnotatedBy(Object objectToScan,
                                                final Class<?> requiredAnnotation,
                                                final Consumer<Field> resultFieldConsumer) {
        Field[] fields = objectToScan.getClass().getDeclaredFields();

        // Loop fields in given object
        Observable.fromIterable(Arrays.asList(fields))
                .subscribe(new Consumer<Field>()
                {
                    @Override
                    public void accept(Field field) throws Exception {
                        // search for annotation on that field
                        processFieldsAnnotatedBy(field, requiredAnnotation, resultFieldConsumer);
                    }
                });

    }

    /**
     * Given one field, search the required annotation in fields annotations
     *
     * @param field              : the field to scan its annotations
     * @param requiredAnnotation : the required annotation to find
     * @param resultFieldConsumer : The consumer to be notified when the field in found as required annotation
     */
    private static void processFieldsAnnotatedBy(final Field field,
                                                 final Class<?> requiredAnnotation,
                                                 final Consumer<Field> resultFieldConsumer) {
        Observable.fromIterable(Arrays.asList(field.getDeclaredAnnotations()))
                .filter(new Predicate<Annotation>()
                {
                    @Override
                    public boolean test(Annotation annotation) throws Exception {
                        // compare annotations to find the required annotation
                        return annotation.annotationType().getName().equals(requiredAnnotation.getName());
                    }
                })
                .subscribe(new Consumer<Annotation>()
                {
                    @Override
                    public void accept(Annotation annotation) throws Exception {
                        Observable.create(new ObservableOnSubscribe<Field>()
                        {
                            @Override
                            public void subscribe(ObservableEmitter<Field> emitter) throws Exception {
                                // Annotation is found, add this field
                                emitter.onNext(field);
                            }
                        }).subscribe(resultFieldConsumer);

                    }
                });
    }
}
