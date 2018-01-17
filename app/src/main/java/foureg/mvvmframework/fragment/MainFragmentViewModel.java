package foureg.mvvmframework.fragment;

import com.foureg.baseframework.annotations.viewmodelfields.ViewModelTextField;
import com.foureg.baseframework.types.Property;
import com.foureg.baseframework.viewmodel.BaseViewModel;

import foureg.mvvmframework.R;

/**
 * Created by aboelela on 08/01/18.
 * Sample code for fragment view model
 */

class MainFragmentViewModel extends BaseViewModel<MainFragment>
{
    @ViewModelTextField(value = R.id.fragment_text_view, fieldName = "Fragment_text_View")
    Property<String> textViewVal = new Property<>();

    public Property<String> getTextViewVal() { return textViewVal; }
}
