package com.foureg.baseframework.scanners;

import com.foureg.baseframework.annotations.ContentViewId;
import com.foureg.baseframework.ui.interfaces.BaseView;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by aboelela on 07/01/18.
 * This class should scan activities and fragments to get the resource ID of its content type
 * declared above the Activity/Fragment
 */

public class ContentViewIDScanner
{
    /**
     * Extract the resource ID declared over the activity/fragment to set its content with that resource
     * @param baseView : Activity/Fragment
     * @param resIDConsumer : consumer to receive result
     */
    public static void extractViewContentID(BaseView baseView,
                                            final Consumer<Integer> resIDConsumer) {
        Observable.fromIterable(Arrays.asList(baseView.getClass().getDeclaredAnnotations()))
                .filter(isAnnotationContentViewID())
                .subscribe(consumeAnnotationValue(resIDConsumer));
    }

    /**
     * Check if class is annotated with ContentViewId or not
     * @return : Predicate with boolean status
     */
    private static Predicate<Annotation> isAnnotationContentViewID() {
        return new Predicate<Annotation>()
        {
            @Override
            public boolean test(Annotation annotation) throws Exception {
                return annotation.annotationType().getName().equals(ContentViewId.class.getName());
            }
        };
    }

    /**
     * Extract the ID value of given annotation and notify result consumer with that ID
     * @param resIDConsumer : this consumer will receive the result res ID
     * @return : the consumer to extract the value
     */
    private static Consumer<Annotation> consumeAnnotationValue(final Consumer<Integer> resIDConsumer) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                final int resourceId = ((ContentViewId) annotation).value();
                Observable.create(new ObservableOnSubscribe<Integer>()
                {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(resourceId);
                    }
                }).subscribe(resIDConsumer);
            }
        };
    }
}
