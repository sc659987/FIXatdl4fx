package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.TextFieldT;

public interface FixTextFieldUiElement<T, K extends Comparable<K>> extends FixUiElement<T, K> {

    /***
     *
     * @param textField
     */
    void setTextField(TextFieldT textField);

}
