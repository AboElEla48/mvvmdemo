package com.foureg.baseframework.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.foureg.baseframework.creators.LifeCycleCreator;
import com.foureg.baseframework.ui.interfaces.ActivityLifeCycle;

/**
 * Created by aboelela on 06/01/18.
 * This should be the base class for all activities
 */

public class BaseActivity extends AppCompatActivity implements ActivityLifeCycle
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init the life cycle creator
        lifeCycleCreator = new LifeCycleCreator(this);
        lifeCycleCreator.onCreate(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        lifeCycleCreator.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        lifeCycleCreator.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifeCycleCreator.onStart();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        lifeCycleCreator.onRestart();
    }

    @Override
    public void onPause() {
        super.onPause();
        lifeCycleCreator.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        lifeCycleCreator.onResume();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lifeCycleCreator.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onActivityBackPressed() {
        return lifeCycleCreator.onActivityBackPressed();
    }

    // Object to lifecycle creator
    private LifeCycleCreator lifeCycleCreator;
}
