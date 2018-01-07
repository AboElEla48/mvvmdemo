package com.foureg.baseframework.creators;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foureg.baseframework.annotations.ViewModel;
import com.foureg.baseframework.exceptions.ErrorInitializingFramework;
import com.foureg.baseframework.scanners.FieldAnnotationTypeScanner;
import com.foureg.baseframework.ui.interfaces.ActivityLifeCycle;
import com.foureg.baseframework.ui.interfaces.BaseView;
import com.foureg.baseframework.ui.interfaces.FragmentLifeCycle;
import com.foureg.baseframework.ui.interfaces.ViewLifeCycle;
import com.foureg.baseframework.viewmodel.BaseViewModel;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by aboelela on 07/01/18.
 * This should be responsible for passing the lifecycle calls from Activity and Fragment to
 * corresponding ViewModel
 */

public class LifeCycleCreator implements ActivityLifeCycle, FragmentLifeCycle
{
    // Hold object of baseViewModel to pass all calls of lifecycle to it
    private BaseViewModel baseViewModel;

    // Hold object of the hosted view (Activity/fragment)
    private WeakReference<ViewLifeCycle> hostViews;

    /**
     * Initialize the host view
     * @param hostView : the host view of the lifecyclecreator
     */
    public LifeCycleCreator(ViewLifeCycle hostView) {
        hostViews = new WeakReference<>(hostView);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Initialize the view model to receive lifecycle calls
        BaseView baseView = hostViews.get();
        if(baseView == null) {
            throw new ErrorInitializingFramework("BaseView is null. Can't initializes life cycle creator"); // stop execution
        }

        // init view model field
        createFieldAnnotatedAsViewModel(baseView);
    }

    /**
     * Create field annotated as view model in the base view
     * @param baseView : the base view (activity or fragment)
     */
    private void createFieldAnnotatedAsViewModel(BaseView baseView) {
        // Iterated fields on base view search for fields annotated as ViewModel, then create it
        ArrayList<Field> viewModelFields = new ArrayList<>();
        FieldAnnotationTypeScanner.extractFieldsAnnotatedBy(baseView, ViewModel.class, viewModelFields);

        // create base view model
        if(viewModelFields.size() > 0) {
            baseViewModel = (BaseViewModel) FieldTypeCreator.createFieldObject(viewModelFields.get(0));
        }
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
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

    }
}
