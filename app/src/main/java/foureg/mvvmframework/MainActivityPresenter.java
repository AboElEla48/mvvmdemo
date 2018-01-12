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

        // set fragment to activity
        FragmentTransaction transaction = getView().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_fragment_placeholder, new MainFragment());
        transaction.commit();

        // change text view string
        changeTextViewString();

        // Show/Hide annotation
        showHideView();
    }

    private void showHideView() {
        RxView.clicks(getView().showHideViewBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.w(Constants.LOG_TAG, "***********************");
                        Log.w(Constants.LOG_TAG, "visibility Values: ");
                        Log.w(Constants.LOG_TAG, "Visible = " + View.VISIBLE);
                        Log.w(Constants.LOG_TAG, "Gone = " + View.GONE);
                        Log.w(Constants.LOG_TAG, "INVISIBLE = " + View.INVISIBLE);

                        Log.w(Constants.LOG_TAG, "Initial Visibility: " +
                                getView().getViewModel().getVisibilityViewVal().get());

                        int visibility = getView().getViewModel().getVisibilityViewVal().get() ==
                                View.VISIBLE ? View.GONE : View.VISIBLE;
                        getView().getViewModel().getVisibilityViewVal().set(visibility);

                        Log.w(Constants.LOG_TAG, "Visibility after change = "
                                + getView().getViewModel().getVisibilityViewVal().get());
                        Log.w(Constants.LOG_TAG, "***********************");
                    }
                });
    }

    private void changeTextViewString() {
        // Change text view content
        RxView.clicks(getView().changeTextBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(Object o) throws Exception {

                        Log.w(Constants.LOG_TAG, "***********************");

                        // get default text currently in text view
                        Log.w(Constants.LOG_TAG, "Initial value in view mode text field = "
                                + getView().getViewModel().getActivityTextViewTextVal().get());

                        // Change text in text view
                        getView().getViewModel().getActivityTextViewTextVal().set(
                                "ViewModel Str + " + getView().getViewModel().dataModel.getDataStr());

                        // Assure text changed
                        Log.w(Constants.LOG_TAG, "View Model text field after change = "
                                + getView().getViewModel().getActivityTextViewTextVal().get());

                        Log.w(Constants.LOG_TAG, "***********************");
                    }
                });
    }

}
