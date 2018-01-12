package foureg.mvvmframework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

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

                        // get default text currently in text view
                        Log.w(Constants.LOG_TAG, "activityTextViewTextVal = "
                                + getView().getViewModel().getActivityTextViewTextVal().get());

                        // Change text in text view
                        getView().getViewModel().getActivityTextViewTextVal().set(
                                "ViewModel Str + " +  getView().getViewModel().dataModel.getDataStr());

                        // Assure text changed
                        Log.w(Constants.LOG_TAG, "activityTextViewTextVal = "
                                + getView().getViewModel().getActivityTextViewTextVal().get());
                    }
                });

        RxView.clicks(getView().hideViewBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(Object o) throws Exception {
                        getView().getViewModel().getVisibilityViewVal().set(View.GONE);

                        Log.w(Constants.LOG_TAG, "visibilityViewVal = "
                                + getView().getViewModel().getVisibilityViewVal().get());
                    }
                });
    }

}
