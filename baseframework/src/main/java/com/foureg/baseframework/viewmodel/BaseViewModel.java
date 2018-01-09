package com.foureg.baseframework.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foureg.baseframework.annotations.viewmodelfields.ViewModelTextField;
import com.foureg.baseframework.scanners.FieldAnnotationTypeScanner;
import com.foureg.baseframework.ui.interfaces.ActivityLifeCycle;
import com.foureg.baseframework.ui.interfaces.BaseView;
import com.foureg.baseframework.ui.interfaces.FragmentLifeCycle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by aboelela on 06/01/18.
 * This should be the base view model of all view models
 */

public class BaseViewModel<V extends BaseView> implements FragmentLifeCycle, ActivityLifeCycle
{
    /**
     * init View Model after creation
     *
     * @param baseView : the baseView associated with this view model
     */
    public void initViewModel(V baseView) {
        this.baseView = baseView;

        initViewModelFieldsAnnotatedAsTextViewField();
    }

    /**
     * scan fields annotated as TextView fields to associate it with text view
     */
    private void initViewModelFieldsAnnotatedAsTextViewField() {
        FieldAnnotationTypeScanner.extractFieldsAnnotatedBy(this,
                ViewModelTextField.class,
                new Consumer<Field>()
                {
                    @Override
                    public void accept(final Field field) throws Exception {
                        Observable.fromIterable(Arrays.asList(field.getDeclaredAnnotations()))
                                .filter(isAnnotationEquals(ViewModelTextField.class))
                                .subscribe(new Consumer<Annotation>()
                                {
                                    @Override
                                    public void accept(Annotation annotation) throws Exception {
                                        int resId = ((ViewModelTextField) annotation).value();

                                        setViewModelTextViewFieldAction(field.getName(), resId);

                                    }
                                });
                    }
                });
    }

    /**
     * Associate the text view annotation with the actual action to set text to view
     *
     * @param resId the view resource Id
     */
    private void setViewModelTextViewFieldAction(String fieldName, final int resId) {
        PublishSubject<String> fieldPublishSubject = PublishSubject.create();

        fieldPublishSubject.subscribe(new Consumer<String>()
        {
            @Override
            public void accept(String textVal) throws Exception {
                TextView textView = (TextView) baseView.findViewById(resId);
                textView.setText(textVal);
            }
        });

        // Add this view and its corresponding publish subject to actions map
        viewModelFieldsActions.put(fieldName, fieldPublishSubject);
    }

    /**
     * set the value of the field and trigger the corresponding action to update associated View
     *
     * @param fieldName : The field name in the view model to update
     * @param value     : the value to be assigned to field
     */
    public void setValue(final String fieldName, final Object value) {
        // Change the field value
        Observable.fromIterable(Arrays.asList(this.getClass().getDeclaredFields()))
                .filter(new Predicate<Field>()
                {
                    @Override
                    public boolean test(Field field) throws Exception {
                        return field.getName().equals(fieldName);
                    }
                })
                .subscribe(new Consumer<Field>()
                {
                    @Override
                    public void accept(Field field) throws Exception {
                        boolean isAccessible = field.isAccessible();
                        field.setAccessible(true);
                        field.set(BaseViewModel.this, value);
                        field.setAccessible(isAccessible);
                    }
                });

        // trigger the corresponding change in view
        viewModelFieldsActions.get(fieldName).onNext(value);
    }


    /**
     * Check if field annotated with given annotation
     *
     * @return : Predicate with boolean status
     */
    private static Predicate<Annotation> isAnnotationEquals(final Class<?> requiredAnnotationType) {
        return new Predicate<Annotation>()
        {
            @Override
            public boolean test(Annotation annotation) throws Exception {
                return annotation.annotationType().getName().equals(requiredAnnotationType.getName());
            }
        };
    }

    /**
     * Get the baseView for further usage
     *
     * @return The base view associated with this view model
     */
    public V getView() {
        return baseView;
    }

    @Override
    public View findViewById(int resId) {
        return baseView.findViewById(resId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public boolean onActivityBackPressed() {
        return false;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    }

    private V baseView;

    private HashMap<String, PublishSubject> viewModelFieldsActions = new HashMap<>();
}
