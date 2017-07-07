package org.atdl4j.fixatdl.converter;


import org.atdl4j.fixatdl.model.core.IntT;
import org.atdl4j.fixatdl.model.core.NumericT;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.core.PercentageT;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by sainik on 01/05/17.
 */
public class DecimalConverter implements TypeConverter<BigDecimal, ParameterT> {

    private ParameterT parameterT;

    public DecimalConverter(ParameterT parameterT) {
        this.parameterT = parameterT;
    }

    //@Override
    public BigDecimal convertParameterConstToComparable() {
        BigDecimal tempBigDecimal = null;
        Object aParameterValue = getConstFieldOfParam();
        if (aParameterValue instanceof BigDecimal) {
            tempBigDecimal = (BigDecimal) aParameterValue;
        } else if (aParameterValue instanceof String) {
            String str = (String) aParameterValue;
            if ((str == null) || (str.trim().length() == 0)) {
                return null;
            } else {
                try {
                    // -- Trim leading and/or trailing spaces --
                    str = str.trim();
                    tempBigDecimal = new BigDecimal(str);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid Decimal Number Format: [" + str + "] for Parameter: " + parameterT.getName());
                }
            }
        }
        return tempBigDecimal;
    }

    @Override
    public ParameterT getParameter() {
        return this.parameterT;
    }

    @Override
    public String convertParameterConstToFixWireValue() {
        BigDecimal tempBigDecimal = convertParameterConstToComparable();

        if ((tempBigDecimal != null) && (isParameterMultiplyBy100())) {
            // -- multiply the parameter value x100 for its wire value --
            tempBigDecimal = tempBigDecimal.scaleByPowerOfTen(2);
        }

        return toString(tempBigDecimal, getPrecision());
    }
//
//    //@Override
//    public Object convertFixWireValueToParameterConst(String aFixWireValue) {
//
//
//        return null;
//    }


    /**
     * Returns the value of Parameter.getMultiplyBy100() for PercentageT assuming
     * it has been set, otherwise returns false.
     *
     * @return
     */
    public boolean isParameterMultiplyBy100() {
        if (getParameter() instanceof PercentageT) {
            return ((PercentageT) getParameter()).isMultiplyBy100();
        } else {
            // -- Return null if Parameter does not have this value set --
            return false;
        }
    }


    /**
     * Returns the value of Parameter.getPrecision() for NumericT assuming it has been set,
     * otherwise returns null.
     *
     * @return
     */
    public BigInteger getPrecision() {
        if (getParameter() instanceof NumericT) {
            return ((NumericT) getParameter()).getPrecision();
        } else if (parameterT instanceof NumericT) {
            return ((NumericT) parameterT).getPrecision();
        } else {
            // -- Return null if Parameter does not have this value set --
            return null;
        }
    }


    /**
     * Applies precision rules, if specified, up to 13 decimal places.
     *
     * @param aValue
     * @param aPrecision
     * @return
     */
    public static String toString(BigDecimal aValue, BigInteger aPrecision) {
        if (aValue != null) {
            if (aPrecision != null) {
                switch (aPrecision.intValue()) {
                    case 0:
                        return DECIMAL_FORMAT_0dp.format(aValue.doubleValue());
                    case 1:
                        return DECIMAL_FORMAT_1dp.format(aValue.doubleValue());
                    case 2:
                        return DECIMAL_FORMAT_2dp.format(aValue.doubleValue());
                    case 3:
                        return DECIMAL_FORMAT_3dp.format(aValue.doubleValue());
                    case 4:
                        return DECIMAL_FORMAT_4dp.format(aValue.doubleValue());
                    case 5:
                        return DECIMAL_FORMAT_5dp.format(aValue.doubleValue());
                    case 6:
                        return DECIMAL_FORMAT_6dp.format(aValue.doubleValue());
                    case 7:
                        return DECIMAL_FORMAT_7dp.format(aValue.doubleValue());
                    case 8:
                        return DECIMAL_FORMAT_8dp.format(aValue.doubleValue());
                    case 9:
                        return DECIMAL_FORMAT_9dp.format(aValue.doubleValue());
                    case 10:
                        return DECIMAL_FORMAT_10dp.format(aValue.doubleValue());
                    case 11:
                        return DECIMAL_FORMAT_11dp.format(aValue.doubleValue());
                    case 12:
                        return DECIMAL_FORMAT_12dp.format(aValue.doubleValue());
                    case 13:
                        return DECIMAL_FORMAT_13dp.format(aValue.doubleValue());
                    default:
                        return aValue.toPlainString();
                }
            } else  // -- No precision expressed --
            {
                return DECIMAL_FORMAT_2dp.format(aValue.doubleValue());
            }
        }

        return null;
    }


    public static NumberFormat DECIMAL_FORMAT_0dp = new DecimalFormat("#;-#");
    public static NumberFormat DECIMAL_FORMAT_1dp = new DecimalFormat("#.0;-#.0");
    public static NumberFormat DECIMAL_FORMAT_2dp = new DecimalFormat("#.00;-#.00");
    public static NumberFormat DECIMAL_FORMAT_3dp = new DecimalFormat("#.000;-#.000");
    public static NumberFormat DECIMAL_FORMAT_4dp = new DecimalFormat("#.0000;-#.0000");
    public static NumberFormat DECIMAL_FORMAT_5dp = new DecimalFormat("#.00000;-#.00000");
    public static NumberFormat DECIMAL_FORMAT_6dp = new DecimalFormat("#.000000;-#.000000");
    public static NumberFormat DECIMAL_FORMAT_7dp = new DecimalFormat("#.0000000;-#.0000000");
    public static NumberFormat DECIMAL_FORMAT_8dp = new DecimalFormat("#.00000000;-#.00000000");
    public static NumberFormat DECIMAL_FORMAT_9dp = new DecimalFormat("#.000000000;-#.000000000");
    public static NumberFormat DECIMAL_FORMAT_10dp = new DecimalFormat("#.0000000000;-#.0000000000");
    public static NumberFormat DECIMAL_FORMAT_11dp = new DecimalFormat("#.00000000000;-#.00000000000");
    public static NumberFormat DECIMAL_FORMAT_12dp = new DecimalFormat("#.000000000000;-#.000000000000");
    public static NumberFormat DECIMAL_FORMAT_13dp = new DecimalFormat("#.0000000000000;-#.0000000000000");


    @Override
    public Object convertControlValueToParameterValue(Object aValue) {
        BigDecimal tempBigDecimal = DatatypeConverter.convertValueToBigDecimalDatatype(aValue);
        if ((tempBigDecimal != null) && (isControlMultiplyBy100())) {
            // -- divide Control's value by 100 --
            return tempBigDecimal.scaleByPowerOfTen(-2);
        } else {
            // -- aDatatypeIfNull=DATATYPE_BIG_DECIMAL --
            Object obj = DatatypeConverter.convertValueToDatatype(tempBigDecimal, getParameterDatatype(BigDecimal.class));

            if (getParameter() instanceof IntT) {
                if (obj instanceof BigInteger)
                    return ((BigInteger) obj).intValue();
            }

            return obj;
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
//    public BigDecimal convertParameterValueToControlValue(Object aValue, ControlT aControl) {
//
//        Object tempValue = adjustParameterValueForEnumRefValue(aValue, getParameter(), aControl);
//
//        BigDecimal tempBigDecimal = DatatypeConverter.convertValueToBigDecimalDatatype(tempValue);
//
//        if ((tempBigDecimal != null) && (isControlMultiplyBy100())) {
//            // -- multiply Control's value by 100 --
//            return tempBigDecimal.scaleByPowerOfTen(2);
//        } else {
//            return tempBigDecimal;
//        }
//
//
//    }

    //@Override
    public BigDecimal convertControlValueToControlComparable(Object aValue) {
        BigDecimal tempBigDecimal = null;

        if (aValue instanceof BigDecimal) {
            // 2/12/2010			return (BigDecimal) aValue;
            tempBigDecimal = (BigDecimal) aValue;
        } else if (aValue instanceof String) {
            String str = (String) aValue;
            if ((str == null) || (str.trim().length() == 0)) {
                return null;
            } else {
                try {
                    // -- Trim leading and/or trailing spaces --
                    str = str.trim();
                    tempBigDecimal = new BigDecimal(str);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid Decimal Number Format: [" + str + "] for Parameter: " + parameterT.getName());
                }
            }
        } else if (aValue instanceof Boolean) {
            Boolean bool = (Boolean) aValue;
            tempBigDecimal = new BigDecimal(bool ? 1 : 0);
        }

        if ((tempBigDecimal != null) && (isControlMultiplyBy100())) {
            return tempBigDecimal.scaleByPowerOfTen(2);
        } else {
            return tempBigDecimal;
        }
    }

    @Override
    public BigDecimal convertStringToControlValue(String aString) {
        return convertControlValueToControlComparable(aString);
    }

}
