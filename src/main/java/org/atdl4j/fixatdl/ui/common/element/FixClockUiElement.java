package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.ClockT;

/**
 * Created by sainik on 4/28/17.
 */
public interface FixClockUiElement<T, K extends Comparable<?>> extends FixUiElement<T, K> {

    void setClockT(ClockT clockT);


}
