package foureg.mvvmframework;

import com.foureg.baseframework.annotations.DataModel;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelTextField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelTextViewTextColorField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelViewVisibilityField;
import com.foureg.baseframework.types.Property;
import com.foureg.baseframework.viewmodel.BaseViewModel;

/**
 * Created by aboelela on 06/01/18.
 * Sample usage of view model
 */

class MainActivityViewModel extends BaseViewModel<MainActivity>
{
    @ViewModelTextField(value = R.id.activity_text_view, fieldName = "activityTextViewTextVal")
    private Property<String> activityTextViewTextVal = new Property<>();

    @ViewModelTextViewTextColorField(value = R.id.activity_text_view, fieldName = "activityTextViewTextColorVal")
    private Property<Integer> activityTextViewTextColorVal = new Property<>();

    @ViewModelViewVisibilityField(value = R.id.activity_visibility_view, fieldName = "visibilityViewVal")
    private Property<Integer> visibilityViewVal = new Property<>();

    @DataModel
    private DataModel dataModel;

    public Property<Integer> getActivityTextViewTextColorVal() {
        return activityTextViewTextColorVal;
    }

    Property<String> getActivityTextViewTextVal() {
        return activityTextViewTextVal;
    }

    Property<Integer> getVisibilityViewVal() {
        return visibilityViewVal;
    }
}
