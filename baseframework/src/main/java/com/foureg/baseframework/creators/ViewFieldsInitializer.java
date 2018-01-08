package com.foureg.baseframework.creators;

import com.foureg.baseframework.annotations.ViewId;
import com.foureg.baseframework.ui.interfaces.BaseView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by aboelela on 07/01/18.
 * This class is responsible for initializing the Views fields in BaseView with its corresponding
 * views from XML
 */

class ViewFieldsInitializer
{
    /**
     * Init fields annotated as ViewId with its View from XML
     * @param baseView : the host view
     */
    static void initViewsFields(final BaseView baseView) {
        Field[] fields = baseView.getClass().getDeclaredFields();

        // Loop fields in given object
        Observable.fromIterable(Arrays.asList(fields))
                .subscribe(new Consumer<Field>()
                {
                    @Override
                    public void accept(Field field) throws Exception {
                        // search for fields annotated as views
                        processViewsFields(baseView, field);
                    }
                });
    }

    /**
     * Init fields that are declared as view fields
     *
     * @param baseView: the holder view of the field
     * @param field     : field to examine if it is view field or not
     */
    private static void processViewsFields(final BaseView baseView, final Field field) {
        Observable.fromIterable(Arrays.asList(field.getDeclaredAnnotations()))
                .filter(new Predicate<Annotation>()
                {
                    @Override
                    public boolean test(Annotation annotation) throws Exception {
                        // compare annotations to find the required annotation
                        return annotation.annotationType().getName().equals(ViewId.class.getName());
                    }
                })
                .subscribe(new Consumer<Annotation>()
                {
                    @Override
                    public void accept(Annotation annotation) throws Exception {
                        initViewFieldWithXMLView(baseView, field, annotation);
                    }
                });
    }

    /**
     * Given a view field, init it with its XML view
     *
     * @param baseView   : the host base view
     * @param field      : the view field
     * @param annotation : the ViewId annotation
     */
    private static void initViewFieldWithXMLView(final BaseView baseView,
                                                 final Field field,
                                                 Annotation annotation) {
        try {
            // This field is View field (declared with annotation ViewId)
            boolean isFieldAccessible = field.isAccessible();
            field.setAccessible(true);
            int resId = ((ViewId) annotation).value();
            field.set(baseView, baseView.findViewById(resId));
            field.setAccessible(isFieldAccessible);
        }
        catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
}
