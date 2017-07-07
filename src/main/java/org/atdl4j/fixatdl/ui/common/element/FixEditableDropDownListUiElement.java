package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.EditableDropDownListT;

/**
 * Created by sainik on 3/24/17.
 */
public interface FixEditableDropDownListUiElement<T, K extends Comparable<K>> extends FixUiElement<T, K> {

    /***
     *
     * @param listT
     */
    void setEditableDropDownList(EditableDropDownListT listT);
}
