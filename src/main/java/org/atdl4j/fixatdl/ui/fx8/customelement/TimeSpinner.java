package org.atdl4j.fixatdl.ui.fx8.customelement;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.InputEvent;
import javafx.util.StringConverter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by sainik on 3/27/17.
 */
public class TimeSpinner extends Spinner<DateTime> {


    // Mode represents the unit that is currently being edited.
    // For convenience expose methods for incrementing and decrementing that
    // unit, and for selecting the appropriate portion in a spinner's editor
    public enum Mode {
        HOURS {
            @Override
            DateTime increment(DateTime time, int steps) {
                return time.plusHours(steps);
            }

            @Override
            void select(TimeSpinner spinner) {
                int index = spinner.getEditor().getText().indexOf(':');
                spinner.getEditor().selectRange(0, index);
            }
        },
        MINUTES {
            @Override
            DateTime increment(DateTime time, int steps) {
                return time.plusMinutes(steps);
            }

            @Override
            void select(TimeSpinner spinner) {
                int hrIndex = spinner.getEditor().getText().indexOf(':');
                int minIndex = spinner.getEditor().getText().indexOf(':', hrIndex + 1);
                spinner.getEditor().selectRange(hrIndex + 1, minIndex);
            }
        },
        SECONDS {
            @Override
            DateTime increment(DateTime time, int steps) {
                return time.plusSeconds(steps);
            }

            @Override
            void select(TimeSpinner spinner) {
                int index = spinner.getEditor().getText().lastIndexOf(':');
                spinner.getEditor().selectRange(index + 1, spinner.getEditor().getText().length());
            }
        };

        abstract DateTime increment(DateTime time, int steps);

        abstract void select(TimeSpinner spinner);

        DateTime decrement(DateTime time, int steps) {
            return increment(time, -steps);
        }
    }

    // Property containing the current editing mode:

    org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss");

    StringConverter<DateTime> localTimeConverter = new StringConverter<DateTime>() {

        @Override
        public String toString(DateTime time) {
            return formatter.print(time);
        }

        @Override
        public DateTime fromString(String string) {
            String[] tokens = string.split(":");
            int hours = getIntField(tokens, 0);
            int minutes = getIntField(tokens, 1);
            int seconds = getIntField(tokens, 2);
            int totalSeconds = (hours * 60 + minutes) * 60 + seconds;
            return new DateTime()
                    .withHourOfDay((totalSeconds / 3600) % 24)
                    .withMinuteOfHour((totalSeconds / 60) % 60)
                    .withSecondOfMinute(seconds % 60);
        }

        private int getIntField(String[] tokens, int index) {
            if (tokens.length <= index || tokens[index].isEmpty()) {
                return 0;
            }
            return Integer.parseInt(tokens[index]);
        }

    };

    TextFormatter<DateTime> textFormatter = new TextFormatter<>(localTimeConverter, DateTime.now(), c -> {
        String newText = c.getControlNewText();
        if (newText.matches("[0-9]{0,2}:[0-9]{0,2}:[0-9]{0,2}")) {
            return c;
        }
        return null;
    });

    SpinnerValueFactory<DateTime> valueFactory = new SpinnerValueFactory<DateTime>() {
        {
            setConverter(localTimeConverter);
            setValue(time);
        }

        @Override
        public void decrement(int steps) {
            setValue(mode.get().decrement(getValue(), steps));
            mode.get().select(TimeSpinner.this);
        }

        @Override
        public void increment(int steps) {
            setValue(mode.get().increment(getValue(), steps));
            mode.get().select(TimeSpinner.this);
        }

    };

    private final ObjectProperty<Mode> mode = new SimpleObjectProperty<>(Mode.HOURS);

    public ObjectProperty<Mode> modeProperty() {
        return mode;
    }

    public final Mode getMode() {
        return modeProperty().get();
    }

    public final void setMode(Mode mode) {
        modeProperty().set(mode);
    }

    private DateTime time;

    public TimeSpinner(DateTime time) {


        setEditable(true);
        this.time = time;
        this.valueFactory.setValue(this.time);
        // Create a StringConverter for converting between the text in the
        // editor and the actual value:

        // The textFormatter both manages the text <-> LocalTime conversion,
        // and vetoes any edits that are not valid. We just make sure we have
        // two colons and only digits in between:

        // The spinner value factory defines innerIncrement and innerDecrement by
        // delegating to the current editing mode:

        this.setValueFactory(valueFactory);
        this.getEditor().setTextFormatter(textFormatter);

        // Update the mode when the user interacts with the editor.
        // This is a bit of a hack, e.g. calling spinner.getEditor().positionCaret()
        // could result in incorrect state. Directly observing the caretPostion
        // didn't work well though; getting that to work properly might be
        // a better approach in the long run.
        this.getEditor().addEventHandler(InputEvent.ANY, e -> {
            int caretPos = this.getEditor().getCaretPosition();
            int hrIndex = this.getEditor().getText().indexOf(':');
            int minIndex = this.getEditor().getText().indexOf(':', hrIndex + 1);
            if (caretPos <= hrIndex) {
                mode.set(Mode.HOURS);
            } else if (caretPos <= minIndex) {
                mode.set(Mode.MINUTES);
            } else {
                mode.set(Mode.SECONDS);
            }
        });

        // When the mode changes, select the new portion:
        mode.addListener((obs, oldMode, newMode) -> newMode.select(this));

    }


    public TimeSpinner() {
        this(DateTime.now());
    }

    public void setTime(DateTime time) {
        this.time = time;
        valueFactory.setValue(this.time);
    }

}
