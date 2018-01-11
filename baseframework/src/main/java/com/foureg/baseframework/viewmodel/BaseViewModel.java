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
import com.foureg.baseframework.types.Property;
import com.foureg.baseframework.ui.interfaces.BaseView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

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
            return createObservableForTextViewAnnotation(field);
        } else if (annotationType.getName().equals(ViewModelTextViewTextColorField.class.getName())) {
            return createObservableForTextViewColorAnnotation(field);
        } else if (annotationType.getName().equals(ViewModelCheckBoxField.class.getName())) {
            return createObservableForCheckBoxAnnotation(field);
        } else if (annotationType.getName().equals(ViewModelHintEditTextField.class.getName())) {
            return createObservableForEditTextHintAnnotation(field);
        } else if (annotationType.getName().equals(ViewModelImageViewField.class.getName())) {
            return createObservableForImageViewAnnotation(field);
        } else if (annotationType.getName().equals(ViewModelViewVisibilityField.class.getName())) {
            return createObservableForViewVisibilityAnnotation(field);
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
     * @return the consumer of annotation
     */
    private Consumer<Annotation> createObservableForTextViewAnnotation(final Field field) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                int resId = ((ViewModelTextField) annotation).value();
                String fieldName = ((ViewModelTextField) annotation).fieldName();

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                ((Property<String>) field.get(BaseViewModel.this)).asObservable(consumeTextViewAction(resId));
                field.setAccessible(isAccessible);
            }
        };
    }

    /**
     * create Text View Color annotation consumer
     *
     * @return the consumer of annotation
     */
    private Consumer<Annotation> createObservableForTextViewColorAnnotation(final Field field) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                int resId = ((ViewModelTextViewTextColorField) annotation).value();
                String fieldName = ((ViewModelTextViewTextColorField) annotation).fieldName();

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                ((Property<Integer>) field.get(BaseViewModel.this)).asObservable(consumeTextViewColorAction(resId));
                field.setAccessible(isAccessible);

            }
        };
    }

    /**
     * create CheckBox annotation consumer
     *
     * @return the consumer of annotation
     */
    private Consumer<Annotation> createObservableForCheckBoxAnnotation(final Field field) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                int resId = ((ViewModelCheckBoxField) annotation).value();
                String fieldName = ((ViewModelCheckBoxField) annotation).fieldName();

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                ((Property<Boolean>) field.get(BaseViewModel.this)).asObservable(consumeCheckBoxAction(resId));
                field.setAccessible(isAccessible);
            }
        };
    }

    /**
     * create Edit Text Hint text annotation consumer
     *
     * @return the consumer of annotation
     */
    private Consumer<Annotation> createObservableForEditTextHintAnnotation(final Field field) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                int resId = ((ViewModelHintEditTextField) annotation).value();
                String fieldName = ((ViewModelHintEditTextField) annotation).fieldName();

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                ((Property<String>) field.get(BaseViewModel.this)).asObservable(consumeEditTextHintAction(resId));
                field.setAccessible(isAccessible);
            }
        };
    }

    /**
     * create Image view annotation consumer
     *
     * @return the consumer of annotation
     */
    private Consumer<Annotation> createObservableForImageViewAnnotation(final Field field) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                int resId = ((ViewModelImageViewField) annotation).value();
                String fieldName = ((ViewModelImageViewField) annotation).fieldName();

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                ((Property<Bitmap>) field.get(BaseViewModel.this)).asObservable(consumeImageViewAction(resId));
                field.setAccessible(isAccessible);

            }
        };
    }

    /**
     * create View visibility annotation consumer
     *
     * @return the consumer of annotation
     */
    private Consumer<Annotation> createObservableForViewVisibilityAnnotation(final Field field) {
        return new Consumer<Annotation>()
        {
            @Override
            public void accept(Annotation annotation) throws Exception {
                int resId = ((ViewModelViewVisibilityField) annotation).value();
                String fieldName = ((ViewModelViewVisibilityField) annotation).fieldName();

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                ((Property<Integer>) field.get(BaseViewModel.this)).asObservable(consumeViewVisibilityAction(resId));
                field.setAccessible(isAccessible);
            }
        };
    }

    /**
     * Associate the text view annotation with the actual action to set text to view
     *
     * @param resId the view resource Id
     */
    private Consumer<String> consumeTextViewAction(final int resId) {
        return new Consumer<String>()
        {
            @Override
            public void accept(String textVal) throws Exception {
                TextView textView = (TextView) baseView.findViewById(resId);
                textView.setText(textVal);
            }
        };

    }

    /**
     * Associate the text view color annotation with the actual action to set text color to view
     *
     * @param resId the view resource Id
     */
    private Consumer<Integer> consumeTextViewColorAction(final int resId) {
        return new Consumer<Integer>()
        {
            @Override
            public void accept(Integer textColorVal) throws Exception {
                TextView textView = (TextView) baseView.findViewById(resId);
                textView.setTextColor(textColorVal);
            }
        };

    }

    /**
     * Associate the Checkbox annotation with the actual action to set checkbox value to view
     *
     * @param resId the view resource Id
     */
    private Consumer<Boolean> consumeCheckBoxAction(final int resId) {
        return new Consumer<Boolean>()
        {
            @Override
            public void accept(Boolean checkVal) throws Exception {
                CheckBox checkBox = (CheckBox) baseView.findViewById(resId);
                checkBox.setChecked(checkVal);
            }
        };


    }

    /**
     * Associate the editText hint annotation with the actual action to set hint value to edit text
     *
     * @param resId the view resource Id
     */
    private Consumer<String> consumeEditTextHintAction(final int resId) {
        return new Consumer<String>()
        {
            @Override
            public void accept(String hintText) throws Exception {
                EditText editText = (EditText) baseView.findViewById(resId);
                editText.setText(hintText);
            }
        };
    }

    /**
     * Associate the view visibility annotation with the actual action to set visbility
     *
     * @param resId the view resource Id
     */
    private Consumer<Integer> consumeViewVisibilityAction(final int resId) {
        return new Consumer<Integer>()
        {
            @Override
            public void accept(Integer visibility) throws Exception {
                View view = baseView.findViewById(resId);
                view.setVisibility(visibility);
            }
        };

    }

    /**
     * Associate the view image annotation
     *
     * @param resId the view resource Id
     */
    private Consumer<Bitmap> consumeImageViewAction(final int resId) {
        return new Consumer<Bitmap>()
        {
            @Override
            public void accept(Bitmap bitmap) throws Exception {
                ImageView imageView = (ImageView) baseView.findViewById(resId);
                imageView.setImageBitmap(bitmap);
            }
        };
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

}
