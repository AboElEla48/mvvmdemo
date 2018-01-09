package foureg.mvvmframework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.foureg.baseframework.ui.BaseViewPresenter;
import com.jakewharton.rxbinding2.view.RxView;

import foureg.mvvmframework.fragment.MainFragment;
import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 10/01/18.
 * Sample presenter activity
 */

public class MainActivityPresenter extends BaseViewPresenter<MainActivity>
{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(Constants.LOG_TAG, "MainActivityPresenter::onCreate");

        getView().helloTextView.setText("Text From Presenter");

        // set fragment to activity
        FragmentTransaction transaction = getView().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_fragment_placeholder, new MainFragment());
        transaction.commit();

        RxView.clicks(getView().changeTextBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(Object o) throws Exception {
                        getView().getViewModel().setValue("activityTextViewTextVal",
                                "Text from View Model after click");
                        Log.w(Constants.LOG_TAG, "activityTextViewTextVal = "
                                + getView().getViewModel().getActivityTextViewTextVal());
                    }
                });
    }

}
