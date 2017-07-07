package org.atdl4j.fixatdl.ui.common.element;

import org.atdl4j.fixatdl.model.layout.SliderT;

/**
 * Created by sainik on 3/23/17.
 */
public interface FixSliderUiElement<T, K extends Comparable<K>> extends FixUiElement<T, K> {

    /***
     *
     * @param slider
     */
    void setSlider(SliderT slider);

}
