package org.atdl4j.fixatdl.ui.fx8.customelement;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by sainik on 4/27/17.
 */
public class DateSpinner extends Spinner<DateTime> {

    public enum Mode {
        DAY {
            @Override
            DateTime increment(DateTime date, int steps) {
                return date.plusDays(steps);
            }

            @Override
            void select(DateSpinner spinner) {
                int index = spinner.getEditor().getText().indexOf('.');
                spinner.getEditor().selectRange(0, index);

            }
        },
        MONTH {
            @Override
            DateTime increment(DateTime date, int steps) {
                return date.plusMonths(steps);
            }

            @Override
            void select(DateSpinner spinner) {
                int dayIndex = spinner.getEditor().getText().indexOf('.');
                int monthIndex = spinner.getEditor().getText().indexOf('.', dayIndex + 1);
                spinner.getEditor().selectRange(dayIndex + 1, monthIndex);
            }
        },
        YEAR {
            @Override
            DateTime increment(DateTime date, int steps) {
                return date.plusYears(steps);
            }

            @Override
            void select(DateSpinner spinner) {
                int index = spinner.getEditor().getText().lastIndexOf('.');
                spinner.getEditor().selectRange(index + 1, spinner.getEditor().getText().length());
            }
        };


        abstract DateTime increment(DateTime date, int steps);

        abstract void select(DateSpinner spinner);

        DateTime decrement(DateTime date, int steps) {
            return increment(date, -steps);
        }
    }


    org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy");


    StringConverter<DateTime> localDateConverter = new StringConverter<DateTime>() {
        @Override
        public String toString(DateTime date) {
            return formatter.print(date);
        }

        @Override
        public DateTime fromString(String string) {
            String[] tokens = string.split("\\.");
            int day = getIntField(tokens, 0);
            int month = getIntField(tokens, 1);
            int year = getIntField(tokens, 2);
            return new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(day);

        }

        private int getIntField(String[] tokens, int index) {
            if (tokens.length <= index || tokens[index].isEmpty()) {
                return 0;
            }
            return Integer.parseInt(tokens[index]);
        }
    };

    TextFormatter<DateTime> textFormatter = new TextFormatter<>(localDateConverter, DateTime.now(), c -> {
        String newText = c.getControlNewText();
        if (newText.matches("[0-9]{0,2}.[0-9]{0,2}.[0-9]{4}")) {
            return c;
        }
        return null;
    });

    SpinnerValueFactory<DateTime> valueFactory = new SpinnerValueFactory<DateTime>() {

        {
            setConverter(localDateConverter);
            setValue(date);
        }

        @Override
        public void decrement(int steps) {
            setValue(mode.get().decrement(getValue(), steps));
            mode.get().select(DateSpinner.this);
        }

        @Override
        public void increment(int steps) {
            setValue(mode.get().increment(getValue(), steps));
            mode.get().select(DateSpinner.this);
        }

    };

    private DateTime date;

    private final ObjectProperty<Mode> mode = new SimpleObjectProperty<Mode>(Mode.DAY);

    public ObjectProperty<DateSpinner.Mode> modeProperty() {
        return mode;
    }

    public final DateSpinner.Mode getMode() {
        return modeProperty().get();
    }

    public final void setMode(DateSpinner.Mode mode) {
        modeProperty().set(mode);
    }

    public void setTime(DateTime date) {
        this.date = date;
        valueFactory.setValue(this.date);
    }


    public DateSpinner(DateTime date) {
        setEditable(true);
        this.date = date;
        this.valueFactory.setValue(this.date);

        this.setValueFactory(valueFactory);
        this.getEditor().setTextFormatter(textFormatter);

        this.getEditor().addEventHandler(javafx.scene.input.InputEvent.ANY, e -> {
            int caretPos = this.getEditor().getCaretPosition();
            int dayIndex = this.getEditor().getText().indexOf('.');
            int monthIndex = this.getEditor().getText().indexOf('.', dayIndex + 1);
            if (caretPos <= dayIndex) {
                mode.set(Mode.DAY);
            } else if (caretPos <= monthIndex) {
                mode.set(Mode.MONTH);
            } else {
                mode.set(Mode.YEAR);
            }
        });

        mode.addListener((observable, oldValue, newValue) -> newValue.select(this));


    }


}
