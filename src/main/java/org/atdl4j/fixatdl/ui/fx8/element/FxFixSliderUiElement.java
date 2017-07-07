package org.atdl4j.fixatdl.ui.fx8.element;

import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.converter.TypeConverterRepo;
import org.atdl4j.fixatdl.model.core.EnumPairT;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.layout.ListItemT;
import org.atdl4j.fixatdl.model.layout.SliderT;
import org.atdl4j.fixatdl.ui.common.element.FixSliderUiElement;
import org.atdl4j.fixatdl.ui.fx8.FxUtils;
import org.atdl4j.fixatdl.ui.fx8.utils.ListStringConverter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FxFixSliderUiElement implements FixSliderUiElement<Pane, String> {

    private SliderT sliderT;
    private Slider slider;
    private ListStringConverter converter;
    private GridPane gridPane;
    private Label label;
    private ParameterT parameterT;

    private ObjectProperty<String> controlIdEmitter = new SimpleObjectProperty<>();

    private TypeConverter<?, ?> controlTTypeConverter;

    @Override
    public Pane create() {
        this.gridPane = new GridPane();
        this.gridPane.getColumnConstraints().addAll(FxUtils.getOneColumnWidthForGridPane());

        this.controlTTypeConverter = TypeConverterRepo.createParameterTypeConverter(parameterT);

        this.label = new Label(this.sliderT.getLabel());

        this.gridPane.add(this.label, 0, 0);

        this.converter = new ListStringConverter(this.sliderT.getListItem().stream().map(ListItemT::getUiRep).collect(Collectors.toList()));

        this.slider = new Slider(0, this.sliderT.getListItem().size() - 1,
                0);

        this.slider.setOnMouseClicked(event -> {
            setFieldValueToParameter(convertEnumIDToWireValue(getValue()), this.parameterT);
            this.controlIdEmitter.setValue(this.sliderT.getID() + ":" + getValue());
            // TODO check it when dragged too faster value change is not recognized and this is not called back
        });

        // initialize control
        initializeControl();
        //Init of parameter
        setFieldValueToParameter(convertEnumIDToWireValue(getValue()), this.parameterT);

        this.gridPane.setPadding(new Insets(0, 30, 0, 30));

        this.slider.setLabelFormatter(this.converter);
        this.slider.setShowTickLabels(true);
        this.slider.setShowTickMarks(true);
        this.slider.setMajorTickUnit(1.0);

        this.slider.setMinWidth(this.converter.length());

        this.slider.setMinorTickCount(0);
        this.slider.setSnapToTicks(true);

        this.slider.setSnapToTicks(true);

        this.slider.setOrientation(Orientation.HORIZONTAL);

        this.gridPane.add(this.slider, 0, 1, 5, 1);
        return this.gridPane;
    }

    @Override
    public void initializeControl() {
        if (this.sliderT.getInitValue() != null) {
            this.slider.setValue(this.converter.fromString(
                    this.sliderT
                            .getListItem()
                            .parallelStream()
                            .filter(listItemT -> listItemT.getEnumID().equals(this.sliderT.getInitValue())).findFirst()
                            .orElse(new ListItemT())
                            .getUiRep()));
        }
    }

    @Override
    public ObjectProperty<String> listenChange() {
        return this.controlIdEmitter;
    }

    @Override
    public String getValue() {
        return this.converter.toString(this.slider.getValue());
    }

    @Override
    public void setValue(String enumID) {
        double convertedDoubleValue = this.converter.fromString(this.sliderT
                .getListItem()
                .parallelStream()
                .filter(listItemT -> listItemT.getEnumID().equals(enumID)).findFirst()
                .orElse(new ListItemT())
                .getUiRep());
        this.slider.setValue(convertedDoubleValue);
        setFieldValueToParameter(convertEnumIDToWireValue(enumID), parameterT);
        controlIdEmitter.setValue(sliderT.getID() + ":" + convertedDoubleValue);
    }


    private String convertEnumIDToWireValue(String enumID) {
        return parameterT == null ? null : parameterT
                .getEnumPair()
                .parallelStream()
                .filter(enumPairT -> enumPairT.getEnumID()
                        .equals(enumID))
                .findFirst()
                .orElse(new EnumPairT())
                .getWireValue();
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
    public SliderT getControl() {
        return this.sliderT;
    }

    @Override
    public void setSlider(SliderT slider) {
        this.sliderT = slider;
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
    public TypeConverter<?, ?> getControlTTypeConverter() {
        return this.controlTTypeConverter;
    }
}
