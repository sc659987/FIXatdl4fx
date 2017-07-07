package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.DropDownListT;

/**
 * Created by sainik on 3/23/17.
 */
public interface FixDropDownListUiElement<T, K extends Comparable<K>> extends FixUiElement<T, K> {

    /***
     *
     * @param downList
     */
    void setDropDownList(DropDownListT downList);

}
