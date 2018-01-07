package com.foureg.baseframework.ui.interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by AboelelaA on 6/6/2017.
 *
 * This interface for mapping the needed life cycle for activities and fragments
 */

public interface ViewLifeCycle extends BaseView
{
    void onCreate(@Nullable Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onSaveInstanceState(Bundle outState);
}
