package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.CheckBoxT;

/**
 * Created by sainik on 3/28/17.
 */
public interface FixCheckBoxUiElement<T, K extends Comparable<K>> extends FixUiElement<T, K> {

    void setCheckBoxT(CheckBoxT checkBoxT);
}
