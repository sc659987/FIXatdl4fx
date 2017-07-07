package org.atdl4j.fixatdl.ui.fx8.element;

import org.atdl4j.fixatdl.converter.DateTimeConverter;
import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.converter.TypeConverterRepo;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.core.UTCDateOnlyT;
import org.atdl4j.fixatdl.model.core.UTCTimestampT;
import org.atdl4j.fixatdl.model.layout.ClockT;
import org.atdl4j.fixatdl.ui.common.element.FixClockUiElement;
import org.atdl4j.fixatdl.ui.fx8.customelement.DateSpinner;
import org.atdl4j.fixatdl.ui.fx8.customelement.TimeSpinner;
import org.atdl4j.fixatdl.utils.Utils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.MonthDay;
import java.time.Year;
import java.util.Collections;
import java.util.List;

public class FxFixClockUiElement implements FixClockUiElement<Pane, DateTime> {

    private TimeSpinner timeSpinner;
    private DateSpinner dateSpinner;

    private GridPane gridPane;
    private ClockT clockT;
    private ParameterT parameterT;
    private ObjectProperty<String> controlIdEmitter = new SimpleObjectProperty<>();
    private int nextColumn = 0;

    private TypeConverter<?, ?> controlTTypeConverter;

    private boolean isInitializedOrHaveValue = false;

    @Override
    public Pane create() {
        if (this.clockT != null) {
            this.gridPane = new GridPane();

            this.controlTTypeConverter = TypeConverterRepo.createParameterTypeConverter(parameterT);

            if (!Utils.isEmptyString(this.clockT.getLabel())) {
                //this.gridPane.getColumnConstraints().addAll(FxUtils.getTwoColumnSameWidthForGridPane());
                this.gridPane.add(new Label(this.clockT.getLabel()), this.nextColumn++, 0);
            }

            this.gridPane.setHgap(3);


            //
            createClockByParameterType();
            initializeControl();
            setFieldValueToParameter(getValue(), parameterT);
            //this.dateSpinner = new DateSpinner(LocalDate.now());
            //this.gridPane.add(this.timeSpinner, this.nextColumn, 0);

            return this.gridPane;
        }
        return null;
    }

    @Override
    public void initializeControl() {
        // LOGIC clockT.getInitValueMode() if 0 then use clockT.getInitValue() otherwise use current time
        // if init value is supplied then have to use localMktTz
        if (this.clockT.getInitValue() != null && this.clockT.getLocalMktTz() != null) {
            // create joda Date time from xmlGregorianCalender with specified timezone
            DateTime initDateTime = DateTimeConverter
                    .convertXMLGregorianCalendarToDateTime(this.clockT.getInitValue(),
                            this.clockT.getLocalMktTz());
            // convert the specified date time to current time zone
            initDateTime = initDateTime.toDateTime(DateTimeZone.getDefault());
            // if init mode is 1 then adjust again
            if (this.clockT.getInitValueMode() == 1) {
                //if current time in current time zone is after initialized time converted to
                //current time zone then use current time zone value
                //DateTime currentTimeInSpecifiedTimeZone = new DateTime(DateTimeZone.forID(clockT.getLocalMktTz().value()));
                DateTime currentTime = new DateTime(DateTimeZone.getDefault());
                if (currentTime.isAfter(initDateTime)) {
                    initDateTime = currentTime;
                }
            }
            setValue(initDateTime);
        } else {
            setValue(new DateTime());
            isInitializedOrHaveValue = false;
        }
    }

    /***
     *
     */
    private void createClockByParameterType() {
        if (parameterT instanceof UTCTimestampT || parameterT instanceof UTCDateOnlyT) {
            this.dateSpinner = new DateSpinner(new DateTime()
                    .withYear(year(clockT.getInitValue()))
                    .withMonthOfYear(month(clockT.getInitValue()))
                    .withDayOfMonth(dayOfMonth(clockT.getInitValue())));

            this.dateSpinner.setOnMouseClicked(event -> {
                isInitializedOrHaveValue = true;
                setFieldValueToParameter(getValue(), parameterT);
            });
            this.gridPane.add(this.dateSpinner, this.nextColumn++, 0);
            if (parameterT instanceof UTCDateOnlyT)
                return;
        }
        this.timeSpinner = new TimeSpinner();
        this.timeSpinner.setOnMouseClicked(event -> {
            isInitializedOrHaveValue = true;
            setFieldValueToParameter(getValue(), parameterT);
        });
        this.gridPane.add(this.timeSpinner, this.nextColumn, 0);
    }

    private int year(XMLGregorianCalendar xmlGregorianCalendar) {
        return xmlGregorianCalendar == null ||
                xmlGregorianCalendar.getYear() == DatatypeConstants.FIELD_UNDEFINED ?
                Year.now().getValue() : xmlGregorianCalendar.getYear();
    }

    private int month(XMLGregorianCalendar xmlGregorianCalendar) {
        return xmlGregorianCalendar == null
                || xmlGregorianCalendar.getMonth() == DatatypeConstants.FIELD_UNDEFINED ?
                MonthDay.now().getMonth().getValue() : xmlGregorianCalendar.getMonth();
    }

    private int dayOfMonth(XMLGregorianCalendar xmlGregorianCalendar) {
        return xmlGregorianCalendar == null
                || xmlGregorianCalendar.getDay() == DatatypeConstants.FIELD_UNDEFINED ?
                MonthDay.now().getDayOfMonth() : xmlGregorianCalendar.getDay();
    }

    @Override
    public void setClockT(ClockT clockT) {
        this.clockT = clockT;
    }

    @Override
    public void setParameters(List<ParameterT> parameterTList) {
        if (parameterTList != null && parameterTList.size() > 0)
            this.parameterT = parameterTList.get(0);
    }

    @Override
    public List<ParameterT> getParameters() {
        List<ParameterT> parameterTS = Collections.emptyList();
        parameterTS.add(this.parameterT);
        return parameterTS;
    }

    @Override
    public ObjectProperty<String> listenChange() {
        return controlIdEmitter;
    }

    @Override
    public ClockT getControl() {
        return this.clockT;
    }

    /****
     * if date part is absent then consider date as undefined
     * if Time part is absent then set those field as undefined
     * Rest of the setting must be handled at the time of Data
     * type conversion
     * @return
     */
    @Override
    public DateTime getValue() {
        if (!isInitializedOrHaveValue)
            return null;
        DateTime dateTime = DateTime.now();
        if (this.dateSpinner != null && this.dateSpinner.getValue() != null) {
            DateTime dateSpinnerDateTime = this.dateSpinner.getValue();
            dateTime = dateTime.withDayOfMonth(dateSpinnerDateTime.getDayOfMonth())
                    .withMonthOfYear(dateSpinnerDateTime.getMonthOfYear())
                    .withYear(dateSpinnerDateTime.getYear());
        }
        if (this.timeSpinner != null && this.timeSpinner.getValue() != null) {
            DateTime timeSpinnerDateTime = this.timeSpinner.getValue();
            dateTime = dateTime.withHourOfDay(timeSpinnerDateTime.getHourOfDay())
                    .withMinuteOfHour(timeSpinnerDateTime.getMinuteOfHour())
                    .withSecondOfMinute(timeSpinnerDateTime.getSecondOfMinute());
        }
        return dateTime;
    }

    @Override
    public void setValue(DateTime s) {
        if (this.timeSpinner != null)
            this.timeSpinner.setTime(s);
        if (this.dateSpinner != null)
            this.dateSpinner.setTime(s);
        setFieldValueToParameter(s, parameterT);
        isInitializedOrHaveValue = true;
    }

    @Override
    public void makeVisible(boolean visible) {
        gridPane.setVisible(visible);
    }

    @Override
    public void makeEnable(boolean enable) {
        gridPane.setDisable(!enable);
        if (enable)
            initializeControl();
    }


    @Override
    public TypeConverter<?, ?> getControlTTypeConverter() {
        return this.controlTTypeConverter;
    }
}
