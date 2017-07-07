package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.CheckBoxListT;

public interface FixCheckBoxListUiElement<T, K extends Comparable<K>> extends FixUiElement<T, K> {

    /**
     * @param checkBoxListT
     */
    void setCheckBoxListT(CheckBoxListT checkBoxListT);
}
