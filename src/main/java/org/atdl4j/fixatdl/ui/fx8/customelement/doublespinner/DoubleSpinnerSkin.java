package org.atdl4j.fixatdl.ui.fx8.customelement.doublespinner;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.AccessibleAction;
import javafx.scene.AccessibleRole;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;


public class DoubleSpinnerSkin extends BehaviorSkinBase<DoubleSpinner, DoubleSpinnerBehavior> {

    private TextField textField;

    private Region firstIncrementArrow;
    private StackPane firstIncrementArrowButton;

    private Region secondIncrementArrow;
    private StackPane secondIncrementArrowButton;

    private Region firstDecrementArrow;
    private StackPane firstDecrementArrowButton;

    private Region secondDecrementArrow;
    private StackPane secondDecrementArrowButton;

    protected DoubleSpinnerSkin(DoubleSpinner spinner) {
        super(spinner, new DoubleSpinnerBehavior(spinner));

        textField = spinner.getEditor();
        getChildren().add(textField);

        // First innerIncrement
        firstIncrementArrow = new Region();
        firstIncrementArrow.setFocusTraversable(false);
        firstIncrementArrow.getStyleClass().setAll("increment-arrow");
        firstIncrementArrow.setMaxWidth(Region.USE_PREF_SIZE);
        firstIncrementArrow.setMaxHeight(Region.USE_PREF_SIZE);
        firstIncrementArrow.setMouseTransparent(true);

        firstIncrementArrowButton = new StackPane() {
            public void executeAccessibleAction(AccessibleAction action, Object... parameters) {
                switch (action) {
                    case FIRE:
                        getSkinnable().innerIncrement();
                    default:
                        super.executeAccessibleAction(action, parameters);
                }
            }
        };
        firstIncrementArrowButton.setAccessibleRole(AccessibleRole.INCREMENT_BUTTON);
        firstIncrementArrowButton.setFocusTraversable(false);
        firstIncrementArrowButton.getStyleClass().setAll("increment-arrow-button");
        firstIncrementArrowButton.getChildren().add(firstIncrementArrow);
        firstIncrementArrowButton.setOnMousePressed(e -> {
            getSkinnable().requestFocus();
            getBehavior().startSpinning(true, false);
        });
        firstIncrementArrowButton.setOnMouseReleased(e -> getBehavior().stopSpinning());

        // First Decrement
        firstDecrementArrow = new Region();
        firstDecrementArrow.setFocusTraversable(false);
        firstDecrementArrow.getStyleClass().setAll("decrement-arrow");
        firstDecrementArrow.setMaxWidth(Region.USE_PREF_SIZE);
        firstDecrementArrow.setMaxHeight(Region.USE_PREF_SIZE);
        firstDecrementArrow.setMouseTransparent(true);

        firstDecrementArrowButton = new StackPane() {
            public void executeAccessibleAction(AccessibleAction action, Object... parameters) {
                switch (action) {
                    case FIRE:
                        getSkinnable().innerDecrement();
                    default:
                        super.executeAccessibleAction(action, parameters);
                }
            }
        };
        firstDecrementArrowButton.setAccessibleRole(AccessibleRole.INCREMENT_BUTTON);
        firstDecrementArrowButton.setFocusTraversable(false);
        firstDecrementArrowButton.getStyleClass().setAll("decrement-arrow-button");
        firstDecrementArrowButton.getChildren().add(firstDecrementArrow);
        firstDecrementArrowButton.setOnMousePressed(e -> {
            getSkinnable().requestFocus();
            getBehavior().startSpinning(false, false);
        });
        firstDecrementArrowButton.setOnMouseReleased(e -> getBehavior().stopSpinning());

        // Second Increment

        secondIncrementArrow = new Region();
        secondIncrementArrow.setFocusTraversable(false);
        secondIncrementArrow.getStyleClass().setAll("increment-arrow");
        secondIncrementArrow.setMaxWidth(Region.USE_PREF_SIZE);
        secondIncrementArrow.setMaxHeight(Region.USE_PREF_SIZE);
        secondIncrementArrow.setMouseTransparent(true);

        secondIncrementArrowButton = new StackPane() {
            public void executeAccessibleAction(AccessibleAction action, Object... parameters) {
                switch (action) {
                    case FIRE:
                        getSkinnable().outerIncrement();
                    default:
                        super.executeAccessibleAction(action, parameters);
                }
            }
        };
        secondIncrementArrowButton.setAccessibleRole(AccessibleRole.INCREMENT_BUTTON);
        secondIncrementArrowButton.setFocusTraversable(false);
        secondIncrementArrowButton.getStyleClass().setAll("increment-arrow-button");
        secondIncrementArrowButton.getChildren().add(secondIncrementArrow);
        secondIncrementArrowButton.setOnMousePressed(e -> {
            getSkinnable().requestFocus();
            getBehavior().startSpinning(true, true);
        });
        secondIncrementArrowButton.setOnMouseReleased(e -> getBehavior().stopSpinning());

        // Second Decrement

        secondDecrementArrow = new Region();
        secondDecrementArrow.setFocusTraversable(false);
        secondDecrementArrow.getStyleClass().setAll("decrement-arrow");
        secondDecrementArrow.setMaxWidth(Region.USE_PREF_SIZE);
        secondDecrementArrow.setMaxHeight(Region.USE_PREF_SIZE);
        secondDecrementArrow.setMouseTransparent(true);

        secondDecrementArrowButton = new StackPane() {
            public void executeAccessibleAction(AccessibleAction action, Object... parameters) {
                switch (action) {
                    case FIRE:
                        getSkinnable().outerDecrement();
                    default:
                        super.executeAccessibleAction(action, parameters);
                }
            }
        };
        secondDecrementArrowButton.setAccessibleRole(AccessibleRole.INCREMENT_BUTTON);
        secondDecrementArrowButton.setFocusTraversable(false);
        secondDecrementArrowButton.getStyleClass().setAll("decrement-arrow-button");
        secondDecrementArrowButton.getChildren().add(secondDecrementArrow);
        secondDecrementArrowButton.setOnMousePressed(e -> {
            getSkinnable().requestFocus();
            getBehavior().startSpinning(false, true);
        });
        secondDecrementArrowButton.setOnMouseReleased(e -> getBehavior().stopSpinning());

        getChildren().addAll(firstIncrementArrowButton, firstDecrementArrowButton, secondIncrementArrowButton, secondDecrementArrowButton);
    }


    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {

        final double firstIncrementArrowButtonWidth = firstIncrementArrowButton.snappedLeftInset() +
                snapSize(firstIncrementArrow.prefWidth(-1)) + firstIncrementArrowButton.snappedRightInset();

        final double firstDecrementArrowButtonWidth = firstDecrementArrowButton.snappedLeftInset() +
                snapSize(firstDecrementArrow.prefWidth(-1)) + firstDecrementArrowButton.snappedRightInset();

        final double widestFirstArrowButton = Math.max(firstIncrementArrowButtonWidth, firstDecrementArrowButtonWidth);

        final double secondIncrementArrowButtonWidth = secondIncrementArrowButton.snappedLeftInset() +
                snapSize(secondIncrementArrow.prefWidth(-1) + secondIncrementArrowButton.snappedRightInset());

        final double secondDecrementArrowButtonWidth = secondDecrementArrow.snappedLeftInset() +
                snapSize(secondDecrementArrow.prefWidth(-1) + secondIncrementArrowButton.snappedRightInset());

        final double widestSecondArrowButton = Math.max(secondIncrementArrowButtonWidth, secondDecrementArrowButtonWidth);

        final double textFieldStartX = contentX;
        final double firstButtonStartX = contentX + contentWidth - widestFirstArrowButton - widestSecondArrowButton;
        final double secondButtonStartX = contentX + contentWidth - widestSecondArrowButton;
        final double halfHeight = Math.floor(contentHeight / 2.0);

        textField.resizeRelocate(textFieldStartX, contentY, contentWidth - widestFirstArrowButton, contentHeight);
        firstIncrementArrowButton.resize(widestFirstArrowButton, halfHeight);

        positionInArea(firstIncrementArrowButton, firstButtonStartX, contentY,
                widestFirstArrowButton, halfHeight, 0, HPos.CENTER, VPos.CENTER);

        firstDecrementArrowButton.resize(widestFirstArrowButton, halfHeight);
        positionInArea(firstDecrementArrowButton, firstButtonStartX, contentY + halfHeight,
                widestFirstArrowButton, contentHeight - halfHeight, 0, HPos.CENTER, VPos.BOTTOM);

        secondIncrementArrowButton.resize(widestSecondArrowButton, halfHeight);
        positionInArea(secondIncrementArrowButton, secondButtonStartX, contentY, widestSecondArrowButton, halfHeight, 0, HPos.CENTER, VPos.CENTER);

        secondDecrementArrowButton.resize(widestSecondArrowButton, halfHeight);
        positionInArea(secondDecrementArrowButton, secondButtonStartX, contentY + halfHeight, widestSecondArrowButton, halfHeight, 0, HPos.CENTER, VPos.CENTER);

    }


    @Override
    protected double computeMinHeight(double width, double topInset,
                                      double rightInset, double bottomInset,
                                      double leftInset) {
        return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computePrefHeight(double width, double topInset,
                                       double rightInset, double bottomInset,
                                       double leftInset) {
        double textFieldHeight = textField.prefHeight(width);
        return topInset + textFieldHeight + bottomInset;
    }

    @Override
    protected double computePrefWidth(double height, double topInset,
                                      double rightInset, double bottomInset,
                                      double leftInset) {
        final double textfieldWidth = textField.prefWidth(height);
        return leftInset + textfieldWidth + rightInset;
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return getSkinnable().prefHeight(width);
    }


    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return getSkinnable().prefWidth(height);
    }

    @Override
    protected double computeBaselineOffset(double topInset, double rightInset, double bottomInset, double leftInset) {
        return textField.getLayoutBounds().getMinY() + textField.getLayoutY() + textField.getBaselineOffset();
    }
}
