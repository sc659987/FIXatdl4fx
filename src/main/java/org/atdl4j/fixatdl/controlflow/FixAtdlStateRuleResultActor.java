package org.atdl4j.fixatdl.controlflow;

import org.atdl4j.fixatdl.ui.common.element.FixUiElement;
import javafx.util.Pair;

public interface FixAtdlStateRuleResultActor {

    /***
     *
     * @param stateRuleResultComparablePair
     * @param fixUiElement
     */
    void doAct(Pair<FixAtdlStateRuleResultType, Comparable> stateRuleResultComparablePair, FixUiElement fixUiElement);

}
