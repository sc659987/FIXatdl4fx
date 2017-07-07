package org.atdl4j.fixatdl.ui.fx8.customelement.doublespinner;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * Created by sainik on 24/04/17.
 */
public class DoubleSpinnerBehavior extends BehaviorBase<DoubleSpinner> {


    public DoubleSpinnerBehavior(DoubleSpinner doubleSpinner) {
        super(doubleSpinner, null);
    }

    private Timeline timeline;
    private boolean isIncrementing = false;
    private static final double INITIAL_DURATION_MS = 750;
    private boolean isOuter = false;

    final EventHandler<ActionEvent> spinningKeyFrameEventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            final DoubleSpinnerValueFactory valueFactory = getControl().getValueFactory();
            if (valueFactory == null) {
                return;
            }
            if (isIncrementing) increment();
            else decrement();
        }
    };


    public void startSpinning(boolean increment, boolean isOuter) {
        isIncrementing = increment;
        this.isOuter = isOuter;
        if (timeline != null)
            timeline.stop();

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        final KeyFrame keyFrame = new KeyFrame(Duration.millis(INITIAL_DURATION_MS), spinningKeyFrameEventHandler);
        timeline.getKeyFrames().setAll(keyFrame);
        timeline.playFromStart();
        timeline.play();
        spinningKeyFrameEventHandler.handle(null);

    }

    public void stopSpinning() {
        if (timeline != null)
            timeline.stop();
    }

    public void increment() {
        if (isOuter) getControl().outerIncrement();
        else getControl().innerIncrement();
    }

    public void decrement() {
        if (isOuter) getControl().outerDecrement();
        else getControl().innerDecrement();
    }


}
