package com.foureg.baseframework.creators;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foureg.baseframework.annotations.ViewModel;
import com.foureg.baseframework.annotations.ViewPresenter;
import com.foureg.baseframework.exceptions.ErrorInitializingFramework;
import com.foureg.baseframework.scanners.FieldAnnotationTypeScanner;
import com.foureg.baseframework.ui.BaseActivity;
import com.foureg.baseframework.ui.BaseViewPresenter;
import com.foureg.baseframework.ui.interfaces.ActivityLifeCycle;
import com.foureg.baseframework.ui.interfaces.BaseView;
import com.foureg.baseframework.ui.interfaces.FragmentLifeCycle;
import com.foureg.baseframework.ui.interfaces.ViewLifeCycle;
import com.foureg.baseframework.viewmodel.BaseViewModel;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 07/01/18.
 * This should be responsible for passing the lifecycle calls from Activity and Fragment to
 * corresponding ViewModel
 */

public class LifeCycleCreator implements ActivityLifeCycle, FragmentLifeCycle
{
    // Hold object of baseViewModel
    private BaseViewModel baseViewModel;

    // Hold object of the hosted view (Activity/fragment)
    private WeakReference<ViewLifeCycle> hostViews;

    // Hold object to view presenter to pass view calls to it
    private BaseViewPresenter baseViewPresenter;

    /**
     * Initialize the host view
     *
     * @param hostView : the host view of the lifecyclecreator
     */
    public LifeCycleCreator(ViewLifeCycle hostView) {
        hostViews = new WeakReference<>(hostView);
    }

    public BaseViewModel getViewModel() {
        return baseViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Initialize the view model to receive lifecycle calls
        BaseView baseView = hostViews.get();
        if (baseView == null) {
            throw new ErrorInitializingFramework("BaseView is null. Can't initializes life cycle creator"); // stop execution
        }

        if (baseView instanceof BaseActivity) {
            // init fields in base view (Associate vars with its views from xml)
            // At this step the activity is initialized with its XML but fragment not initialized yet
            createFieldsAnnotatedAsViewId(baseView);
        }

        // init view presenter field
        createFieldAnnotatedAsViewPresenter(baseView);

        // init view model field
        createFieldAnnotatedAsViewModel(baseView);

        if (baseViewPresenter != null) {
            // make lifecycle calls
            baseViewPresenter.onCreate(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        BaseView baseView = hostViews.get();
        if (baseView != null) {
            // init fields in base view (Associate vars with its views from xml)
            createFieldsAnnotatedAsViewId(baseView);
        }

        if (baseViewPresenter != null) {
            baseViewPresenter.onCreateView(inflater, container, savedInstanceState);
        }

        return null;
    }

    /**
     * Create fields annotated by ViewId
     *
     * @param baseView : the base view contains the views fields
     */
    private void createFieldsAnnotatedAsViewId(BaseView baseView) {
        // search for fields annotated by viewId and init it with its resource from XML
        ViewFieldsInitializer.initViewsFields(baseView);
    }

    /**
     * Create field annotated as view model in the base view
     *
     * @param baseView : the base view (activity or fragment)
     */
    private void createFieldAnnotatedAsViewModel(final BaseView baseView) {
        // Iterated fields on base view search for fields annotated as ViewModel, then create it

        FieldAnnotationTypeScanner.extractFieldsAnnotatedBy(baseView, ViewModel.class,
                new Consumer<Field>()
                {
                    @Override
                    public void accept(Field viewModelField) throws Exception {
                        // create base view model
                        baseViewModel = (BaseViewModel) FieldTypeCreator.createFieldObject(viewModelField);
                        baseViewModel.initViewModel(baseView);

                    }
                });


    }

    /**
     * Create fields annotated as View Presenter
     *
     * @param baseView : the host view
     */
    private void createFieldAnnotatedAsViewPresenter(final BaseView baseView) {
        // Iterated fields on base view search for fields annotated as ViewModel, then create it

        FieldAnnotationTypeScanner.extractFieldsAnnotatedBy(baseView, ViewPresenter.class,
                new Consumer<Field>()
                {
                    @Override
                    public void accept(Field viewPresenterField) throws Exception {
                        // create base view model
                        baseViewPresenter = (BaseViewPresenter) FieldTypeCreator.createFieldObject(viewPresenterField);
                        if (baseViewPresenter != null) {
                            baseViewPresenter.initViewPresenter(baseView);
                        }

                    }
                });


    }

    @Override
    public void onStart() {
        if (baseViewPresenter != null) {
            baseViewPresenter.onStart();
        }
    }

    @Override
    public void onResume() {
        if (baseViewPresenter != null) {
            baseViewPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        if (baseViewPresenter != null) {
            baseViewPresenter.onPause();
        }
    }

    @Override
    public void onRestart() {
        if (baseViewPresenter != null) {
            baseViewPresenter.onRestart();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (baseViewPresenter != null) {
            baseViewPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onActivityBackPressed() {
        return baseViewPresenter != null && baseViewPresenter.onActivityBackPressed();
    }

    @Override
    public void onStop() {
        if (baseViewPresenter != null) {
            baseViewPresenter.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (baseViewPresenter != null) {
            baseViewPresenter.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (baseViewPresenter != null) {
            baseViewPresenter.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (baseViewPresenter != null) {
            baseViewPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (baseViewPresenter != null) {
            baseViewPresenter.onActivityCreated(savedInstanceState);
        }
    }

    @Override
    public View findViewById(int resId) {
        return null;
    }

}
