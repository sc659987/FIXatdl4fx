package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.StrategyLayoutT;

public interface FixLayoutUiElement<T, K extends Comparable<K>> extends FixUiElement<T, K> {

    void setStrategyLayout(StrategyLayoutT strategyLayoutT);

}
