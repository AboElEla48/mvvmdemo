package foureg.mvvmframework;

import com.foureg.baseframework.annotations.viewmodelfields.ViewModelTextField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelTextViewTextColorField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelViewVisibilityField;
import com.foureg.baseframework.viewmodel.BaseViewModel;

/**
 * Created by aboelela on 06/01/18.
 * Sample usage of view model
 */

class MainActivityViewModel extends BaseViewModel<MainActivity>
{
    @ViewModelTextField(value = R.id.activity_text_view, fieldName = "activityTextViewTextVal")
    private String activityTextViewTextVal;

    @ViewModelTextViewTextColorField(value = R.id.activity_text_view, fieldName = "activityTextViewTextColorVal")
    private int activityTextViewTextColorVal;

    @ViewModelViewVisibilityField(value = R.id.activity_visibility_view, fieldName = "visibilityViewVal")
    private Integer visibilityViewVal;

    public int getActivityTextViewTextColorVal() {
        return activityTextViewTextColorVal;
    }

    public String getActivityTextViewTextVal() {
        return activityTextViewTextVal;
    }

    public Integer getVisibilityViewVal() {
        return visibilityViewVal;
    }
}
