package org.atdl4j.fixatdl.ui.fx8.element;

import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.converter.TypeConverterRepo;
import org.atdl4j.fixatdl.model.core.*;
import org.atdl4j.fixatdl.model.layout.SingleSpinnerT;
import org.atdl4j.fixatdl.ui.common.element.FixSingleSpinnerUiElement;
import org.atdl4j.fixatdl.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.Collections;
import java.util.List;


public class FxFixSingleSpinnerUiElement implements FixSingleSpinnerUiElement<Pane, Double> {

    private Spinner<Double> singleSpinner;
    private SingleSpinnerT singleSpinnerT;

    private GridPane gridPane;

    private ParameterT parameterT;

    private int nextColumn = 0;

    private ObjectProperty<String> controlIdEmitter = new SimpleObjectProperty<>();

    private Pair<Double, Double> limit;

    private TypeConverter<?, ?> controlTTypeConverter;

    private boolean isInitialized = false;


    @Override
    public Pane create() {
        if (this.singleSpinnerT != null) {
            this.gridPane = new GridPane();

            this.controlTTypeConverter = TypeConverterRepo.createParameterTypeConverter(parameterT);

            if (Utils.isNonEmptyString(this.singleSpinnerT.getLabel())) {
                // TODO check when it's required and when not
                //this.gridPane.getColumnConstraints().addAll(FxUtils.getTwoColumnSameWidthForGridPane());
                this.gridPane.add(new Label(this.singleSpinnerT.getLabel()),
                        this.nextColumn++, 0);
            }
            this.gridPane.setHgap(3);
            this.limit = extractRangeFromParameter();
            this.singleSpinner = new Spinner<>(limit.getKey(),
                    limit.getValue(),
                    0,
                    calculateIncrement());

            this.singleSpinner.setOnMouseClicked(event -> {
                setValue( this.singleSpinner.getValue());
                controlIdEmitter.setValue(singleSpinnerT.getID() + ":" + getValue());
            });

            initializeControl();

            this.gridPane.add(this.singleSpinner, this.nextColumn, 0);
            return this.gridPane;
        }
        return null;
    }


    private double calculateIncrement() {
        String incrementPolicy = this.singleSpinnerT.getIncrementPolicy();
        if (incrementPolicy == null)
            return singleSpinnerT.getIncrement() == null ? adjustWhenNoIncrementsInfoProvided() : singleSpinnerT.getIncrement();
        Double increment = singleSpinnerT.getIncrement();
        switch (incrementPolicy.toLowerCase()) {
            case INCREMENT_POLICY_STATIC:
                return increment != null ? increment : 1.0;
            case INCREMENT_POLICY_LOT_SIZE:
                return increment != null ? increment : defaultLotSizeIncrementValue;
            case INCREMENT_POLICY_TICK:
                return increment != null ? increment : defaultTickIncrementValue;
            default:
                return 0.0;
        }

    }

    private double adjustWhenNoIncrementsInfoProvided() {
        if (parameterT != null) {
            if (parameterT instanceof IntT || parameterT instanceof LengthT || parameterT instanceof SeqNumT || parameterT instanceof TagNumT) {
                return 1.0;
            }
        }
        return 0.01;
    }


    private Double defaultLotSizeIncrementValue = 1.0;
    private Double defaultTickIncrementValue = 0.0001;

    public static final String INCREMENT_POLICY_STATIC = "static"; // -- use value from increment attribute --
    public static final String INCREMENT_POLICY_LOT_SIZE = "lotsize"; // -- use the round lot size of symbol --
    public static final String INCREMENT_POLICY_TICK = "tick"; // -- use symbol minimum tick size --


    @Override
    public void initializeControl() {
        if (this.singleSpinnerT.getInitValue() != null) {
            setValue(this.singleSpinnerT.getInitValue());
            this.isInitialized = true;
        } else {
            this.isInitialized = false;
        }
    }

    private Pair<Double, Double> extractRangeFromParameter() {
        return parameterT != null && (parameterT instanceof IntT)
                ? new Pair<>((((IntT) parameterT).getMinValue() == null ? 0.0 : ((IntT) parameterT).getMinValue()),
                (((IntT) parameterT).getMaxValue() == null ? Double.MAX_VALUE : ((IntT) parameterT).getMaxValue()))
                : new Pair<>(0.0, Double.MAX_VALUE);
    }

    @Override
    public void setSingleSpinner(SingleSpinnerT singleSpinnerT) {
        this.singleSpinnerT = singleSpinnerT;
    }

    @Override
    public void setParameters(List<ParameterT> parameterTList) {
        assert (parameterTList != null);
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
    public SingleSpinnerT getControl() {
        return this.singleSpinnerT;
    }

    @Override
    public Double getValue() {
        if (!isInitialized)
            return null;
        return this.singleSpinner.getValue();
    }

    @Override
    public void setValue(Double value) {
        this.singleSpinner.getValueFactory().setValue(value == null ? limit.getKey() : value);
        setFieldValueToParameter(value, parameterT);
    }

    @Override
    public void makeVisible(boolean visible) {
        this.singleSpinner.setVisible(visible);
    }

    @Override
    public void makeEnable(boolean enable) {
        this.singleSpinner.setDisable(!enable);
        if (enable)
            initializeControl();
    }

    @Override
    public TypeConverter<?, ?> getControlTTypeConverter() {
        return this.controlTTypeConverter;
    }
}
