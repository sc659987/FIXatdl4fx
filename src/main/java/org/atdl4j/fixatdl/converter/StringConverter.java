package org.atdl4j.fixatdl.converter;

import org.atdl4j.fixatdl.model.core.*;

import java.math.BigDecimal;

/**
 * Created by sainik on 01/05/17.
 */
public class StringConverter implements TypeConverter<String, ParameterT> {

    private ParameterT parameterT;

    public StringConverter(ParameterT parameterT) {
        this.parameterT = parameterT;
    }

    //@Override
    public String convertParameterConstToComparable() {
        Object value = getConstFieldOfParam();
        return (value == null || "".equals(value)) ? null : value.toString();
    }

    @Override
    public ParameterT getParameter() {
        return this.parameterT;
    }

    @Override
    public String convertParameterConstToFixWireValue() {
        String str = convertParameterConstToComparable();
        if (str != null) {
            if (getParameter() instanceof MultipleCharValueT && ((MultipleCharValueT) getParameter()).isInvertOnWire()) {
                return invertOnWire(str);
            } else if (getParameter() instanceof MultipleStringValueT && ((MultipleStringValueT) getParameter()).isInvertOnWire()) {
                return invertOnWire(str);
            } else {
                return str;
            }
        } else {
            return null;
        }
    }

    //@Override
//    public Object convertFixWireValueToParameterConst(String aFixWireValue) {
//        String str = convertStringToParameterValue(aFixWireValue);
//        if (getParameter() instanceof MultipleCharValueT && ((MultipleCharValueT) getParameter()).isInvertOnWire()) {
//            invertOnWire(str);
//        } else if (getParameter() instanceof MultipleStringValueT && ((MultipleStringValueT) getParameter()).isInvertOnWire()) {
//            invertOnWire(str);
//        }
//        return null;
//        // TODO set to parameter
//    }

    protected String convertStringToParameterValue(String aValue) {
        if ((aValue != null) && (!"".equals(aValue))) {
            return aValue.toString();
        } else {
            return null;
        }
    }

    private static String invertOnWire(String text) {
        StringBuffer invertedString = new StringBuffer();

        int startIndex = text.lastIndexOf(" ");
        int endIndex = text.length();

        do {
            invertedString.append(text.substring(startIndex + 1, endIndex));
            if (startIndex == -1) {
                return invertedString.toString();
            } else {
                invertedString.append(" ");
            }
            endIndex = startIndex;
            startIndex = (text.substring(0, endIndex)).lastIndexOf(" ");
        }
        while (endIndex != -1);

        return invertedString.toString();
    }


    @Override
    public Object convertControlValueToParameterValue(Object aValue) {
        if ((aValue != null) && (isControlMultiplyBy100())) {
            BigDecimal tempBigDecimal;
            try {
                tempBigDecimal = DatatypeConverter.convertValueToBigDecimalDatatype(aValue);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid Decimal Number Format: [" + aValue + "] for Parameter: " + parameterT.getName());
            }
            // -- Divide Control's value by 100 --
            tempBigDecimal = tempBigDecimal.scaleByPowerOfTen(-2);
            return tempBigDecimal;
        } else {
            Object object = DatatypeConverter.convertValueToDatatype(aValue, getParameterDatatype(String.class));
            if (object != null && getParameter() instanceof IntT)
                return ((Number) object).intValue();
            return object;
        }
    }

    public boolean isControlMultiplyBy100() {
        if (getParameter() instanceof PercentageT) {
            return true;
        } else if (getParameter() instanceof PercentageT) {
            return true;
        } else {
            return false;
        }
    }


//    @Override
//    public String convertParameterValueToControlValue(Object aValue, ControlT aControl) {
//        Object tempValue = adjustParameterValueForEnumRefValue(aValue, getParameter(), aControl);
//
//        if ((tempValue == null) || ("".equals(tempValue))) {
//            return null;
//        }
//        // -- handle PercentageT getParameter() coming through with BigDecial (eg from Load FIX Message) --
//        else if ((tempValue != null) && (isControlMultiplyBy100())) {
//            BigDecimal tempBigDecimal;
//            try {
//                if (tempValue instanceof BigDecimal) {
//                    tempBigDecimal = (BigDecimal) tempValue;
//                } else {
//                    tempBigDecimal = new BigDecimal((String) tempValue);
//                }
//            } catch (NumberFormatException e) {
//                throw new NumberFormatException("Invalid Decimal Number Format: [" + aValue + "] for Parameter: " + getParameter().getName());
//            }
//
//            // -- Multiply Control's value by 100 --
//            tempBigDecimal = tempBigDecimal.scaleByPowerOfTen(2);
//            return tempBigDecimal.toString();
//        } else {
//            return DatatypeConverter.convertValueToStringDatatype(tempValue);
//
//        }
//    }

    //@Override
    public String convertControlValueToControlComparable(Object value) {
        return (value == null || "".equals(value)) ? null : value.toString();
    }

    @Override
    public String convertStringToControlValue(String aString) {
        return convertControlValueToControlComparable(aString);
    }


}
