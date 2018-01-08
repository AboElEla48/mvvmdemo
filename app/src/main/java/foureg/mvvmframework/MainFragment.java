package foureg.mvvmframework;

import android.widget.TextView;

import com.foureg.baseframework.annotations.ContentViewId;
import com.foureg.baseframework.annotations.ViewId;
import com.foureg.baseframework.annotations.ViewModel;
import com.foureg.baseframework.ui.BaseFragment;


/**
 * Sample fragment
 */
@ContentViewId(R.layout.fragment_main)
public class MainFragment extends BaseFragment
{
    @ViewModel
    MainFragmentViewModel viewMode;

    @ViewId(R.id.fragment_text_view)
    TextView fragmentTextView;

//    public MainFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     */
//    public static MainFragment newInstance() {
//        MainFragment fragment = new MainFragment();
//
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false);
//    }


}
