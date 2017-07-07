package org.atdl4j.fixatdl.controlflow;

import org.atdl4j.fixatdl.model.layout.ControlT;
import org.atdl4j.fixatdl.model.validation.EditT;
import org.atdl4j.fixatdl.ui.common.element.FixUiElement;

import java.util.*;
import java.util.stream.Collectors;

public class FixAtdlControlFlowRegister {

    // controlId and Fx Ui element which are dependent on that Id for control flow
    private Map<String, Set<FixUiElement>> controlIdUiElementMap;

    // evaluate the condition of state rule
    private FixAtdlStateRuleEvaluator fixAtdlStateRuleEvaluator;

    // act of result of fixAtdlStateRuleEvaluator
    private FixAtdlStateRuleResultActor fxFixAtdlStateRuleResultActor;

    //
    private Map<String, FixUiElement<?, ?>> allIFixUiElements = new HashMap<>();

    private static FixAtdlControlFlowRegister _singleton;

    public static synchronized FixAtdlControlFlowRegister getSingleTon() {
        return _singleton == null ? _singleton = new FixAtdlControlFlowRegister() : _singleton;
    }

    private FixAtdlControlFlowRegister() {
        controlIdUiElementMap = new LinkedHashMap<>();
        fixAtdlStateRuleEvaluator = new FixAtdlStateRuleEvaluatorImpl(this.allIFixUiElements);
        fxFixAtdlStateRuleResultActor = new FixAtdlStateRuleResultActorImpl();
    }

    public void registerControlFlow(FixUiElement<?, ? extends Comparable<?>> fixUiElement) {
        allIFixUiElements.put(fixUiElement.getControl().getID(), fixUiElement);
        // get control from fixUiElement
        ControlT iFixUiElementControlT = fixUiElement.getControl();
        // for each StateRule map all control Id this UiElement depends on
        iFixUiElementControlT.getStateRule().forEach(stateRuleT -> getAllControlIdFromEdit(stateRuleT.getEdit()).forEach(controlId -> {
            Set<FixUiElement> fixUiElements = controlIdUiElementMap.getOrDefault(controlId, new LinkedHashSet<>());
            fixUiElements.add(fixUiElement);
            controlIdUiElementMap.put(controlId, fixUiElements);
        }));
        // listen to all
        //TODO change redundant split of string extend SingleObjectProperty and change it's behavior
        fixUiElement.listenChange().addListener((observable, oldValue, newValue) -> executeControlFLowByControlId(newValue.split(":")[0]));
    }

    private void executeControlFLowByControlId(String controlId) {
        controlIdUiElementMap.getOrDefault(controlId, new LinkedHashSet<>()).forEach(effectedIFixElement ->
                effectedIFixElement.getControl().getStateRule().forEach(stateRuleT -> fixAtdlStateRuleEvaluator
                        .getResult(stateRuleT).forEach(atdlStateRuleResultTypeComparablePair ->
                                fxFixAtdlStateRuleResultActor.doAct(atdlStateRuleResultTypeComparablePair, effectedIFixElement))));
    }

    public void executeControlFlowForAllControls() {
        controlIdUiElementMap.keySet().stream().forEach(this::executeControlFLowByControlId);
    }


    private Set<String> getAllControlIdFromEdit(EditT editT) {
        if (editT.getLogicOperator() != null) {
            return editT.getEdit().stream().map(editT1 -> getAllControlIdFromEdit(editT1)).flatMap(strings -> strings.stream()).collect(Collectors.toSet());
        } else {
            Set<String> stringList = new HashSet<>();
            if (editT.getField() != null)
                stringList.add(editT.getField());
            if (editT.getField2() != null)
                stringList.add(editT.getField2());
            return stringList;
        }
    }

}
