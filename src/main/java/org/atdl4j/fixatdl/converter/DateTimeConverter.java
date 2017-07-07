package org.atdl4j.fixatdl.converter;

import org.atdl4j.fixatdl.model.timezones.Timezone;
import org.atdl4j.fixatdl.model.core.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public class DateTimeConverter implements TypeConverter<DateTime, ParameterT> {

    private ParameterT parameterT;
    private Timezone timezone = null;

    public DateTimeConverter(ParameterT parameterT) {
        this.parameterT = parameterT;
        timezone = getLocalMktTz(parameterT);
    }


    private Timezone getLocalMktTz(ParameterT parameter) {
        if (parameter instanceof UTCTimestampT) {
            return ((UTCTimestampT) parameter).getLocalMktTz();
        } else if (parameter instanceof UTCTimeOnlyT) {
            return ((UTCTimeOnlyT) parameter).getLocalMktTz();
        }
        return null;
    }


    //@Override
    public DateTime convertParameterConstToComparable() {
        Object aValue = getConstFieldOfParam();
        if (aValue instanceof DateTime) {
            return (DateTime) aValue;
        } else if (aValue instanceof XMLGregorianCalendar) {
            return convertXMLGregorianCalendarToDateTime((XMLGregorianCalendar) aValue, timezone);
        } else {
            return null;
        }
    }

    @Override
    public ParameterT getParameter() {
        return this.parameterT;
    }

    @Override
    public String convertParameterConstToFixWireValue() {
        DateTime date = convertParameterConstToComparable();
        if (date != null) {
            DateTimeFormatter fmt = DateTimeFormat.forPattern(getFormatString());
            return fmt.withZone(DateTimeZone.UTC).print(date);
        } else {
            return null;
        }
    }

//    //@Override
//    public Object convertFixWireValueToParameterConst(String aFixWireValue) {
//        if (aFixWireValue != null) {
//            String str = (String) aFixWireValue;
//            String format = getFormatString();
//            DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
//            try {
//                if (getParameter() == null ||
//                        getParameter() instanceof UTCTimeOnlyT ||
//                        getParameter() instanceof UTCTimestampT) {
//                    DateTime tempDateTime = fmt.withZone(DateTimeZone.UTC).parseDateTime(str);
//                    return tempDateTime;
//                } else {
//                    return fmt.parseDateTime(str);
//                }
//            } catch (IllegalArgumentException e) {
//                throw new IllegalArgumentException("Unable to parse \"" + str + "\" with format \"" + format + "\"  Execption: " + e.getMessage());
//            }
//        }
//        return null;
//        // TODO Set to parameter
//    }

    private String getFormatString() {
        if (getParameter() != null) {
            if (getParameter() instanceof LocalMktDateT) {
                return "yyyyMMdd";
            } else if (getParameter() instanceof MonthYearT) {
                return "yyyyMM";
            } else if (getParameter() instanceof UTCDateOnlyT) {
                return "yyyyMMdd";
            } else if (getParameter() instanceof UTCTimeOnlyT) {
                return "HH:mm:ss";
            } else if (getParameter() instanceof UTCTimestampT) {
                return "yyyyMMdd-HH:mm:ss";
            }
            // TODO: Uncomment when TZTimestamp / TZTimeOnly becomes available
            /*
             * else if (getParameter() instanceof TZTimeOnlyT) { return "HH:mm:ssZZ"; }
			 * else if (getParameter() instanceof TZTimestampT) { return
			 * "yyyyMMdd-HH:mm:ssZZ"; }
			 */
        }
        return "yyyyMMdd-HH:mm:ss";
    }


    public static DateTime convertXMLGregorianCalendarToDateTime(XMLGregorianCalendar aXMLGregorianCalendar, Timezone aTimezone) {
        // -- DateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond) --
        int tempSubsecond = 0;
        makeValidCalender(aXMLGregorianCalendar, aTimezone);
        if (aXMLGregorianCalendar.getFractionalSecond() != null) {
            tempSubsecond = aXMLGregorianCalendar.getFractionalSecond().intValue();
        }

        DateTimeZone tempDateTimeZone = convertTimezoneToDateTimeZone(aTimezone);
        if (tempDateTimeZone == null) {
            tempDateTimeZone = DateTimeZone.getDefault();
        }

        return new DateTime(aXMLGregorianCalendar.getYear(),
                aXMLGregorianCalendar.getMonth(),
                aXMLGregorianCalendar.getDay(),
                aXMLGregorianCalendar.getHour(),
                aXMLGregorianCalendar.getMinute(),
                aXMLGregorianCalendar.getSecond(),
                tempSubsecond,
                tempDateTimeZone);
    }

    public static void makeValidCalender(final XMLGregorianCalendar aDailyValue, final Timezone aTimezone) {
        // -- Note that the XMLGregorianCalendar does not default to current month, day, year --
        if (aDailyValue != null) {
            // -- Init calendar date portion equal to "current date" local/default --
            DateTime tempDateTime = new DateTime();

            if (aTimezone != null) {
                DateTimeZone tempDateTimeZone = DateTimeZone.forID(aTimezone.value());
                if (tempDateTimeZone != null) {
                    int tempOffsetMillis = tempDateTimeZone.getOffset(System.currentTimeMillis());
                    // -- convert milliseconds to minutes --
                    aDailyValue.setTimezone(tempOffsetMillis / 60000);

                    // -- Set calendar date portion equal to "current date" of the Timezone --
                    // -- (eg Asian security trading in Japan during the morning of Feb 15 might be local of 9pm ET Feb 14.
                    // -- Want to ensure we use Feb 15, not Feb 14 if localMktTz is for Japan) --
                    tempDateTime = new DateTime(tempDateTimeZone);
                }
            }

            if (aDailyValue.getDay() == DatatypeConstants.FIELD_UNDEFINED)
                aDailyValue.setDay(tempDateTime.getDayOfMonth());
            if (aDailyValue.getMonth() == DatatypeConstants.FIELD_UNDEFINED)
                aDailyValue.setMonth(tempDateTime.getMonthOfYear());
            if (aDailyValue.getYear() == DatatypeConstants.FIELD_UNDEFINED)
                aDailyValue.setYear(tempDateTime.getYear());
        }

    }


    public static DateTimeZone convertTimezoneToDateTimeZone(Timezone aTimezone) {
        if (aTimezone != null) {
            return DateTimeZone.forID(aTimezone.value());
        } else {
            return null;
        }
    }


    @Override
    public Object convertControlValueToParameterValue(Object aValue) {
        if (aValue instanceof XMLGregorianCalendar) {
            return aValue;
        } else if (aValue instanceof DateTime) {
            return convertDateTimeToXMLGregorianCalendarToDateTime((DateTime) aValue);
        }
        return null;
    }


    private XMLGregorianCalendar convertDateTimeToXMLGregorianCalendarToDateTime(DateTime dateTime) {
        XMLGregorianCalendar xmlGregorianCalendar = null;
        try {
            DatatypeFactory dataTypeFactory = DatatypeFactory.newInstance();
            xmlGregorianCalendar = dataTypeFactory.newXMLGregorianCalendar(dateTime.toGregorianCalendar());
        } catch (DatatypeConfigurationException e) {
        }
        return xmlGregorianCalendar;
    }

//    @Override
//    public DateTime convertParameterValueToControlValue(Object aValue, ControlT aControl) {
//        Object tempValue = adjustParameterValueForEnumRefValue(aValue, getParameter(), aControl);
//        if (tempValue instanceof DateTime) {
//            return (DateTime) tempValue;
//        } else if (tempValue instanceof XMLGregorianCalendar) {
//            return convertXMLGregorianCalendarToDateTime((XMLGregorianCalendar) tempValue, timezone);
//        } else {
//            return null;
//        }
//    }

    //@Override
    public DateTime convertControlValueToControlComparable(Object aValue) {
        if (aValue instanceof DateTime) {
            return (DateTime) aValue;
        } else if (aValue instanceof XMLGregorianCalendar) {
            return convertXMLGregorianCalendarToDateTime((XMLGregorianCalendar) aValue, timezone);
        } else if (aValue instanceof String) {
            String str = (String) aValue;
            String format = getFormatString();
            DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
            try {
                if ((getParameter() instanceof UTCTimeOnlyT) || (getParameter() instanceof UTCTimestampT)) {
                    DateTime tempDateTime = fmt.parseDateTime(str);
                    return tempDateTime.withZone(DateTimeZone.UTC);
                }

				/*
                 * else if (getParameter() instanceof TZTimestamp || getParameter() instanceof
				 * TZTimeOnlyT) { return fmt.withOffsetParsed().parseDateTime(str);
				 * }
				 */
                else {
                    return fmt.parseDateTime(str);
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unable to parse \"" + str + "\" with format \"" + format + "\"  Exception: " + e.getMessage());
            }
        } else {
            return null;
        }
    }

    @Override
    public DateTime convertStringToControlValue(String aString) {
        return convertControlValueToControlComparable(aString);
    }

}
