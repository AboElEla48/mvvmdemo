package foureg.mvvmframework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foureg.baseframework.viewmodel.BaseViewModel;

/**
 * Created by aboelela on 08/01/18.
 * Sample code for fragment view model
 */

class MainFragmentViewModel extends BaseViewModel<MainFragment>
{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        getView().fragmentTextView.setText("Text From Fragment View Model");

        return v;
    }
}
