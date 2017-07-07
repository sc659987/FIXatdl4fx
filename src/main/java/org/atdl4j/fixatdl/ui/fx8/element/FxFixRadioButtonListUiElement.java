package org.atdl4j.fixatdl.ui.fx8.element;

import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.converter.TypeConverterRepo;
import org.atdl4j.fixatdl.model.core.EnumPairT;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.layout.PanelOrientationT;
import org.atdl4j.fixatdl.model.layout.RadioButtonListT;
import org.atdl4j.fixatdl.ui.common.element.FixRadioButtonListUiElement;
import org.atdl4j.fixatdl.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FxFixRadioButtonListUiElement implements FixRadioButtonListUiElement<Pane, String> {

    private RadioButtonListT radioButtonListT;
    private GridPane gridPane;
    private List<RadioButton> radioButtonList;
    private ToggleGroup toggleGroup;
    private ParameterT parameterT;

    private ObjectProperty<String> controlIdEmitter = new SimpleObjectProperty<>();

    private TypeConverter<?, ?> controlTTypeConverter;

    @Override
    public Pane create() {
        if (this.radioButtonListT != null) {
            this.gridPane = new GridPane();

            this.controlTTypeConverter = TypeConverterRepo.createParameterTypeConverter(parameterT);

            this.toggleGroup = new ToggleGroup();
            this.gridPane.add(new Label(this.radioButtonListT.getLabel()), 0, 0, 1, 1);
            this.gridPane.setHgap(3);
            this.radioButtonList = this.radioButtonListT.getListItem().stream().map(listItemT -> {
                RadioButton radioButton = new RadioButton();
                radioButton.setText(listItemT.getUiRep());
                radioButton.setId(listItemT.getEnumID());
                radioButton.setToggleGroup(toggleGroup);
                return radioButton;
            }).collect(Collectors.toList());

            // this initializes the control and try to set the value to parameter.
            initializeControl();

            this.radioButtonList.forEach(radioButton ->
                    radioButton.setOnAction(event -> {
                        setFieldValueToParameter(convertEnumItToWireValue(((RadioButton) event.getSource()).getId()), parameterT);
                        this.controlIdEmitter.setValue(this.radioButtonListT.getID() + ":" + getValue());
                    }));

            IntStream.range(0, this.radioButtonList.size()).forEach(index -> {
                if (this.radioButtonListT.getOrientation() == PanelOrientationT.HORIZONTAL) {
                    this.gridPane.add(this.radioButtonList.get(index), 3 * index, 1, 1, 1);
                } else {
                    this.gridPane.add(this.radioButtonList.get(index), 0, index + 1, 1, 1);
                }
            });
            return gridPane;
        }
        return null;
    }

    private String convertEnumItToWireValue(String enumId) {
        return parameterT == null ? null : parameterT.getEnumPair()
                .parallelStream()
                .filter(enumPairT -> enumPairT.getEnumID().equals(enumId))
                .findFirst()
                .orElse(new EnumPairT())
                .getWireValue();
    }

    @Override
    public void initializeControl() {
        if (Utils.isNonEmptyString(this.radioButtonListT.getInitValue()))
            setValue(this.radioButtonListT.getInitValue());
    }

    @Override
    public void setRadioButtonListT(RadioButtonListT radioButtonListT) {
        this.radioButtonListT = radioButtonListT;
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
    public String getValue() {
        return radioButtonList.stream()
                .filter(RadioButton::isSelected)
                .map(RadioButton::getId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void setValue(String enumId) {
        this.radioButtonList.stream()
                .filter(radioButton -> radioButton.getId().equals(enumId))
                .forEach(radioButton -> {
                    radioButton.setSelected(true);

                });
        setFieldValueToParameter(convertEnumItToWireValue(getValue()), parameterT);
    }

    @Override
    public RadioButtonListT getControl() {
        return this.radioButtonListT;
    }

    @Override
    public void makeVisible(boolean visible) {
        this.gridPane.setVisible(visible);


    }

    @Override
    public void makeEnable(boolean enable) {
        this.gridPane.setDisable(!enable);
        if (enable)
            initializeControl();
    }

    @Override
    public TypeConverter<?, ?> getControlTTypeConverter() {
        return this.controlTTypeConverter;
    }
}
