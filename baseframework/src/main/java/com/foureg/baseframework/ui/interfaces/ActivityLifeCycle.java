package com.foureg.baseframework.ui.interfaces;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by AboelelaA on 6/8/2017.
 * This interface for the lifecycle of activity
 */

public interface ActivityLifeCycle extends ViewLifeCycle
{
    void onRestart();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    boolean onActivityBackPressed();

    void onRestoreInstanceState(Bundle savedInstanceState);
}
