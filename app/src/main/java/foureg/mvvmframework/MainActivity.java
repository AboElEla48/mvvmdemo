package foureg.mvvmframework;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.foureg.baseframework.annotations.ContentViewId;
import com.foureg.baseframework.annotations.ViewId;
import com.foureg.baseframework.annotations.ViewModel;
import com.foureg.baseframework.annotations.ViewPresenter;
import com.foureg.baseframework.ui.BaseActivity;

@ContentViewId(R.layout.activity_main)
public class MainActivity extends BaseActivity<MainActivityViewModel>
{
    @ViewModel
    MainActivityViewModel viewModel;

    @ViewPresenter
    MainActivityPresenter presenter;

    @ViewId(R.id.activity_text_view)
    TextView helloTextView;

    @ViewId(R.id.activity_change_text_value_btn)
    Button changeTextBtn;

    @ViewId(R.id.activity_change_text_color_btn)
    Button changeTextColorBtn;

    @ViewId(R.id.activity_visibility_view)
    View visibilityView;

    @ViewId(R.id.activity_visibility_show_hide_btn)
    Button showHideViewBtn;
}
