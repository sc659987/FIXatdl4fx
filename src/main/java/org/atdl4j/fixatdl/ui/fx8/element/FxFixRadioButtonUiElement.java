package org.atdl4j.fixatdl.ui.fx8.element;

import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.converter.TypeConverterRepo;
import org.atdl4j.fixatdl.model.core.EnumPairT;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.layout.RadioButtonT;
import org.atdl4j.fixatdl.ui.common.element.FixRadioButtonUiElement;
import org.atdl4j.fixatdl.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FxFixRadioButtonUiElement implements FixRadioButtonUiElement<RadioButton, String> {

    private RadioButtonT radioButtonT;
    private RadioButton radioButton;
    private ParameterT parameterT;
    private static final Map<String, ToggleGroup> TOGGLE_GROUPS = new HashMap<>();
    private ObjectProperty<String> controlIdEmitter = new SimpleObjectProperty<>();

    private TypeConverter<?, ?> controlTTypeConverter;

    @Override
    public RadioButton create() {
        if (this.radioButtonT != null) {
            this.radioButton = new RadioButton(this.radioButtonT.getLabel());

            this.controlTTypeConverter = TypeConverterRepo.createParameterTypeConverter(parameterT);

            this.radioButton.setId(this.radioButtonT.getID());
            if (!Utils.isEmptyString(this.radioButtonT.getRadioGroup())) {
                ToggleGroup toggleGroup = TOGGLE_GROUPS.get(this.radioButtonT.getRadioGroup());
                if (toggleGroup == null) {
                    TOGGLE_GROUPS.put(this.radioButtonT.getRadioGroup(), toggleGroup = new ToggleGroup());
                    toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                        if (oldValue != null)
                            ((RadioButton) oldValue).onActionProperty().get().handle(null);
                    });
                }
                this.radioButton.setToggleGroup(toggleGroup);
            }
            // initialize with init value and mark it as 'false' if no initial value supplied
            initializeControl();
            // set value to parameter
            setFieldValueToParameter(getRadioButtonParamValue(radioButtonT.isInitValue() == null ? false : radioButtonT.isInitValue()), parameterT);

            this.radioButton.setOnAction(event -> {
                setFieldValueToParameter(getRadioButtonParamValue(radioButton.isSelected()), this.parameterT);
                // publish GUI change mainly for control flow
                controlIdEmitter.setValue(radioButtonT.getID() + ":" + getValue());
            });
            return this.radioButton;
        }
        return null;
    }

    /**
     * If Radio Button has paramRef then that param should have at least two
     * EnumPair which matches the value of Radio button's value. so match
     * the each value of the radio button with parameter's enumId and then
     * map to corresponding to wire value
     *
     * @param isSelected
     * @return
     */
    private String getRadioButtonParamValue(final Boolean isSelected) {
        if (this.radioButtonT.getCheckedEnumRef() != null
                && this.radioButtonT.getUncheckedEnumRef() != null
                && this.parameterT != null) {
            String enumId = isSelected ? this.radioButtonT.getCheckedEnumRef() : this.radioButtonT.getUncheckedEnumRef();
            return this.parameterT.getEnumPair()
                    .stream()
                    .filter(enumPairT -> enumPairT.getEnumID().equals(enumId))
                    .findFirst()
                    .orElseGet(() -> {
                        EnumPairT enumPairT = new EnumPairT();
                        enumPairT.setWireValue(isSelected.toString());
                        return enumPairT;
                    }).getWireValue();
        }
        return isSelected.toString();
    }

    /**
     * Radio Button will have two values all ways either 'true' or 'false'.
     * if checkedEnumRef and uncheckedEnumRef present with the control
     * then use the value from there other wise use 'true' or 'false'
     *
     * @param isSelected boolean true or fals
     * @return value of 'checkedEnumRef' or 'uncheckedEnumRef' or 'true' or 'false'
     */
    private String getRadioButtonControlValue(Boolean isSelected) {
        if (this.radioButtonT.getCheckedEnumRef() != null
                && this.radioButtonT.getUncheckedEnumRef() != null) {
            return isSelected ? this.radioButtonT.getCheckedEnumRef() : this.radioButtonT.getUncheckedEnumRef();
        }
        return isSelected.toString();
    }

    /***
     * Radio button can have 'checkedEnumRef' and 'uncheckedEnumRef' or may not have as part of control element in Fix Atdl.
     * In case it does not have any 'checkedEnumRef' and 'uncheckedEnumRef' then consider
     * 'true' or 'false' as value of Radio Button otherwise use value of 'checkedEnumRef' or 'uncheckedEnumRef'.
     *
     * @param value
     * @return
     */
    private boolean isSelectedFromControlValue(String value) {
        if (this.radioButtonT.getCheckedEnumRef() != null
                && this.radioButtonT.getUncheckedEnumRef() != null) {
            return value.equals(this.radioButtonT.getCheckedEnumRef());
        }
        return Boolean.parseBoolean(value);
    }


    @Override
    public void initializeControl() {
        if (this.radioButtonT.isInitValue() != null)
            this.radioButton.setSelected(this.radioButtonT.isInitValue());
        else
            this.radioButton.setSelected(false);
    }

    @Override
    public void setRadioButtonT(RadioButtonT radioButtonT) {
        this.radioButtonT = radioButtonT;
    }

    @Override
    public void setParameters(List<ParameterT> parameterTList) {
        if (parameterTList != null && parameterTList.size() > 0)
            this.parameterT = parameterTList.get(0);
    }

    @Override
    public List<ParameterT> getParameters() {
        return Collections.singletonList(this.parameterT);
    }

    @Override
    public ObjectProperty<String> listenChange() {
        return this.controlIdEmitter;
    }

    @Override
    public RadioButtonT getControl() {
        return this.radioButtonT;
    }

    @Override
    public String getValue() {
        return getRadioButtonControlValue(this.radioButton.isSelected());
    }

    @Override
    public void setValue(String isSelected) {
        this.radioButton.setSelected(isSelectedFromControlValue(isSelected));
        setFieldValueToParameter(getRadioButtonParamValue(isSelectedFromControlValue(isSelected)), this.parameterT);
    }

    @Override
    public void makeVisible(boolean visible) {
        radioButton.setVisible(visible);
    }

    @Override
    public void makeEnable(boolean enable) {
        radioButton.setDisable(!enable);
        if (enable)
            initializeControl();
    }

    @Override
    public TypeConverter<?, ?> getControlTTypeConverter() {
        return this.controlTTypeConverter;
    }
}
