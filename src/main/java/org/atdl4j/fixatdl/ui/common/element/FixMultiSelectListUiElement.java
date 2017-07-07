package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.MultiSelectListT;

/**
 * Created by sainik on 3/24/17.
 */
public interface FixMultiSelectListUiElement<T, K extends Comparable<K>> extends FixUiElement<T, K> {

    void setMultiSelectList(MultiSelectListT multiSelectListT);

}
