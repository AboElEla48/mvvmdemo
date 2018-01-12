package com.foureg.baseframework.viewmodel;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.foureg.baseframework.annotations.DataModel;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelCheckBoxField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelHintEditTextField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelImageViewField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelTextField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelTextViewTextColorField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelViewVisibilityField;
import com.foureg.baseframework.creators.FieldTypeCreator;
import com.foureg.baseframework.exceptions.ErrorInitializingFramework;
import com.foureg.baseframework.model.BaseDataModel;
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

public class BaseViewModel<V extends BaseView>
{
    /**
     * init View Model after creation
     *
     * @param baseView : the baseView associated with this view model
     */
    public void initViewModel(V baseView) {
        this.baseView = baseView;

        createFieldsAnnotatedAsDataModels();

        initViewModelFieldsAnnotatedAsCheckBoxField();
        initViewModelFieldsAnnotatedAsTextViewField();
        initViewModelFieldsAnnotatedAsTextViewTextColorField();
        initViewModelFieldsAnnotatedAsHintEditTextField();
        initViewModelFieldsAnnotatedAsImageViewField();
        initViewModelFieldsAnnotatedAsViewVisibilityField();
    }

    void createFieldsAnnotatedAsDataModels() {
        FieldAnnotationTypeScanner.extractFieldsAnnotatedBy(this,
                DataModel.class,
                new Consumer<Field>()
                {
                    @Override
                    public void accept(Field field) throws Exception {
                        // Create this field
                        BaseDataModel baseDataModel = (BaseDataModel) FieldTypeCreator.createFieldObject(field);

                        boolean isAccessible = field.isAccessible();
                        field.setAccessible(true);
                        field.set(BaseViewModel.this, baseDataModel);
                        field.setAccessible(isAccessible);
                    }
                });
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

                // get the associated view
                TextView view = (TextView) baseView.findViewById(resId);

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);

                // init view model field with value in the view
                Property<String> viewModelProperty = ((Property<String>) field.get(BaseViewModel.this));
                viewModelProperty.set(view.getText().toString());

                // associate property with view
                viewModelProperty.asObservable(consumeTextViewAction(view));
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

                // get the associated view
                TextView view = (TextView) baseView.findViewById(resId);

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);

                // init view model field with value in the view
                Property<Integer> viewModelProperty = ((Property<Integer>) field.get(BaseViewModel.this));
                viewModelProperty.set(view.getCurrentTextColor());

                // associate property with view
                viewModelProperty.asObservable(consumeTextViewColorAction(view));
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

                // get the associated view
                CheckBox view = (CheckBox) baseView.findViewById(resId);

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);

                // init view model field with value in the view
                Property<Boolean> viewModelProperty = ((Property<Boolean>) field.get(BaseViewModel.this));
                viewModelProperty.set(view.isChecked());

                // associate property with view
                viewModelProperty.asObservable(consumeCheckBoxAction(view));
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

                // get the associated view
                EditText view = (EditText) baseView.findViewById(resId);

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);

                // init view model field with value in the view
                Property<String> viewModelProperty = ((Property<String>) field.get(BaseViewModel.this));
                viewModelProperty.set(view.getHint().toString());

                // associate property with view
                viewModelProperty.asObservable(consumeEditTextHintAction(view));
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

                // get the associated view
                ImageView view = (ImageView) baseView.findViewById(resId);

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);

                // init view model field with value in the view
                Property<Bitmap> viewModelProperty = ((Property<Bitmap>) field.get(BaseViewModel.this));
                viewModelProperty.set(((BitmapDrawable) view.getDrawable()).getBitmap());

                // associate property with view
                viewModelProperty.asObservable(consumeImageViewAction(view));
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

                // get the associated view
                View view = baseView.findViewById(resId);

                // associate property with view
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);

                // init view model field with value in the view
                Property<Integer> viewModelProperty = ((Property<Integer>) field.get(BaseViewModel.this));
                viewModelProperty.set(view.getVisibility());

                ((Property<Integer>) field.get(BaseViewModel.this)).asObservable(
                        consumeViewVisibilityAction(view));
                field.setAccessible(isAccessible);
            }
        };
    }

    /**
     * Associate the text view annotation with the actual action to set text to view
     *
     * @param textView the text view to change its text
     */
    private Consumer<String> consumeTextViewAction(final TextView textView) {
        return new Consumer<String>()
        {
            @Override
            public void accept(String textVal) throws Exception {
                textView.setText(textVal);
            }
        };

    }

    /**
     * Associate the text view color annotation with the actual action to set text color to view
     *
     * @param textView the view to change its color
     */
    private Consumer<Integer> consumeTextViewColorAction(final TextView textView) {
        return new Consumer<Integer>()
        {
            @Override
            public void accept(Integer textColorVal) throws Exception {
                textView.setTextColor(textColorVal);
            }
        };

    }

    /**
     * Associate the Checkbox annotation with the actual action to set checkbox value to view
     *
     * @param checkBox the check box to change its checking status
     */
    private Consumer<Boolean> consumeCheckBoxAction(final CheckBox checkBox) {
        return new Consumer<Boolean>()
        {
            @Override
            public void accept(Boolean checkVal) throws Exception {
                checkBox.setChecked(checkVal);
            }
        };


    }

    /**
     * Associate the editText hint annotation with the actual action to set hint value to edit text
     *
     * @param editText the editor to change its hint text
     */
    private Consumer<String> consumeEditTextHintAction(final EditText editText) {
        return new Consumer<String>()
        {
            @Override
            public void accept(String hintText) throws Exception {
                editText.setText(hintText);
            }
        };
    }

    /**
     * Associate the view visibility annotation with the actual action to set visbility
     *
     * @param view the view to change its visibility
     */
    private Consumer<Integer> consumeViewVisibilityAction(final View view) {
        return new Consumer<Integer>()
        {
            @Override
            public void accept(Integer visibility) throws Exception {
                view.setVisibility(visibility);
            }
        };

    }

    /**
     * Associate the view image annotation
     *
     * @param imageView the image view to set its bitmap
     */
    private Consumer<Bitmap> consumeImageViewAction(final ImageView imageView) {
        return new Consumer<Bitmap>()
        {
            @Override
            public void accept(Bitmap bitmap) throws Exception {
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
