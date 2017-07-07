package org.atdl4j.fixatdl.ui.fx8.element;

import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.converter.TypeConverterRepo;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.layout.TextFieldT;
import org.atdl4j.fixatdl.ui.common.element.FixTextFieldUiElement;
import org.atdl4j.fixatdl.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Collections;
import java.util.List;

public class FxFixTextFieldUiElement implements FixTextFieldUiElement<Pane, String> {

    private TextFieldT textFieldT;

    private TextField textField;
    private GridPane gridPane;

    private ParameterT parameterT;
    private int nextColumn = 0;

    private ObjectProperty<String> controlIdEmitter = new SimpleObjectProperty<>();

    private TypeConverter<?, ?> controlTTypeConverter;

    @Override
    public Pane create() {
        if (this.textFieldT != null) {
            this.gridPane = new GridPane();

            this.controlTTypeConverter = TypeConverterRepo.createParameterTypeConverter(parameterT);

            if (!Utils.isEmptyString(this.textFieldT.getLabel())) {
                // TODO decide where to use it
                //this.gridPane.getColumnConstraints().addAll(FxUtils.getTwoColumnSameWidthForGridPane());
                this.gridPane.add(new Label(this.textFieldT.getLabel()), this.nextColumn++, 0);
            }
            //this.gridPane.setHgap(1);

            this.textField = new TextField();

            initializeControl();
            setValue(this.textFieldT.getInitValue());

            this.textField.setOnKeyReleased(event -> {
                setFieldValueToParameter(getValue(), this.parameterT);
                this.controlIdEmitter.setValue(textFieldT.getID() + ":" + getValue());
            });


            this.gridPane.add(this.textField, this.nextColumn, 0);
            return this.gridPane;
        }
        return null;
    }


    private void initializeTextFormatter() {
        // TODO make it possible
    }


    @Override
    public void initializeControl() {
        if (Utils.isNonEmptyString(this.textFieldT.getInitValue())) {
            this.textField.setText(this.textFieldT.getInitValue());
            setFieldValueToParameter(this.textFieldT.getInitValue(), parameterT);
        }
    }

    @Override
    public void setTextField(TextFieldT textField) {
        this.textFieldT = textField;
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
    public TextFieldT getControl() {
        return this.textFieldT;
    }

    @Override
    public String getValue() {
        String str = this.textField.getCharacters().toString();
        return Utils.isEmptyString(str) ? null : str;
    }

    @Override
    public void setValue(String text) {
        this.textField.setText(text);
        setFieldValueToParameter(text, parameterT);
    }

    @Override
    public void makeVisible(boolean visible) {
        this.textField.setVisible(visible);
    }

    @Override
    public void makeEnable(boolean enable) {
        this.textField.setDisable(!enable);
        if (enable)
            initializeControl();
    }

    @Override
    public TypeConverter<?, ?> getControlTTypeConverter() {
        return this.controlTTypeConverter;
    }
}
