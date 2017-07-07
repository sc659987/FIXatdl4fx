package org.atdl4j.fixatdl.ui.fx8.element;

import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.converter.TypeConverterRepo;
import org.atdl4j.fixatdl.model.core.EnumPairT;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.layout.CheckBoxT;
import org.atdl4j.fixatdl.ui.common.element.FixCheckBoxUiElement;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.CheckBox;

import java.util.Collections;
import java.util.List;

public class FxFixCheckBoxUiElement implements FixCheckBoxUiElement<CheckBox, String> {

    private CheckBoxT checkBoxT;
    private CheckBox checkBox;
    private ParameterT parameterT;

    private ObjectProperty<String> controlIdEmitter = new SimpleObjectProperty<>();

    private TypeConverter<?, ?> controlTTypeConverter;

    @Override
    public CheckBox create() {
        if (this.checkBoxT != null) {
            // create java fx control
            this.checkBox = new CheckBox(this.checkBoxT.getLabel());
            // create converter on the basic of parameter type
            this.controlTTypeConverter = TypeConverterRepo.createParameterTypeConverter(parameterT);
            // checkbox is initialized
            initializeControl();
            // value is set in parameter
            setFieldValueToParameter(getCheckBoxParamValue(checkBox.isSelected()), this.parameterT);
            // listen to user interaction with checkbox
            this.checkBox.setOnAction(event -> {
                this.controlIdEmitter.setValue(this.checkBoxT.getID() + ":" + getValue());
                setFieldValueToParameter(getCheckBoxParamValue(checkBox.isSelected()),
                        this.parameterT);
            });
            return this.checkBox;
        }
        return null;
    }

    @Override
    public void initializeControl() {
        if (this.checkBoxT.isInitValue() != null)
            this.checkBox.setSelected(this.checkBoxT.isInitValue());
    }


    /***
     *  If checkBoxT control has 'checkedEnumRef' and 'uncheckedEnumRef' them take enumId from those field
     *  if those field are not present with checkbox then consider "true" and "false" as enumId and
     *  find wire value from parameter's wire value but if no parameter is associated with checkbox then return
     *  null as it does not matter as value will not be saved anywhere.
     * @param isSelected status of checkbox javafx field
     * @return try to find wire value
     */
    private String getCheckBoxParamValue(final Boolean isSelected) {
        String enumId;
        if (this.checkBoxT.getCheckedEnumRef() != null && this.checkBoxT.getUncheckedEnumRef() != null)
            enumId = isSelected ? this.checkBoxT.getCheckedEnumRef() : this.checkBoxT.getUncheckedEnumRef();
        else
            enumId = isSelected.toString();
        return parameterT == null ? null : this.parameterT.getEnumPair()
                .stream()
                .filter(enumPairT -> enumPairT.getEnumID().equals(enumId))
                .findFirst()
                .orElseGet(() -> {
                    EnumPairT enumPairT = new EnumPairT();
                    enumPairT.setWireValue(isSelected.toString());
                    return enumPairT;
                }).getWireValue();
    }


    @Override
    public void setCheckBoxT(CheckBoxT checkBoxT) {
        this.checkBoxT = checkBoxT;
    }

    @Override
    public void setParameters(List<ParameterT> parameterTList) {
        if (parameterTList != null && parameterTList.size() > 0)
            this.parameterT = parameterTList.get(0);
    }

    @Override
    public List<ParameterT> getParameters() {
        List<ParameterT> parameterTS = Collections.emptyList();
        parameterTS.add(this.parameterT);
        return parameterTS;
    }

    @Override
    public ObjectProperty<String> listenChange() {
        return this.controlIdEmitter;
    }

    @Override
    public CheckBoxT getControl() {
        return this.checkBoxT;
    }

    @Override
    public String getValue() {
        return String.valueOf(this.checkBox.isSelected());
    }

    @Override
    public void setValue(String value) {
        this.checkBox.setSelected(isSelectedFromControlValue(value));
        setFieldValueToParameter(getCheckBoxParamValue(isSelectedFromControlValue(value)),
                this.parameterT);
    }

    /***
     * NOT IN USE
     * Returns checkbox value as String either 'true' or 'false' if no
     * checkedEnumRef or uncheckedEnumRef associated with control
     * @param isSelected
     * @return "true" or "false" or 'checkedEnumRef' value or 'uncheckedEnumRef' value
     */
    private String getCheckBoxControlValue(Boolean isSelected) {
        if (this.checkBoxT.getCheckedEnumRef() != null
                && this.checkBoxT.getUncheckedEnumRef() != null) {
            return isSelected ? this.checkBoxT.getCheckedEnumRef() : this.checkBoxT.getUncheckedEnumRef();
        }
        return isSelected.toString();
    }

    /***
     *  When someone tries to set value to checkbox then either it's 'true' or
     * 'false' or enumId mentioned with checkBoxT's checkedEnumRef or uncheckedEnumRef
     * so we have to convert that to boolean true or false.
     * @param value 'true' , 'false' or checkedEnumRef or uncheckedEnumRef
     * @return true or false
     */
    private boolean isSelectedFromControlValue(String value) {
        if (this.checkBoxT.getCheckedEnumRef() != null
                && this.checkBoxT.getUncheckedEnumRef() != null) {
            return value.equals(this.checkBoxT.getCheckedEnumRef());
        }
        return Boolean.parseBoolean(value);
    }

    @Override
    public void makeVisible(boolean visible) {
        this.checkBox.setVisible(visible);
    }

    @Override
    public void makeEnable(boolean enable) {
        this.checkBox.setDisable(!enable);
        if (enable)
            initializeControl();
    }

    @Override
    public TypeConverter<?, ?> getControlTTypeConverter() {
        return this.controlTTypeConverter;
    }
}
