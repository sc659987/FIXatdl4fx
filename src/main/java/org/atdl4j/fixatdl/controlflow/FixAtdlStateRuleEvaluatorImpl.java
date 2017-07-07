package org.atdl4j.fixatdl.controlflow;

import org.atdl4j.fixatdl.evaluator.FixAtdlEditEvaluator;
import org.atdl4j.fixatdl.evaluator.RecursiveFixAtdlEditEvaluator;
import org.atdl4j.fixatdl.model.flow.StateRuleT;
import org.atdl4j.fixatdl.ui.common.element.FixUiElement;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FixAtdlStateRuleEvaluatorImpl implements FixAtdlStateRuleEvaluator {

    private FixAtdlEditEvaluator fixAtdlEditEvaluator;
    private ControlIdToValueCachedMap fieldToComparableMapperCache;

    public FixAtdlStateRuleEvaluatorImpl(Map<String, FixUiElement<?, ?>> allIFixUiElement) {
        this.fieldToComparableMapperCache = new ControlIdToValueCachedMap(allIFixUiElement);
        this.fixAtdlEditEvaluator = new RecursiveFixAtdlEditEvaluator(this.fieldToComparableMapperCache);
    }

    @Override
    public List<Pair<FixAtdlStateRuleResultType, Comparable>> getResult(StateRuleT stateRuleT) {
        boolean validationResult = fixAtdlEditEvaluator.validate(stateRuleT.getEdit());
        List<Pair<FixAtdlStateRuleResultType, Comparable>> pairList = new ArrayList<>();
        if (stateRuleT.isEnabled() != null)
            pairList.add(new Pair<>(FixAtdlStateRuleResultType.ENABLE, validationResult ? stateRuleT.isEnabled() : !stateRuleT.isEnabled()));
        if (stateRuleT.isVisible() != null)
            pairList.add(new Pair<>(FixAtdlStateRuleResultType.VISIBLE, validationResult ? stateRuleT.isVisible() : !stateRuleT.isVisible()));
        if (stateRuleT.getValue() != null && validationResult)
            pairList.add(new Pair<>(FixAtdlStateRuleResultType.VALUE, stateRuleT.getValue()));
        return pairList;
    }

}
