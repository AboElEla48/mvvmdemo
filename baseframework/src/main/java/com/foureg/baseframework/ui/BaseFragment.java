package com.foureg.baseframework.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foureg.baseframework.creators.LifeCycleCreator;
import com.foureg.baseframework.ui.interfaces.FragmentLifeCycle;

/**
 * Created by aboelela on 06/01/18.
 * This should be the base class for all fragments
 */

public class BaseFragment extends Fragment implements FragmentLifeCycle
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return lifeCycleCreator.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        lifeCycleCreator.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lifeCycleCreator = new LifeCycleCreator(this);
        lifeCycleCreator.onCreate(savedInstanceState);

        lifeCycleCreator.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        lifeCycleCreator.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifeCycleCreator.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        lifeCycleCreator.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        lifeCycleCreator.onPause();
    }

    @Override
    public void onStop() {
        lifeCycleCreator.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        lifeCycleCreator.onDestroy();
        super.onDestroy();
    }

    // Object to lifecycle creator
    private LifeCycleCreator lifeCycleCreator;
}
