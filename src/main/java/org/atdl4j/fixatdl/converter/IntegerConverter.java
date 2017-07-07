package org.atdl4j.fixatdl.converter;

import org.atdl4j.fixatdl.model.core.IntT;
import org.atdl4j.fixatdl.model.core.ParameterT;

import java.math.BigInteger;

/**
 * Created by sainik on 01/05/17.
 */
public class IntegerConverter implements TypeConverter<BigInteger, ParameterT> {

    private ParameterT parameterT;

    public IntegerConverter(ParameterT parameterT) {
        this.parameterT = parameterT;
    }

    //@Override
    public BigInteger convertParameterConstToComparable() {
        Object aParameterValue = getConstFieldOfParam();

        if (aParameterValue instanceof BigInteger) {
            return (BigInteger) aParameterValue;
        } else if (aParameterValue instanceof Integer) {
            return new BigInteger(((Integer) aParameterValue).toString());
        } else if (aParameterValue instanceof String) {
            String str = (String) aParameterValue;
            if ((str == null) || (str.trim().length() == 0)) {
                return null;
            } else {
                try {
                    // -- Trim leading and/or trailing spaces --
                    str = str.trim();
                    return new BigInteger(str);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid Integer Number Format: [" + str + "] for Parameter: " + this.parameterT.getName());
                }
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
        BigInteger tempBigInteger = convertParameterConstToComparable();
        if (tempBigInteger != null) {
            return tempBigInteger.toString();
        } else {
            return null;
        }
    }

    //@Override
//    public Object convertFixWireValueToParameterConst(String aFixWireValue) {
//        if (aFixWireValue != null) {
//            String str = (String) aFixWireValue;
//            if ((str == null) || (str.trim().length() == 0)) {
//
//
//                //return null;
//            } else {
//                try {
//                    // -- Trim leading and/or trailing spaces --
//                    str = str.trim();
//                    new BigInteger(str);
//                } catch (NumberFormatException e) {
//                    throw new NumberFormatException("Invalid Integer Number Format: [" + str + "] for Parameter: " + parameterT.getName());
//                }
//            }
//        } else {
//
//            //return null;
//        }
//        return null;
//        // TODO set the value later
//    }


    @Override
    public Object convertControlValueToParameterValue(Object aValue) {
        Object obj = DatatypeConverter
                .convertValueToDatatype(aValue,
                        getParameterDatatype(BigInteger.class));
        if (getParameter() != null) {
            if (obj != null && parameterT instanceof IntT) {
                return ((BigInteger) obj).intValue();
            }
        }
        return obj;
    }

//    @Override
//    public BigInteger convertParameterValueToControlValue(Object aValue, ControlT aControl) {
//        Object tempValue = adjustParameterValueForEnumRefValue(aValue, getParameter(), aControl);
//
//
//        return DatatypeConverter.convertValueToBigIntegerDatatype(tempValue);
//    }

    //@Override
    public BigInteger convertControlValueToControlComparable(Object aValue) {
        if (aValue instanceof BigInteger) {
            return (BigInteger) aValue;
        } else if (aValue instanceof Integer) {
            return new BigInteger(((Integer) aValue).toString());
        } else if (aValue instanceof String) {
            String str = (String) aValue;
            if ((str == null) || (str.trim().length() == 0)) {
                return null;
            } else {
                try {
                    // -- Trim leading and/or trailing spaces --
                    str = str.trim();
                    return new BigInteger(str);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid Integer Number Format: [" + str + "] for Parameter: " + parameterT.getName());
                }
            }

        } else if (aValue instanceof Boolean) {
            Boolean bool = (Boolean) aValue;
            return new BigInteger(bool ? "1" : "0");
        } else {
            return null;
        }
    }

    @Override
    public BigInteger convertStringToControlValue(String aString) {


        return convertControlValueToControlComparable(aString);
    }
}
