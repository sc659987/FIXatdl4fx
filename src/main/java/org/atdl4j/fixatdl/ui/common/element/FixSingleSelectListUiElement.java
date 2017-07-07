package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.SingleSelectListT;

public interface FixSingleSelectListUiElement<T, K extends Comparable<K>> extends FixUiElement<T, K> {

    /****
     *
     * @param singleSelectListT
     */
    void setSingleSelectList(SingleSelectListT singleSelectListT);

}
