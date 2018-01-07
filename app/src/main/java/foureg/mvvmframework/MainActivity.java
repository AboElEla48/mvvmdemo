package foureg.mvvmframework;

import com.foureg.baseframework.annotations.ContentViewID;
import com.foureg.baseframework.annotations.ViewModel;
import com.foureg.baseframework.ui.BaseActivity;

@ContentViewID(R.layout.activity_main)
public class MainActivity extends BaseActivity
{
    @ViewModel
    MainActivityViewModel viewMode;
}
