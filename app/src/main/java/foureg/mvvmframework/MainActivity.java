package foureg.mvvmframework;

import android.os.Bundle;

import com.foureg.baseframework.annotations.ContentViewID;
import com.foureg.baseframework.annotations.ViewModel;
import com.foureg.baseframework.ui.BaseActivity;

@ContentViewID(R.layout.activity_main)
public class MainActivity extends BaseActivity
{
    @ViewModel
    MainActivityViewModel viewMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
