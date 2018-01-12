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

        // Change text color in text view
        changeTextColor();

        // Show/Hide annotation
        showHideView();

        // Check/Uncheck checkbox
        ChangeCheckBoxVal();

        // Editor Hint text value
        changeEditorHintVal();
    }

    private void changeEditorHintVal() {
        RxView.clicks(getView().changeEditorHintBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.w(Constants.LOG_TAG, "***********************");
                        Log.w(Constants.LOG_TAG, "Initial Hint text value = "
                                + getView().getViewModel().getEditorHintVal().get());

                        getView().getViewModel().getEditorHintVal().set("New Hint Text");

                        Log.w(Constants.LOG_TAG, "Hint text value after change = "
                                + getView().getViewModel().getEditorHintVal().get());
                        Log.w(Constants.LOG_TAG, "***********************");
                    }
                });
    }

    private void ChangeCheckBoxVal() {
        RxView.clicks(getView().changeCheckBoxBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.w(Constants.LOG_TAG, "***********************");
                        Log.w(Constants.LOG_TAG, "Initial Check Box val = "
                                + getView().getViewModel().getCheckBoxVal().get());

                        Boolean isChecked = !getView().getViewModel().getCheckBoxVal().get();
                        getView().getViewModel().getCheckBoxVal().set(isChecked);

                        Log.w(Constants.LOG_TAG, "CheckBox value after change = "
                                + getView().getViewModel().getCheckBoxVal().get());
                        Log.w(Constants.LOG_TAG, "***********************");
                    }
                });
    }

    private void changeTextColor() {
        RxView.clicks(getView().changeTextColorBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.w(Constants.LOG_TAG, "***********************");

                        Log.w(Constants.LOG_TAG, "Initial Text Color: "
                                + getView().getViewModel().getTextColor().get());

                        getView().getViewModel().getTextColor().set(0xFFFF0000);

                        Log.w(Constants.LOG_TAG, "Text Color after change: "
                                + getView().getViewModel().getTextColor().get());

                        Log.w(Constants.LOG_TAG, "***********************");
                    }
                });
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
                                + getView().getViewModel().getTextVal().get());

                        // Change text in text view
                        getView().getViewModel().getTextVal().set(
                                "ViewModel Str + " + getView().getViewModel().dataModel.getDataStr());

                        // Assure text changed
                        Log.w(Constants.LOG_TAG, "View Model text field after change = "
                                + getView().getViewModel().getTextVal().get());

                        Log.w(Constants.LOG_TAG, "***********************");
                    }
                });
    }

}
