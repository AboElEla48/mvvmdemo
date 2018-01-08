package foureg.mvvmframework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.foureg.baseframework.viewmodel.BaseViewModel;

/**
 * Created by aboelela on 06/01/18.
 * Sample usage of view model
 */

class MainActivityViewModel extends BaseViewModel<MainActivity>
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(Constants.LOG_TAG, "MainActivityViewModel::onCreate");

        getView().helloTextView.setText("Text From View Model");

        FragmentTransaction transaction = getView().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_fragment_placeholder, new MainFragment());
        transaction.commit();
    }
}
