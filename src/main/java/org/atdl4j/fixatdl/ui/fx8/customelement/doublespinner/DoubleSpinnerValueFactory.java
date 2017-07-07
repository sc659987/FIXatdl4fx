package org.atdl4j.fixatdl.ui.fx8.customelement.doublespinner;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by sainik on 4/25/17.
 */
public class DoubleSpinnerValueFactory {


    public void incrementInnerAmount() {
        double newValue = getValue() + getInnerIncrementAmount();
        setValue(getMax() > newValue ? newValue : getMax());
    }

    public void decrementInnerAmount() {
        double newValue = getValue() - getInnerIncrementAmount();
        setValue(getMin() > newValue ? getMin() : newValue);
    }

    public void incrementOuterAmount() {
        double newValue = getValue() + getOuterIncrementAmount();
        setValue(getMax() > newValue ? newValue : getMax());
    }

    public void decrementOuterAmount() {
        double newValue = getValue() - getOuterIncrementAmount();
        setValue(getMin() > newValue ? getMin() : newValue);
    }


    private ObjectProperty<Double> value = new SimpleObjectProperty<>(this, "value");

    public final Double getValue() {
        return value.get();
    }

    public final void setValue(Double newValue) {
        value.set(newValue);
    }

    public final ObjectProperty<Double> valueProperty() {
        return value;
    }


    public DoubleSpinnerValueFactory(double min, double max, double initialValue, double innerIncrement, double outerIncrement) {
        setMin(min);
        setMax(max);
        setInnerIncrementAmount(innerIncrement);
        setOuterIncrementAmount(outerIncrement);

        valueProperty().addListener((o, oldValue, newValue) -> {
            // when the value is set, we need to react to ensure it is a
            // valid value (and if not, blow up appropriately)
            if (newValue < getMin()) {
                setValue(getMin());
            } else if (newValue > getMax()) {
                setValue(getMax());
            }
        });
        setValue(initialValue >= min && initialValue <= max ? initialValue : min);
    }

    public final DoubleProperty minProperty() {
        return min;
    }


    private DoubleProperty min = new SimpleDoubleProperty(this, "min") {
        @Override
        protected void invalidated() {
            Double currentValue = DoubleSpinnerValueFactory.this.getValue();
            if (currentValue == null)
                return;

            final double newMin = get();
            if (newMin > getMax()) {
                setMin(getMax());
                return;
            }
            if (currentValue < newMin)
                setValue(newMin);

        }
    };


    public final double getMin() {
        return min.get();
    }

    public final void setMin(double value) {
        min.set(value);
    }


    private DoubleProperty max = new SimpleDoubleProperty(this, "max") {
        @Override
        protected void invalidated() {
            Double currentValue = DoubleSpinnerValueFactory.this.getValue();
            if (currentValue == null)
                return;
            final double newMax = get();
            if (newMax < getMin()) {
                setMax(getMin());
            }

            if (currentValue > newMax) {
                setValue(newMax);
            }
        }
    };

    public final void setMax(double value) {
        max.set(value);
    }

    public final double getMax() {
        return max.get();
    }


    public final DoubleProperty maxProperty() {
        return max;
    }


    private DoubleProperty innerIncrementAmount = new SimpleDoubleProperty(this, "innerIncrementAmount");

    public final void setInnerIncrementAmount(double value) {
        innerIncrementAmount.set(value);
    }

    public final double getInnerIncrementAmount() {
        return innerIncrementAmount.get();
    }

    public final DoubleProperty innerIncrementAmountProperty() {
        return innerIncrementAmount;
    }


    private DoubleProperty outerIncrementAmount = new SimpleDoubleProperty(this, "outerIncrementAmount");


    public final void setOuterIncrementAmount(double value) {
        outerIncrementAmount.set(value);
    }

    public final double getOuterIncrementAmount() {
        return outerIncrementAmount.get();
    }

    public final DoubleProperty outerIncrementAmountProperty() {
        return outerIncrementAmount;
    }


}
