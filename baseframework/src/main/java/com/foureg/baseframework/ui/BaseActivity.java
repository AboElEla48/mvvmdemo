package com.foureg.baseframework.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.foureg.baseframework.creators.LifeCycleCreator;
import com.foureg.baseframework.messages.MessagesActor;
import com.foureg.baseframework.messages.MessagesServer;
import com.foureg.baseframework.messages.data.CustomMessage;
import com.foureg.baseframework.scanners.ContentViewIDScanner;
import com.foureg.baseframework.ui.interfaces.ActivityLifeCycle;
import com.foureg.baseframework.viewmodel.BaseViewModel;

import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 06/01/18.
 * This should be the base class for all activities
 */

public class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity
        implements ActivityLifeCycle, MessagesActor
{
    @Override
    public final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MessagesServer.getInstance().registerActor(this);

        // set content view of activity
        ContentViewIDScanner.extractViewContentID(this, new Consumer<Integer>()
        {
            @Override
            public void accept(Integer resID) throws Exception {
                setContentView(resID);
                initActivity(savedInstanceState);

            }
        });


    }

    /**
     * get the view model associated with view
     *
     * @return : the view model associated with view
     */
    public VM getViewModel() {
        return (VM)lifeCycleCreator.getViewModel();
    }

    private void initActivity(Bundle savedInstanceState) {
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
        MessagesServer.getInstance().unregisterActor(this);
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

    @Override
    public final void onReceiveMessage(int payload, CustomMessage customMessage) {
        lifeCycleCreator.onReceiveMessage(payload, customMessage);
    }

    @Override
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    // Object to lifecycle creator
    private LifeCycleCreator lifeCycleCreator;


}
