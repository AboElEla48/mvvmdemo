package com.foureg.baseframework.ui;

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
 * Created by aboelela on 10/01/18.
 * The parent class for all presenters
 */

public class BaseViewPresenter<V extends BaseView> implements FragmentLifeCycle, ActivityLifeCycle
{
    /**
     * init presenter
     * @param baseView : the corresponding view
     */
    public void initViewPresenter(V baseView) {
        this.baseView = baseView;
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
