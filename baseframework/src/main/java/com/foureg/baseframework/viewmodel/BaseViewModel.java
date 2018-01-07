package com.foureg.baseframework.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foureg.baseframework.ui.interfaces.ActivityLifeCycle;
import com.foureg.baseframework.ui.interfaces.BaseView;
import com.foureg.baseframework.ui.interfaces.FragmentLifeCycle;

/**
 * Created by aboelela on 06/01/18.
 * This should be the base view model of all view models
 */

public class BaseViewModel<V extends BaseView> implements FragmentLifeCycle, ActivityLifeCycle
{
    /**
     * init View Model after creation
     * @param baseView : the baseView associated with this view model
     */
    public void initViewModel(V baseView) {
        this.baseView = baseView;
    }

    /**
     * Get the baseView for further usage
     * @return The base view associated with this view model
     */
    public V getView() {
        return baseView;
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
}
