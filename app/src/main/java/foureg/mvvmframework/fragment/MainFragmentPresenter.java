package foureg.mvvmframework.fragment;

import android.util.Log;

import com.foureg.baseframework.ui.BaseViewPresenter;
import com.jakewharton.rxbinding2.view.RxView;

import foureg.mvvmframework.Constants;
import io.reactivex.functions.Consumer;

/**
 * Created by aboelela on 10/01/18.
 * Sample presenter for fragment
 */

public class MainFragmentPresenter extends BaseViewPresenter<MainFragment>
{
    @Override
    public void initFragmentValues() {
        super.initFragmentValues();

        // Change text value
        changeTextViewVal();
    }

    private void changeTextViewVal() {
        RxView.clicks(getView().changeTextBtn)
                .subscribe(new Consumer<Object>()
                {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.w(Constants.LOG_TAG, "***********************");
                        Log.w(Constants.LOG_TAG, "Initial value of text view = "
                                + getView().getViewModel().getTextViewVal().get());

                        getView().getViewModel().getTextViewVal().set("New Fragment Text value");

                        Log.w(Constants.LOG_TAG, "Text view after change= "
                                + getView().getViewModel().getTextViewVal().get());
                        Log.w(Constants.LOG_TAG, "***********************");
                    }
                });
    }
}
