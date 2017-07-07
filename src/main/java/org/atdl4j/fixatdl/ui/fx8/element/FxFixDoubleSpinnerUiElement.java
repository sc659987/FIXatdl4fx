package org.atdl4j.fixatdl.ui.fx8.element;

import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.converter.TypeConverterRepo;
import org.atdl4j.fixatdl.model.core.IntT;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.layout.DoubleSpinnerT;
import org.atdl4j.fixatdl.ui.common.element.FixDoubleSpinnerUiElement;
import org.atdl4j.fixatdl.ui.fx8.customelement.doublespinner.DoubleSpinner;
import org.atdl4j.fixatdl.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

public class FxFixDoubleSpinnerUiElement implements FixDoubleSpinnerUiElement<Pane, Double> {

    private DoubleSpinner doubleSpinner;
    private DoubleSpinnerT doubleSpinnerT;

    private GridPane gridPane;

    private ParameterT parameterT;

    private int nextColumn = 0;

    private ObjectProperty<String> controlIdEmitter = new SimpleObjectProperty<>();

    private Pair<Double, Double> limit;

    private TypeConverter<?, ?> controlTTypeConverter;

    private Pair<Double, Double> extractRangeFromParameter() {
        return parameterT != null && (parameterT instanceof IntT)
                ? new Pair<>((((IntT) parameterT).getMinValue() == null ? 0.0 : ((IntT) parameterT).getMinValue()),
                (((IntT) parameterT).getMaxValue() == null ? Double.MAX_VALUE : ((IntT) parameterT).getMaxValue()))
                : new Pair<>(0.0, Double.MAX_VALUE);
    }

    @Override
    public Pane create() {
        if (this.doubleSpinnerT != null) {
            this.gridPane = new GridPane();

            controlTTypeConverter = TypeConverterRepo.createParameterTypeConverter(parameterT);

            if (Utils.isNonEmptyString(this.doubleSpinnerT.getLabel())) {
                this.gridPane.add(new Label(this.doubleSpinnerT.getLabel()),
                        this.nextColumn++, 0);
            }
            this.gridPane.setHgap(3);
            this.limit = extractRangeFromParameter();
            this.doubleSpinner = new DoubleSpinner(limit.getKey(), limit.getValue(), limit.getKey(),
                    doubleSpinnerT.getInnerIncrement(), doubleSpinnerT.getOuterIncrement() == null ? doubleSpinnerT.getInnerIncrement() : doubleSpinnerT.getOuterIncrement());
            // init field with value
            initializeControl();
            // set value to parameter
            setFieldValueToParameter(doubleSpinner.getValue(), parameterT);
            //listen to user interaction with double spinner and set the value to parameter
            this.doubleSpinner.setOnMouseClicked(event -> {
                setFieldValueToParameter(doubleSpinner.getValue(), parameterT);
                controlIdEmitter.setValue(this.doubleSpinnerT.getID() + ":" + getValue());
            });
            this.gridPane.add(this.doubleSpinner, this.nextColumn, 0);
            return this.gridPane;
        }
        return null;
    }

    @Override
    public void initializeControl() {
        if (this.doubleSpinnerT.getInitValue() != null)
            this.doubleSpinner.setValue(this.doubleSpinnerT.getInitValue());
    }

    @Override
    public void setDoubleSpinner(DoubleSpinnerT doubleSpinnerT) {
        this.doubleSpinnerT = doubleSpinnerT;

    }

    @Override
    public void setParameters(List<ParameterT> parameterTList) {
        this.parameterT = parameterTList.get(0);

    }

    @Override
    public List<ParameterT> getParameters() {
        return Arrays.asList(this.parameterT);
    }

    @Override
    public ObjectProperty<String> listenChange() {
        return this.controlIdEmitter;
    }

    @Override
    public DoubleSpinnerT getControl() {
        return this.doubleSpinnerT;
    }

    @Override
    public Double getValue() {
        return this.doubleSpinner.getValue();
    }

    @Override
    public void setValue(Double s) {
        this.doubleSpinner.getValueFactory().setValue(s);
        setFieldValueToParameter(doubleSpinner.getValue(), parameterT);
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
