package org.atdl4j.fixatdl.controlflow;

import org.atdl4j.fixatdl.model.flow.StateRuleT;
import javafx.util.Pair;

import java.util.List;

public interface FixAtdlStateRuleEvaluator {

    /***
     *
     * @param stateRuleT
     * @return
     */
    List<Pair<FixAtdlStateRuleResultType, Comparable>> getResult(StateRuleT stateRuleT);

}
