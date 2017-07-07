package org.atdl4j.fixatdl.ui.fx8.element;

import org.atdl4j.fixatdl.controlflow.FixAtdlControlFlowRegister;
import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.layout.ControlT;
import org.atdl4j.fixatdl.model.layout.StrategyLayoutT;
import org.atdl4j.fixatdl.ui.common.UiElementAbstractFactory;
import org.atdl4j.fixatdl.ui.common.element.FixLayoutUiElement;
import org.atdl4j.fixatdl.ui.common.element.FixPanelUiElement;
import org.atdl4j.fixatdl.ui.fx8.FxUiElementFactory;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

public class FxFixLayoutUiElement implements FixLayoutUiElement<Node, String> {

    private StrategyLayoutT strategyLayoutT;

    private UiElementAbstractFactory factory = FxUiElementFactory.getInstance();

    private List<ParameterT> allParameterTList;

    private ScrollPane scrollPane = new ScrollPane();

    public FxFixLayoutUiElement() {
    }

    @Override
    public Node create() {
        VBox scrollPaneWrapper = new VBox();
        scrollPaneWrapper.getChildren().add(scrollPane);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(this.strategyLayoutT.getStrategyPanel().stream().map(strategyPanelT -> {
            FixPanelUiElement<Node, String> element = factory.instantiateNewPanel();
            element.setStrategyPanelT(strategyPanelT);
            element.setParameters(this.allParameterTList);
            return element.create();
        }).collect(Collectors.toList()));
        FixAtdlControlFlowRegister.getSingleTon().executeControlFlowForAllControls();
        scrollPane.setContent(vBox);
        return scrollPane;
    }

    @Override
    public void initializeControl() {

    }

    @Override
    public void setStrategyLayout(StrategyLayoutT strategyLayoutT) {
        this.strategyLayoutT = strategyLayoutT;
    }

    @Override
    public void setParameters(List<ParameterT> parameterTList) {
        this.allParameterTList = parameterTList;
    }

    @Override
    public List<ParameterT> getParameters() {
        return this.allParameterTList;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void setValue(String s) {

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
