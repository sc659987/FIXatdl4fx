package org.atdl4j.fixatdl.converter;

import org.atdl4j.fixatdl.model.core.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory that creates the appropriate ParameterTypeConveter depending on the parameter
 * or creates the appropriate ControlTypeConveter depending upon the control type.
 * <p>
 * Note that all UI widgets in ATDL are strongly typed.
 */
public class TypeConverterRepo {


    private static final Map<String, TypeConverter<?, ?>> PARAMETER_NAME_TO_PARAMETER_T_TYPE_CONVERTER_MAP = new HashMap<>();


    public static TypeConverter<?, ?> createParameterTypeConverter(ParameterT parameterT) {
        TypeConverter<?, ?> typeConverter;
        if (parameterT != null) {
            typeConverter = PARAMETER_NAME_TO_PARAMETER_T_TYPE_CONVERTER_MAP.get(parameterT.getName());
            if (typeConverter != null)
                return typeConverter;
            typeConverter = get(parameterT);
            PARAMETER_NAME_TO_PARAMETER_T_TYPE_CONVERTER_MAP.put(parameterT.getName(), typeConverter);
            return typeConverter;
        }
        return null;
    }


    /*
     * Create adapter based on ParameterT
     */
    public static TypeConverter<?, ?> get(ParameterT parameter) {
        TypeConverter<?, ?> typeConverter = null;
        if (parameter instanceof StringT || parameter instanceof CharT || parameter instanceof MultipleCharValueT
                || parameter instanceof MultipleStringValueT || parameter instanceof CurrencyT || parameter instanceof ExchangeT
                || parameter instanceof DataT) {
            typeConverter = new StringConverter(parameter);
        } else if (parameter instanceof NumericT) {
            typeConverter = new DecimalConverter(parameter);
        } else if (parameter instanceof IntT || parameter instanceof NumInGroupT || parameter instanceof SeqNumT || parameter instanceof TagNumT || parameter instanceof LengthT) {
            typeConverter = new IntegerConverter(parameter);
        } else if (parameter instanceof BooleanT) {
            typeConverter = new BooleanConverter(parameter);
        } else if (parameter instanceof MonthYearT || parameter instanceof UTCTimestampT || parameter instanceof UTCTimeOnlyT || parameter instanceof LocalMktDateT || parameter instanceof UTCDateOnlyT) {
            typeConverter = new DateTimeConverter(parameter);
        }
        return typeConverter;
    }

    /*
     * Returns an Object that is an instanceof the Parameter's base data type
     * (eg String, BigDecimal, DateTime, etc)
     */
    // 3/12/2010 Scott Atwell added
    public static Class<?> getParameterDatatype(ParameterT aParameter) {
        if (aParameter instanceof StringT || aParameter instanceof CharT
                || aParameter instanceof MultipleCharValueT
                || aParameter instanceof MultipleStringValueT
                || aParameter instanceof CurrencyT
                || aParameter instanceof ExchangeT
                || aParameter instanceof DataT) {
            return String.class;
        } else if (aParameter instanceof NumericT) {
            return BigDecimal.class; // Float field
        } else if (aParameter instanceof IntT
                || aParameter instanceof NumInGroupT
                || aParameter instanceof SeqNumT
                || aParameter instanceof TagNumT
                || aParameter instanceof LengthT) {
            return BigInteger.class; // Integer field
        } else if (aParameter instanceof BooleanT) {
            return Boolean.class;
        } else if (aParameter instanceof MonthYearT) {
            return String.class;
        } else if (aParameter instanceof UTCTimestampT
                || aParameter instanceof UTCTimeOnlyT
                || aParameter instanceof LocalMktDateT
                || aParameter instanceof UTCDateOnlyT) {
            return XMLGregorianCalendar.class;
        } else {
            throw new IllegalArgumentException("Unsupported ParameterT type: "
                    + aParameter.getClass().getName());
        }
    }
}
