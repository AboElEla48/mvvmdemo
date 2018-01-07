package com.foureg.baseframework.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foureg.baseframework.creators.LifeCycleCreator;
import com.foureg.baseframework.scanners.ContentViewIDScanner;
import com.foureg.baseframework.ui.interfaces.FragmentLifeCycle;

/**
 * Created by aboelela on 06/01/18.
 * This should be the base class for all fragments
 */

public class BaseFragment extends Fragment implements FragmentLifeCycle
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifeCycleCreator = new LifeCycleCreator(this);
        lifeCycleCreator.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public final View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        int resId = ContentViewIDScanner.extractViewContentID(this);
        View view = inflater.inflate(resId, container, false);

        lifeCycleCreator.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    public final void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lifeCycleCreator.onSaveInstanceState(outState);
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lifeCycleCreator.onActivityCreated(savedInstanceState);
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        lifeCycleCreator.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public final void onStart() {
        super.onStart();
        lifeCycleCreator.onStart();
    }

    @Override
    public final void onResume() {
        super.onResume();
        lifeCycleCreator.onResume();
    }

    @Override
    public final void onPause() {
        super.onPause();
        lifeCycleCreator.onPause();
    }

    @Override
    public final void onStop() {
        lifeCycleCreator.onStop();
        super.onStop();
    }

    @Override
    public final void onDestroy() {
        lifeCycleCreator.onDestroy();
        super.onDestroy();
    }

    // Object to lifecycle creator
    private LifeCycleCreator lifeCycleCreator;
    private View fragmentView;
}
