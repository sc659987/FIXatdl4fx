package org.atdl4j.fixatdl.ui.common;

import org.atdl4j.fixatdl.ui.common.element.*;

public interface UiElementAbstractFactory {

    <T, K extends Comparable<K>> FixCheckBoxListUiElement<T, K> instantiateNewCheckBoxList();

    <T, K extends Comparable<K>> FixCheckBoxUiElement<T, K> instantiateNewCheckBox();

    <T, K extends Comparable<K>> FixClockUiElement<T, K> instantiateNewClock();

    <T, K extends Comparable<K>> FixDoubleSpinnerUiElement<T, K> instantiateNewDoubleSpinner();

    <T, K extends Comparable<K>> FixDropDownListUiElement<T, K> instantiateNewDropDownList();

    <T, K extends Comparable<K>> FixEditableDropDownListUiElement<T, K> instantiateNewEditableDropDownList();

    <T, K extends Comparable<K>> FixLabelUiElement<T, K> instantiateNewLabel();

    <T, K extends Comparable<K>> FixMultiSelectListUiElement<T, K> instantiateNewMultiSelectList();

    <T, K extends Comparable<K>> FixRadioButtonListUiElement<T, K> instantiateNewRadioButtonList();

    <T, K extends Comparable<K>> FixRadioButtonUiElement<T, K> instantiateNewRadioButton();

    <T, K extends Comparable<K>> FixSingleSelectListUiElement<T, K> instantiateNewSingleSelectList();

    <T, K extends Comparable<K>> FixSingleSpinnerUiElement<T, K> instantiateNewSingleSpinner();

    <T, K extends Comparable<K>> FixSliderUiElement<T, K> instantiateNewSlider();

    <T, K extends Comparable<K>> FixTextFieldUiElement<T, K> instantiateNewTextField();

    <T, K extends Comparable<K>> FixHiddenFieldUiElement<T, K> instantiateNewHiddenField();

    <T, K extends Comparable<K>> FixPanelUiElement<T, K> instantiateNewPanel();

    <T, K extends Comparable<K>> FixLayoutUiElement<T, K> instantiateNewLayout();

}