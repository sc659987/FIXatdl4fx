package org.atdl4j.fixatdl.converter;


import org.atdl4j.fixatdl.model.core.ParameterT;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BooleanConverter implements TypeConverter<Boolean, ParameterT> {

    private final ParameterT parameterT;

    public static final String BOOLEAN_FALSE = "N";
    public static final String BOOLEAN_TRUE = "Y";

    public BooleanConverter(ParameterT parameterT) {
        this.parameterT = parameterT;
    }
    // parameter related

    //@Override
    public Boolean convertParameterConstToComparable() {
        Object aParameterValue = getConstFieldOfParam();
        if (aParameterValue == null)
            return null;
        if (aParameterValue instanceof Boolean) {
            return (Boolean) aParameterValue;
        } else if (aParameterValue instanceof String) {
            String str = (String) aParameterValue;
            if (str.equalsIgnoreCase("true") || str.equals("1") || str.equals(BooleanConverter.BOOLEAN_TRUE)) {
                return new Boolean(true);
            } else if (str.equalsIgnoreCase("false") || str.equals("0") || str.equals(BooleanConverter.BOOLEAN_FALSE)) {
                return new Boolean(false);
            } else if (str.equals("")) {
                return null;
            } else {
                return new Boolean(false);
            }
        } else if (aParameterValue instanceof BigDecimal || aParameterValue instanceof BigInteger) {
            BigDecimal num = (BigDecimal) aParameterValue;
            if (num.intValue() == 1) {
                return new Boolean(true);
            } else if (num.intValue() == 0) {
                return new Boolean(false);
            } else {
                return new Boolean(false);
            }
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
        Boolean bool = convertParameterConstToComparable();
        if (bool != null) {
            return bool.booleanValue() ? BOOLEAN_TRUE : BOOLEAN_FALSE;
        } else return null;
    }

//    @Override
//    public Object convertFixWireValueToParameterConst(String aFixWireValue) {
//        return convertStringToParameterValue(aFixWireValue);
//    }

    protected Boolean convertStringToParameterValue(String aValue) {
        if (aValue != null) {
            String str = (String) aValue;
            if (str.equalsIgnoreCase("true") || str.equals("1") || str.equals(BOOLEAN_TRUE)) {
                return new Boolean(true);
            } else if (str.equalsIgnoreCase("false") || str.equals("0") || str.equals(BOOLEAN_FALSE)) {
                return new Boolean(false);
            } else if (str.equals("")) {
                return null;
            } else {
                return new Boolean(false);
            }
        } else {
            return null;
        }
    }


    // Control related

    @Override
    public Object convertControlValueToParameterValue(Object aValue) {
        return (Boolean) convertControlValueToControlComparable(aValue)
                ? BOOLEAN_TRUE : BOOLEAN_FALSE;
    }


    private String convertToBooleanParameterType(Object aValue) {

        return null;
    }


    //@Override
    public Boolean convertControlValueToControlComparable(Object aValue) {
        if (aValue == null)
            return null;

        if (aValue instanceof Boolean) {
            return (Boolean) aValue;
        } else if (aValue instanceof String) {
            String str = (String) aValue;
            if (str.equalsIgnoreCase("true") || str.equals("1") || str.equals(BooleanConverter.BOOLEAN_TRUE)) {
                return new Boolean(true);
            } else if (str.equalsIgnoreCase("false") || str.equals("0") || str.equals(BooleanConverter.BOOLEAN_FALSE)) {
                return new Boolean(false);
            } else if (str.equals("")) {
                return null;
            } else {
                return new Boolean(false);
            }
        } else if (aValue instanceof BigDecimal || aValue instanceof BigInteger) {
            BigDecimal num = (BigDecimal) aValue;
            if (num.intValue() == 1) {
                return new Boolean(true);
            } else if (num.intValue() == 0) {
                return new Boolean(false);
            } else {
                return new Boolean(false);
            }
        } else {
            return null;
        }
    }

    @Override
    public Boolean convertStringToControlValue(String aString) {
        return convertControlValueToControlComparable(aString);
    }


}
