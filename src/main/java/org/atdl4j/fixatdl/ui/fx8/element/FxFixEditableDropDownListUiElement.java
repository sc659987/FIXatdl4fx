package org.atdl4j.fixatdl.ui.fx8.element;

import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.converter.TypeConverterRepo;
import org.atdl4j.fixatdl.model.core.EnumPairT;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.layout.EditableDropDownListT;
import org.atdl4j.fixatdl.model.layout.ListItemT;
import org.atdl4j.fixatdl.ui.common.element.FixEditableDropDownListUiElement;
import org.atdl4j.fixatdl.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Collections;
import java.util.List;


public class FxFixEditableDropDownListUiElement
        implements FixEditableDropDownListUiElement<Pane, String> {

    private ComboBox<ListItemT> editableComboBox;
    private EditableDropDownListT editableDropDownListT;
    private GridPane gridPane;

    private int nextColumn = 0;
    private ParameterT parameterT;

    private ObjectProperty<String> controlIdEmitter = new SimpleObjectProperty<>();

    private TypeConverter<?, ?> controlTTypeConverter;

    @Override
    public Pane create() {
        if (this.editableDropDownListT != null) {
            this.gridPane = new GridPane();

            this.controlTTypeConverter = TypeConverterRepo.createParameterTypeConverter(parameterT);

            if (Utils.isNonEmptyString(this.editableDropDownListT.getLabel()))
                this.gridPane.add(new Label(this.editableDropDownListT.getLabel()),
                        this.nextColumn++, 0);
            this.gridPane.setHgap(3);
            this.editableComboBox = new ComboBox<>();
            this.editableComboBox.getItems()
                    .addAll(this.editableDropDownListT
                            .getListItem());

            this.editableComboBox.setEditable(true);

            initializeControl();

            this.editableComboBox.setOnAction(event -> {
                this.controlIdEmitter.set(editableDropDownListT.getID() + ":" + getValue());

                if (this.parameterT != null)
                    setFieldValueToParameter(getParameterValue(editableComboBox.getValue().getEnumID()), this.parameterT);
            });

            this.gridPane.add(this.editableComboBox, nextColumn, 0);
            return this.gridPane;
        }
        return null;
    }


    private String getParameterValue(String enumId) {
        return parameterT == null ? null : parameterT.getEnumPair()
                .stream()
                .filter(enumPairT -> enumPairT.getEnumID().equals(enumId))
                .findFirst().orElseGet(() -> {
                    EnumPairT enumPairT = new EnumPairT();
                    enumPairT.setWireValue(enumId);
                    return enumPairT;
                }).getWireValue();
    }

    @Override
    public void initializeControl() {

        if (Utils.isNonEmptyString(this.editableDropDownListT.getInitValue()))
            setValue(this.editableDropDownListT.getInitValue());
    }

    @Override
    public void setEditableDropDownList(EditableDropDownListT editableDropDownListT) {
        this.editableDropDownListT = editableDropDownListT;
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
    public EditableDropDownListT getControl() {
        return this.editableDropDownListT;
    }

    // TODO test with example and debug
    //TODO no test fix ATDL found hence minor modification required
    @Override
    public String getValue() {
        return this.editableComboBox.getValue().getEnumID();
    }

    @Override
    public void setValue(String enumId) {
        this.editableDropDownListT
                .getListItem()
                .stream()
                .filter(listItemT -> listItemT.getEnumID().equals(enumId))
                .findFirst()
                .ifPresent(listItemT -> {
                    editableComboBox.setValue(listItemT);

                });
        setFieldValueToParameter(getParameterValue(enumId), this.parameterT);
    }

    @Override
    public void makeVisible(boolean visible) {
        editableComboBox.setVisible(visible);
    }

    @Override
    public void makeEnable(boolean enable) {
        this.editableComboBox.setDisable(!enable);
    }

    @Override
    public TypeConverter<?, ?> getControlTTypeConverter() {
        return this.controlTTypeConverter;
    }
}
