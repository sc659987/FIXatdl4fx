package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.RadioButtonListT;

/**
 * Created by sainik on 3/24/17.
 */
public interface FixRadioButtonListUiElement<T, K extends Comparable<K>> extends FixUiElement<T, K> {

    void setRadioButtonListT(RadioButtonListT radioButtonListT);
}
