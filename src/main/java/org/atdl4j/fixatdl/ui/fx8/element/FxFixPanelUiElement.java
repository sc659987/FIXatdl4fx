package org.atdl4j.fixatdl.ui.fx8.element;

import org.atdl4j.fixatdl.controlflow.FixAtdlControlFlowRegister;
import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.ui.common.UiElementAbstractFactory;
import org.atdl4j.fixatdl.ui.common.element.*;
import org.atdl4j.fixatdl.ui.fx8.FxUiElementFactory;
import org.atdl4j.fixatdl.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.atdl4j.fixatdl.model.layout.*;

import java.util.List;
import java.util.stream.Collectors;

public class FxFixPanelUiElement implements FixPanelUiElement<Node, String> {

    private UiElementAbstractFactory factory = FxUiElementFactory.getInstance();

    private StrategyPanelT strategyPanelT;

    private List<ParameterT> parameterTList;

    private FixAtdlControlFlowRegister fxAtdlControlFlowRegister;

    public FxFixPanelUiElement(FixAtdlControlFlowRegister singleton) {
        this.fxAtdlControlFlowRegister = singleton;
    }

    @Override
    public Node create() {
        if (this.strategyPanelT != null) {
            Pane parent = (this.strategyPanelT.getOrientation() == PanelOrientationT.HORIZONTAL)
                    ? new HBox() : new VBox(4);
            TitledPane titledPane = null;
            if (Utils.isNonEmptyString(this.strategyPanelT.getTitle())) {
                titledPane = new TitledPane();
                titledPane.setCollapsible(this.strategyPanelT.isCollapsible());
                titledPane.setExpanded(!this.strategyPanelT.isCollapsed());
                // titledPane.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                titledPane.setText(this.strategyPanelT.getTitle());
            } else {
                parent.setPadding(new Insets(2, 2, 2, 2));
            }
            // Container for either groups of parameters or strategyPanels, but not both.
            // I.e., a StrategyPanel will contain either all Control elements or all
            // StrategyPanel elements.
            if (this.strategyPanelT.getStrategyPanel() != null && this.strategyPanelT.getStrategyPanel().size() > 0) {
                parent.getChildren().addAll(this.strategyPanelT
                        .getStrategyPanel()
                        .stream()
                        .map(eachStrategyPanelT -> {
                            FixPanelUiElement<Node, String> element = factory.instantiateNewPanel();
                            element.setStrategyPanelT(eachStrategyPanelT);
                            element.setParameters(this.parameterTList);
                            return element.create();
                        }).collect(Collectors.toList()));
            } else if (this.strategyPanelT.getControl() != null) {
                parent.getChildren().addAll(this.strategyPanelT.getControl().stream().map(controlT -> {
                    if (controlT instanceof CheckBoxListT) {
                        FixCheckBoxListUiElement<Pane, String> element = factory.instantiateNewCheckBoxList();
                        element.setCheckBoxListT((CheckBoxListT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof CheckBoxT) {
                        FixCheckBoxUiElement<CheckBox, String> element = factory.instantiateNewCheckBox();
                        element.setCheckBoxT((CheckBoxT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof ClockT) {
                        FixClockUiElement<HBox, String> element = factory.instantiateNewClock();
                        element.setClockT((ClockT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof DoubleSpinnerT) {
                        FixDoubleSpinnerUiElement<Pane, Double> element = factory.instantiateNewDoubleSpinner();
                        element.setDoubleSpinner((DoubleSpinnerT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof DropDownListT) {
                        FixDropDownListUiElement<HBox, String> element = factory.instantiateNewDropDownList();
                        element.setDropDownList((DropDownListT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof EditableDropDownListT) {
                        FixEditableDropDownListUiElement<HBox, String> element = factory.instantiateNewEditableDropDownList();
                        element.setEditableDropDownList((EditableDropDownListT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof LabelT) {
                        FixLabelUiElement<WebView, String> element = factory.instantiateNewLabel();
                        element.setLabel((LabelT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof MultiSelectListT) {
                        FixMultiSelectListUiElement<Pane, String> element = factory.instantiateNewMultiSelectList();
                        element.setMultiSelectList((MultiSelectListT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof RadioButtonListT) {
                        FixRadioButtonListUiElement<Pane, String> element = factory.instantiateNewRadioButtonList();
                        element.setRadioButtonListT((RadioButtonListT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof RadioButtonT) {
                        FixRadioButtonUiElement<RadioButton, String> element = factory.instantiateNewRadioButton();
                        element.setRadioButtonT((RadioButtonT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof SingleSelectListT) {
                        FixSingleSelectListUiElement<Pane, String> element = factory.instantiateNewSingleSelectList();
                        element.setSingleSelectList((SingleSelectListT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof SingleSpinnerT) {
                        FixSingleSpinnerUiElement<Pane, String> element = factory.instantiateNewSingleSpinner();
                        element.setSingleSpinner((SingleSpinnerT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof SliderT) {
                        FixSliderUiElement<Slider, String> element = factory.instantiateNewSlider();
                        element.setSlider((SliderT) controlT);
                        element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof TextFieldT) {
                        FixTextFieldUiElement<HBox, String> element = factory.instantiateNewTextField();
                        element.setTextField((TextFieldT) controlT);
                        if (controlT.getParameterRef() != null)
                            element.setParameters(findParameterByName(controlT.getParameterRef()));
                        this.fxAtdlControlFlowRegister.registerControlFlow(element);
                        return element.create();
                    } else if (controlT instanceof HiddenFieldT) {

                    }
                    throw new RuntimeException("control is not recognized");
                }).collect(Collectors.toList()));
            }
            if (titledPane != null)
                titledPane.setContent(parent);
            return titledPane == null ? parent : titledPane;
        }
        return null;
    }


    @Override
    public void initializeControl() {

    }

    @Override
    public void setStrategyPanelT(StrategyPanelT strategyPanelT) {
        this.strategyPanelT = strategyPanelT;
    }

    @Override
    public void setParameters(List<ParameterT> parameterTList) {
        this.parameterTList = parameterTList;
    }

    @Override
    public List<ParameterT> getParameters() {
        return this.parameterTList;
    }

    @Override
    public ObjectProperty<String> listenChange() {
        return null;
    }

    @Override
    public ControlT getControl() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void setValue(String s) {

    }

    @Override
    public void makeVisible(boolean visible) {

    }

    @Override
    public void makeEnable(boolean enable) {

    }

    @Override
    public TypeConverter<?, ?> getControlTTypeConverter() {
        return null;
    }
}
