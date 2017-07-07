package org.atdl4j.fixatdl.ui.fx8.customelement.doublespinner;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.AccessibleAction;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;

/**
 * Created by sainik on 4/10/17.
 */
public class DoubleSpinner extends Control {

    private static final String DEFAULT_STYLE_CLASS = "spinner";


    public DoubleSpinner() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setAccessibleRole(AccessibleRole.SPINNER);
    }

    public DoubleSpinner(double min, double max, double initialValue, double innerIncrement, double outerIncrement) {
        this();
        setValueFactory(new DoubleSpinnerValueFactory(min, max, initialValue, innerIncrement, outerIncrement));
        editorProperty().get().setText(String.valueOf(getValueFactory().getValue()));
    }


    public void innerIncrement() {
        getValueFactory().incrementInnerAmount();
        editorProperty().get().setText(String.valueOf(getValueFactory().getValue()));
    }

    public void innerDecrement() {
        getValueFactory().decrementInnerAmount();
        editorProperty().get().setText(String.valueOf(getValueFactory().getValue()));
    }

    public void outerIncrement() {
        getValueFactory().incrementOuterAmount();
        editorProperty().get().setText(String.valueOf(getValueFactory().getValue()));
    }

    public void outerDecrement() {
        getValueFactory().decrementOuterAmount();
        editorProperty().get().setText(String.valueOf(getValueFactory().getValue()));
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DoubleSpinnerSkin(this);
    }


    private ReadOnlyObjectWrapper<Double> value = new ReadOnlyObjectWrapper<>(this, "value");

    public final Double getValue() {
        return this.value.get();
    }

    public final void setValue(Double value){
        this.value.set(value);
    }

    public final ReadOnlyObjectProperty<Double> valueProperty() {
        return value;
    }


    private ObjectProperty<DoubleSpinnerValueFactory> valueFactory =
            new SimpleObjectProperty<DoubleSpinnerValueFactory>(this, "valueFactory") {
                @Override
                protected void invalidated() {
                    value.unbind();
                    DoubleSpinnerValueFactory newFactory = get();
                    if (newFactory != null) {
                        value.bind(newFactory.valueProperty());
                    }
                }
            };

    public final void setValueFactory(DoubleSpinnerValueFactory valueFactory) {
        this.valueFactory.setValue(valueFactory);
    }

    public final DoubleSpinnerValueFactory getValueFactory() {
        return this.valueFactory.get();
    }

    public final ObjectProperty<DoubleSpinnerValueFactory> valueFactoryProperty() {
        return valueFactory;
    }

    public final ReadOnlyObjectProperty<TextField> editorProperty() {
        if (editor == null) {
            editor = new ReadOnlyObjectWrapper<>(this, "editor");
            textField = new ComboBoxListViewSkin.FakeFocusTextField();
            editor.set(textField);
        }
        return editor.getReadOnlyProperty();
    }

    private TextField textField;
    private ReadOnlyObjectWrapper<TextField> editor;

    public final TextField getEditor() {
        return editorProperty().get();
    }


    @Override
    public void executeAccessibleAction(AccessibleAction action, Object... parameters) {
        super.executeAccessibleAction(action, parameters);
    }
}
