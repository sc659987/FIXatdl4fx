package org.atdl4j.fixatdl.controlflow;

import org.atdl4j.fixatdl.ui.common.element.FixUiElement;
import javafx.util.Pair;

public class FixAtdlStateRuleResultActorImpl implements FixAtdlStateRuleResultActor {

    @Override
    public void doAct(Pair<FixAtdlStateRuleResultType, Comparable> stateRuleResultComparablePair,
                      FixUiElement fixUiElement) {
        switch (stateRuleResultComparablePair.getKey()) {
            case ENABLE:
                fixUiElement.makeEnable((Boolean) stateRuleResultComparablePair.getValue());
                break;
            case VALUE:
                fixUiElement.setValue(stateRuleResultComparablePair.getValue().equals(FixUiElement.NULL_VALUE) ? null : stateRuleResultComparablePair.getValue());
                break;
            case VISIBLE:
                fixUiElement.makeVisible((Boolean) stateRuleResultComparablePair.getValue());
                break;
            default:
        }
    }
}
