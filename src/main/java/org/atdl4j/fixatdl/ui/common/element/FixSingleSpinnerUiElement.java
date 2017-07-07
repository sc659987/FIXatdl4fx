package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.SingleSpinnerT;

public interface FixSingleSpinnerUiElement<T, K extends Comparable<K>> extends FixUiElement<T, K> {
    /***
     *
     * @param singleSpinnerT
     */
    void setSingleSpinner(SingleSpinnerT singleSpinnerT);

}
