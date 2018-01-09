package foureg.mvvmframework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.foureg.baseframework.annotations.viewmodelfields.ViewModelTextField;
import com.foureg.baseframework.viewmodel.BaseViewModel;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.functions.Consumer;

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

        // set fragment to activity
        FragmentTransaction transaction = getView().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_fragment_placeholder, new MainFragment());
        transaction.commit();

        RxView.clicks(getView().changeTextBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(Object o) throws Exception {
                        setValue("activityTextViewTextVal", "New Text after Btn click");
                        Log.w(Constants.LOG_TAG, "MainActivityViewModel::activityTextViewTextVal = " + activityTextViewTextVal);
                    }
                });
    }

    @ViewModelTextField(R.id.activity_text_view)
    private String activityTextViewTextVal;
}
