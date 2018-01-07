package foureg.mvvmframework;

import android.widget.TextView;

import com.foureg.baseframework.annotations.ContentViewId;
import com.foureg.baseframework.annotations.ViewId;
import com.foureg.baseframework.annotations.ViewModel;
import com.foureg.baseframework.ui.BaseActivity;

@ContentViewId(R.layout.activity_main)
public class MainActivity extends BaseActivity
{
    @ViewModel
    MainActivityViewModel viewMode;

    @ViewId(R.id.activity_text_view)
    TextView helloTextView;
}
