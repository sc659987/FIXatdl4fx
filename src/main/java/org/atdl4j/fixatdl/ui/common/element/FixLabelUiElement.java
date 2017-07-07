package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.LabelT;

public interface FixLabelUiElement<T, K extends Comparable<K>> extends FixUiElement<T, K> {

    /***
     *
     * @param label
     */
    void setLabel(LabelT label);
}
