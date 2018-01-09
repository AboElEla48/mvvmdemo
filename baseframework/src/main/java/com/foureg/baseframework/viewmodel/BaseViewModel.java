package com.foureg.baseframework.viewmodel;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.foureg.baseframework.annotations.viewmodelfields.ViewModelCheckBoxField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelHintEditTextField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelImageViewField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelTextField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelTextViewTextColorField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelViewVisibilityField;
import com.foureg.baseframework.exceptions.ErrorInitializingFramework;
import com.foureg.baseframework.scanners.FieldAnnotationTypeScanner;
import com.foureg.baseframework.ui.interfaces.BaseView;

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

public class BaseViewModel<V extends BaseView> /*implements FragmentLifeCycle, ActivityLifeCycle*/
{
    /**
     * init View Model after creation
     *
     * @param baseView : the baseView associated with this view model
     */
    public void initViewModel(V baseView) {
        this.baseView = baseView;

        initViewModelFieldsAnnotatedAsCheckBoxField();
        initViewModelFieldsAnnotatedAsTextViewField();
        initViewModelFieldsAnnotatedAsTextViewTextColorField();
        initViewModelFieldsAnnotatedAsHintEditTextField();
        initViewModelFieldsAnnotatedAsImageViewField();
        initViewModelFieldsAnnotatedAsViewVisibilityField();
    }

    /**
     * scan fields annotation with given annotation
     *
     * @param annotationType : the view model field annotation type class
     */
    private void processFieldOfAnnotation(final Class<?> annotationType) {
        FieldAnnotationTypeScanner.extractFieldsAnnotatedBy(this,
                annotationType,
                new Consumer<Field>()
                {
                    @Override
                    public void accept(final Field field) throws Exception {
                        Observable.fromIterable(Arrays.asList(field.getDeclaredAnnotations()))
                                .filter(isAnnotationEquals(annotationType))
                                .subscribe(getCorrespondingConsumerToAnnotation(field,
                                        annotationType));
                    }
                });
    }

    /**
     * Map the annotation type to the suitable consumer
     *
     * @param field          : the field of annotation
     * @param annotationType : the annotation type
     * @return : the corresponding consumer
     */
    private Consumer<Annotation> getCorrespondingConsumerToAnnotation(Field field,
                                                                      Class<?> annotationType) throws Exception {
        if (annotationType.getName().equals(ViewModelTextField.class.getName())) {
            return consumeTextViewAnnotation(field);
        } else if (annotationType.getName().equals(ViewModelTextViewTextColorField.class.getName())) {
            return consumeTextViewColorAnnotation(field);
        } else if (annotationType.getName().equals(ViewModelCheckBoxField.class.getName())) {
            return consumeCheckBoxAnnotation(field);
        } else if (annotationType.getName().equals(ViewModelHintEditTextField.class.getName())) {
            return consumeEditTextHintAnnotation(field);
        } else if (annotationType.getName().equals(ViewModelImageViewField.class.getName())) {
            return consumeImageViewAnnotation(field);
        } else if (annotationType.getName().equals(ViewModelViewVisibilityField.class.getName())) {
            return consumeViewVisibilityAnnotation(field);
        }

        throw new ErrorInitializingFramework("Annotation in View Model not found!");
    }

    /**
     * scan fields annotated as CheckBox fields to associate it with CheckBox
     */
    private void initViewModelFieldsAnnotatedAsCheckBoxField() {
        processFieldOfAnnotation(ViewModelCheckBoxField.class);
    }

    /**
     * scan fields annotated as TextView fields to associate it with text value
     */
    private void initViewModelFieldsAnnotatedAsTextViewField() {
        processFieldOfAnnotation(ViewModelTextField.class);
    }

    /**
     * scan fields annotated as TextView color fields to associate it with color value
     */
    private void initViewModelFieldsAnnotatedAsTextViewTextColorField() {
        processFieldOfAnnotation(ViewModelTextViewTextColorField.class);
    }

    /**
     * scan fields annotated as EditText fields to associate it with hint text value
     */
    private void initViewModelFieldsAnnotatedAsHintEditTextField() {
        processFieldOfAnnotation(ViewModelHintEditTextField.class);
    }

    /**
     * scan fields annotated as Image View fields to associate it with image value
     */
    private void initViewModelFieldsAnnotatedAsImageViewField() {
        processFieldOfAnnotation(ViewModelImageViewField.class);
    }

    /**
     * scan fields annotated as Visibility fields to associate it with visibility value
     */
    private void initViewModelFieldsAnnotatedAsViewVisibilityField() {
        processFieldOfAnnotation(ViewModelViewVisibilityField.class);
    }

    /**
     * create Text View annotation consumer
     *
     * @param field the field of annotation
     * @return the consumer of annotation
     */
    private Consumer<Annotation> consumeTextViewAnnotation(final Field field) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                int resId = ((ViewModelTextField) annotation).value();
                String fieldName = ((ViewModelTextField) annotation).fieldName();

                PublishSubject fieldPublishSubject = createTextViewPublishSubject(resId);

                // Add this view and its corresponding publish subject to actions map
                viewModelFieldsActions.put(fieldName, fieldPublishSubject);

            }
        };
    }

    /**
     * create Text View Color annotation consumer
     *
     * @param field the field of annotation
     * @return the consumer of annotation
     */
    private Consumer<Annotation> consumeTextViewColorAnnotation(final Field field) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                int resId = ((ViewModelTextViewTextColorField) annotation).value();
                String fieldName = ((ViewModelTextViewTextColorField) annotation).fieldName();

                PublishSubject fieldPublishSubject = createTextViewTextColorPublishSubject(
                        resId);

                // Add this view and its corresponding publish subject to actions map
                viewModelFieldsActions.put(fieldName, fieldPublishSubject);

            }
        };
    }

    /**
     * create CheckBox annotation consumer
     *
     * @param field the field of annotation
     * @return the consumer of annotation
     */
    private Consumer<Annotation> consumeCheckBoxAnnotation(final Field field) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                int resId = ((ViewModelCheckBoxField) annotation).value();
                String fieldName = ((ViewModelCheckBoxField) annotation).fieldName();

                PublishSubject fieldPublishSubject = createCheckBoxPublishSubject(
                        resId);

                // Add this view and its corresponding publish subject to actions map
                viewModelFieldsActions.put(fieldName, fieldPublishSubject);

            }
        };
    }

    /**
     * create Edit Text Hint text annotation consumer
     *
     * @param field the field of annotation
     * @return the consumer of annotation
     */
    private Consumer<Annotation> consumeEditTextHintAnnotation(final Field field) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                int resId = ((ViewModelHintEditTextField) annotation).value();
                String fieldName = ((ViewModelHintEditTextField) annotation).fieldName();

                PublishSubject fieldPublishSubject = createEditTextHintPublishSubject(resId);

                // Add this view and its corresponding publish subject to actions map
                viewModelFieldsActions.put(fieldName, fieldPublishSubject);

            }
        };
    }

    /**
     * create Image view annotation consumer
     *
     * @param field the field of annotation
     * @return the consumer of annotation
     */
    private Consumer<Annotation> consumeImageViewAnnotation(final Field field) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                int resId = ((ViewModelImageViewField) annotation).value();
                String fieldName = ((ViewModelImageViewField) annotation).fieldName();

                PublishSubject fieldPublishSubject = createImageViewPublishSubject(resId);

                // Add this view and its corresponding publish subject to actions map
                viewModelFieldsActions.put(fieldName, fieldPublishSubject);

            }
        };
    }

    /**
     * create View visibility annotation consumer
     *
     * @param field the field of annotation
     * @return the consumer of annotation
     */
    private Consumer<Annotation> consumeViewVisibilityAnnotation(final Field field) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                int resId = ((ViewModelViewVisibilityField) annotation).value();
                String fieldName = ((ViewModelViewVisibilityField) annotation).fieldName();

                PublishSubject fieldPublishSubject = createViewVisibilityPublishSubject(resId);

                // Add this view and its corresponding publish subject to actions map
                viewModelFieldsActions.put(fieldName, fieldPublishSubject);

            }
        };
    }

    /**
     * Associate the text view annotation with the actual action to set text to view
     *
     * @param resId the view resource Id
     */
    private PublishSubject<String> createTextViewPublishSubject(final int resId) {
        PublishSubject<String> fieldPublishSubject = PublishSubject.create();

        fieldPublishSubject.subscribe(new Consumer<String>()
        {
            @Override
            public void accept(String textVal) throws Exception {
                TextView textView = (TextView) baseView.findViewById(resId);
                textView.setText(textVal);
            }
        });

        return fieldPublishSubject;

    }

    /**
     * Associate the text view color annotation with the actual action to set text color to view
     *
     * @param resId the view resource Id
     */
    private PublishSubject<Integer> createTextViewTextColorPublishSubject(final int resId) {
        PublishSubject<Integer> fieldPublishSubject = PublishSubject.create();

        fieldPublishSubject.subscribe(new Consumer<Integer>()
        {
            @Override
            public void accept(Integer textColorVal) throws Exception {
                TextView textView = (TextView) baseView.findViewById(resId);
                textView.setTextColor(textColorVal);
            }
        });

        return fieldPublishSubject;

    }

    /**
     * Associate the Checkbox annotation with the actual action to set checkbox value to view
     *
     * @param resId the view resource Id
     */
    private PublishSubject<Boolean> createCheckBoxPublishSubject(final int resId) {
        PublishSubject<Boolean> fieldPublishSubject = PublishSubject.create();

        fieldPublishSubject.subscribe(new Consumer<Boolean>()
        {
            @Override
            public void accept(Boolean checkVal) throws Exception {
                CheckBox checkBox = (CheckBox) baseView.findViewById(resId);
                checkBox.setChecked(checkVal);
            }
        });

        return fieldPublishSubject;

    }

    /**
     * Associate the editText hint annotation with the actual action to set hint value to edit text
     *
     * @param resId the view resource Id
     */
    private PublishSubject<String> createEditTextHintPublishSubject(final int resId) {
        PublishSubject<String> fieldPublishSubject = PublishSubject.create();

        fieldPublishSubject.subscribe(new Consumer<String>()
        {
            @Override
            public void accept(String hintText) throws Exception {
                EditText editText = (EditText) baseView.findViewById(resId);
                editText.setText(hintText);
            }
        });

        return fieldPublishSubject;

    }

    /**
     * Associate the view visibility annotation with the actual action to set visbility
     *
     * @param resId the view resource Id
     */
    private PublishSubject<Integer> createViewVisibilityPublishSubject(final int resId) {
        PublishSubject<Integer> fieldPublishSubject = PublishSubject.create();

        fieldPublishSubject.subscribe(new Consumer<Integer>()
        {
            @Override
            public void accept(Integer visibility) throws Exception {
                View view = baseView.findViewById(resId);
                view.setVisibility(visibility);
            }
        });

        return fieldPublishSubject;

    }

    /**
     * Associate the view image annotation
     *
     * @param resId the view resource Id
     */
    private PublishSubject<Bitmap> createImageViewPublishSubject(final int resId) {
        PublishSubject<Bitmap> fieldPublishSubject = PublishSubject.create();

        fieldPublishSubject.subscribe(new Consumer<Bitmap>()
        {
            @Override
            public void accept(Bitmap bitmap) throws Exception {
                ImageView imageView = (ImageView) baseView.findViewById(resId);
                imageView.setImageBitmap(bitmap);
            }
        });

        return fieldPublishSubject;

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


    private V baseView;

    private HashMap<String, PublishSubject> viewModelFieldsActions = new HashMap<>();
}
