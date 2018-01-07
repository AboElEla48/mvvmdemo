package com.foureg.baseframework.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.foureg.baseframework.creators.LifeCycleCreator;
import com.foureg.baseframework.scanners.ContentViewIDScanner;
import com.foureg.baseframework.ui.interfaces.ActivityLifeCycle;

import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 06/01/18.
 * This should be the base class for all activities
 */

public class BaseActivity extends AppCompatActivity implements ActivityLifeCycle
{
    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set content view of activity
        ContentViewIDScanner.extractViewContentID(this, new Consumer<Integer>()
        {
            @Override
            public void accept(Integer resID) throws Exception {
                setContentView(resID);
            }
        });


        // Init the life cycle creator
        lifeCycleCreator = new LifeCycleCreator(this);
        lifeCycleCreator.onCreate(savedInstanceState);
    }

    @Override
    public final void onRestoreInstanceState(Bundle savedInstanceState) {
        lifeCycleCreator.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public final void onSaveInstanceState(Bundle outState) {
        lifeCycleCreator.onSaveInstanceState(outState);
    }

    @Override
    public final void onStart() {
        super.onStart();
        lifeCycleCreator.onStart();
    }

    @Override
    public final void onRestart() {
        super.onRestart();
        lifeCycleCreator.onRestart();
    }

    @Override
    public final void onPause() {
        super.onPause();
        lifeCycleCreator.onPause();
    }

    @Override
    public final void onResume() {
        super.onResume();
        lifeCycleCreator.onResume();
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

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lifeCycleCreator.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public final boolean onActivityBackPressed() {
        return lifeCycleCreator.onActivityBackPressed();
    }

    // Object to lifecycle creator
    private LifeCycleCreator lifeCycleCreator;
}
