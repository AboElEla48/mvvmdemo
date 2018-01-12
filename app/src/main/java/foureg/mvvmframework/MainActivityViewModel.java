package foureg.mvvmframework;

import com.foureg.baseframework.annotations.DataModel;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelCheckBoxField;
import com.foureg.baseframework.annotations.viewmodelfields.ViewModelHintEditTextField;
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
    @ViewModelTextField(value = R.id.activity_text_view, fieldName = "textVal")
    private Property<String> textVal = new Property<>();

    @ViewModelTextViewTextColorField(value = R.id.activity_text_view, fieldName = "textColor")
    private Property<Integer> textColor = new Property<>();

    @ViewModelViewVisibilityField(value = R.id.activity_visibility_view, fieldName = "visibilityViewVal")
    private Property<Integer> visibilityViewVal = new Property<>();

    @ViewModelCheckBoxField(value = R.id.activity_check_box_view, fieldName = "CheckBoxView")
    private Property<Boolean> checkBoxVal = new Property<>();

    @ViewModelHintEditTextField(value = R.id.activity_edit_view, fieldName = "EditorView")
    private Property<String> editorHintVal = new Property<>();

    @DataModel
    foureg.mvvmframework.data.DataModel dataModel;


    Property<String> getTextVal() {
        return textVal;
    }

    Property<Integer> getTextColor() { return textColor; }

    Property<Integer> getVisibilityViewVal() {
        return visibilityViewVal;
    }

    Property<Boolean> getCheckBoxVal() { return checkBoxVal; }

    Property<String> getEditorHintVal() { return editorHintVal; }
}
