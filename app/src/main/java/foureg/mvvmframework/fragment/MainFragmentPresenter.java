package foureg.mvvmframework.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foureg.baseframework.ui.BaseViewPresenter;

/**
 * Created by aboelela on 10/01/18.
 * Sample presenter for fragment
 */

public class MainFragmentPresenter extends BaseViewPresenter<MainFragment>
{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        return v;
    }
}
