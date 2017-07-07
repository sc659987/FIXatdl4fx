package org.atdl4j.fixatdl.validator;

import org.atdl4j.fixatdl.model.core.*;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class FixParameterValidatorImpl implements FixParameterValidator {

    private List<ParameterT> parameterTS;

    private static final String REQUIRED = "required";

    public FixParameterValidatorImpl(List<ParameterT> parameterTS) {
        this.parameterTS = parameterTS;
    }

    @Override
    public List<String> validateParameter() {
        return this.parameterTS.parallelStream().map(parameterT -> {
            if (parameterT instanceof DataT) {
                DataT dataT = (DataT) parameterT;

                if (dataT.getConstValue() == null)
                    return dataT.getUse().value().equals(REQUIRED) ? dataT.getName() + " is required" : null;

                BigInteger length = new BigInteger(String.valueOf(dataT.getConstValue().length()));
                if ((dataT.getMinLength() != null && dataT.getMinLength().compareTo(length) > 0)
                        || (dataT.getMaxLength() != null && dataT.getMaxLength().compareTo(length) < 0))
                    return parameterT.getName() + " is out of range. Min :" + dataT.getMinLength() + "Max : " + dataT.getMaxLength();

            } else if (parameterT instanceof StringT) {
                StringT stringT = (StringT) parameterT;

                if (stringT.getConstValue() == null)
                    return stringT.getUse().value().equals(REQUIRED) ? stringT.getName() + " is required" : null;

                BigInteger length = new BigInteger(String.valueOf(((StringT) parameterT).getConstValue().length()));
                if ((stringT.getMinLength() != null && stringT.getMinLength().compareTo(length) > 0)
                        || (stringT.getMaxLength() != null && stringT.getMaxLength().compareTo(length) < 0))
                    return parameterT.getName() + " is out of range. Min :" + stringT.getMinLength() + "Max : " + stringT.getMaxLength();

            } else if (parameterT instanceof UTCTimeOnlyT) {
                UTCTimeOnlyT utcTimeOnlyT = (UTCTimeOnlyT) parameterT;

                if (utcTimeOnlyT.getConstValue() == null)
                    return utcTimeOnlyT.getUse().value().equals(REQUIRED) ? utcTimeOnlyT.getName() + " is required" : null;

                if ((utcTimeOnlyT.getMinValue() != null && utcTimeOnlyT.getMinValue().compare(utcTimeOnlyT.getConstValue()) > 0)
                        || (utcTimeOnlyT.getMaxValue() != null && utcTimeOnlyT.getMaxValue().compare(utcTimeOnlyT.getConstValue()) < 0))
                    return parameterT.getName() + " is out of range. Min :" + utcTimeOnlyT.getMinValue() + "Max : " + utcTimeOnlyT.getMaxValue();

            } else if (parameterT instanceof TZTimestampT) {
                TZTimestampT tzTimestampT = (TZTimestampT) parameterT;

                if (tzTimestampT.getConstValue() == null)
                    return tzTimestampT.getUse().value().equals(REQUIRED) ? tzTimestampT.getName() + " is required" : null;

                if ((tzTimestampT.getMinValue() != null && tzTimestampT.getMinValue().compare(tzTimestampT.getConstValue()) > 0)
                        || (tzTimestampT.getMaxValue() != null && tzTimestampT.getMaxValue().compare(tzTimestampT.getConstValue()) < 0))
                    return parameterT.getName() + " is out of range. Min :" + tzTimestampT.getMinValue() + "Max : " + tzTimestampT.getMaxValue();

            } else if (parameterT instanceof LocalMktDateT) {
                LocalMktDateT localMktDateT = (LocalMktDateT) parameterT;

                if (localMktDateT == null)
                    return localMktDateT.getUse().value().equals(REQUIRED) ? localMktDateT.getName() + " is required" : null;

                if ((localMktDateT.getMinValue() != null && localMktDateT.getMinValue().compare(localMktDateT.getConstValue()) > 0)
                        || (localMktDateT.getMaxValue() != null && localMktDateT.getMaxValue().compare(localMktDateT.getConstValue()) < 0))
                    return parameterT.getName() + " is out of range. Min :" + localMktDateT.getMinValue() + "Max : " + localMktDateT.getMaxValue();

            } else if (parameterT instanceof IntT) {
                IntT intT = (IntT) parameterT;

                if (intT.getConstValue() == null)
                    return intT.getUse().value().equals(REQUIRED) ? intT.getName() + " is required" : null;

                if ((intT.getMinValue() != null && intT.getMinValue().compareTo(intT.getConstValue()) > 0)
                        || (intT.getMaxValue() != null && intT.getMaxValue().compareTo(intT.getConstValue()) < 0))
                    return parameterT.getName() + " is out of range. Min :" + intT.getMinValue() + "Max : " + intT.getMaxValue();

            } else if (parameterT instanceof MultipleStringValueT) {
                MultipleStringValueT multipleStringValueT = (MultipleStringValueT) parameterT;

                if (multipleStringValueT.getConstValue() == null)
                    return multipleStringValueT.getUse().value().equals(REQUIRED) ? multipleStringValueT.getName() + " is required" : null;

                if ((multipleStringValueT.getMinLength() != null
                        && multipleStringValueT.getMinLength().intValue() > multipleStringValueT.getConstValue().length())
                        || (multipleStringValueT.getMaxLength() != null
                        || multipleStringValueT.getMaxLength().intValue() < multipleStringValueT.getConstValue().length()))
                    return parameterT.getName() + " is out of range. Min :" + multipleStringValueT.getMinLength() + "Max : "
                            + multipleStringValueT.getMaxLength();

            } else if (parameterT instanceof TZTimeOnlyT) {
                TZTimeOnlyT tzTimeOnlyT = (TZTimeOnlyT) parameterT;

                if (tzTimeOnlyT.getConstValue() == null)
                    return tzTimeOnlyT.getUse().value().equals(REQUIRED) ? tzTimeOnlyT.getName() + " is required" : null;

                if ((tzTimeOnlyT.getMinValue() != null && tzTimeOnlyT.getMinValue().compare(tzTimeOnlyT.getConstValue()) > 0)
                        || (tzTimeOnlyT.getMaxValue() != null && tzTimeOnlyT.getMaxValue().compare(tzTimeOnlyT.getConstValue()) < 0))
                    return parameterT.getName() + " is out of range. Min :" + tzTimeOnlyT.getMinValue() + "Max : " + tzTimeOnlyT.getMaxValue();

            } else if (parameterT instanceof UTCDateOnlyT) {
                UTCDateOnlyT utcDateOnlyT = (UTCDateOnlyT) parameterT;

                if (utcDateOnlyT.getConstValue() == null)
                    return utcDateOnlyT.getUse().value().equals(REQUIRED) ? utcDateOnlyT.getName() + " is required" : null;

                if ((utcDateOnlyT.getMinValue() != null && utcDateOnlyT.getMinValue().compare(utcDateOnlyT.getConstValue()) > 0)
                        || (utcDateOnlyT.getMaxValue() != null && utcDateOnlyT.getMaxValue().compare(utcDateOnlyT.getConstValue()) < 0))
                    return parameterT.getName() + " is out of range. Min :" + utcDateOnlyT.getMinValue() + "Max : " + utcDateOnlyT.getMaxValue();

            } else if (parameterT instanceof MultipleCharValueT) {
                MultipleCharValueT charValueT = (MultipleCharValueT) parameterT;

                if (charValueT.getConstValue() == null)
                    return charValueT.getUse().value().equals(REQUIRED) ? charValueT.getName() + " is required" : null;

                if ((charValueT.getMinLength() != null && charValueT.getConstValue().length() > charValueT.getMinLength().intValue())
                        || (charValueT.getMaxLength() == null && charValueT.getMaxLength().intValue() < charValueT.getConstValue().length()))
                    return parameterT.getName() + " is out of range. Min :" + charValueT.getMinLength() + "Max : " + charValueT.getMaxLength();

            } else if (parameterT instanceof FloatT) {
                FloatT floatT = (FloatT) parameterT;

                if (floatT.getConstValue() == null)
                    return floatT.getUse().value().equals(REQUIRED) ? floatT.getName() + " is required" : null;

                if ((floatT.getMinValue() != null && floatT.getMinValue().compareTo(floatT.getConstValue()) > 0)
                        || (floatT.getMaxValue() != null && floatT.getMaxValue().compareTo(floatT.getConstValue()) < 0))
                    return parameterT.getName() + " is out of range. Min :" + floatT.getMinValue() + "Max : " + floatT.getMaxValue();

            } else if (parameterT instanceof PriceT) {
                PriceT priceT = (PriceT) parameterT;

                if (priceT.getConstValue() == null)
                    return priceT.getUse().value().equals(REQUIRED) ? priceT.getName() + " is required" : null;

                if ((priceT.getMinValue() != null && priceT.getMinValue().compareTo(priceT.getConstValue()) > 0)
                        || (priceT.getMaxValue() != null && priceT.getMaxValue().compareTo(priceT.getConstValue()) < 0))
                    return parameterT.getName() + " is out of range. Min :" + priceT.getMinValue() + "Max : " + priceT.getMaxValue();

            } else if (parameterT instanceof AmtT) {
                AmtT amtT = (AmtT) parameterT;

                if (amtT.getConstValue() == null)
                    return amtT.getUse().value().equals(REQUIRED) ? amtT.getName() + " is required" : null;

                if ((amtT.getMinValue() != null && amtT.getMinValue().compareTo(amtT.getConstValue()) > 0)
                        || (amtT.getMaxValue() != null && amtT.getMaxValue().compareTo(amtT.getConstValue()) < 0))
                    return parameterT.getName() + " is out of range. Min :" + amtT.getMinValue() + "Max : " + amtT.getMaxValue();

            } else if (parameterT instanceof PercentageT) {
                PercentageT percentageT = (PercentageT) parameterT;

                if (percentageT.getConstValue() == null)
                    return percentageT.getUse().value().equals(REQUIRED) ? percentageT.getName() + " is required" : null;

                if ((percentageT.getMinValue() != null && percentageT.getMinValue().compareTo(percentageT.getConstValue()) > 0)
                        || (percentageT.getMaxValue() != null && percentageT.getMaxValue().compareTo(percentageT.getConstValue()) < 0))
                    return parameterT.getName() + " is out of range. Min :" + percentageT.getMinValue() + "Max : " + percentageT.getMaxValue();

            } else if (parameterT instanceof QtyT) {
                QtyT qtyT = (QtyT) parameterT;

                if (qtyT.getConstValue() == null)
                    return qtyT.getUse().value().equals(REQUIRED) ? qtyT.getName() + " is required" : null;

                if ((qtyT.getMinValue() != null && qtyT.getMinValue().compareTo(qtyT.getConstValue()) > 0)
                        || (qtyT.getMaxValue() != null && qtyT.getMaxValue().compareTo(qtyT.getConstValue()) < 0))
                    return parameterT.getName() + " is out of range. Min :" + qtyT.getMinValue() + "Max : " + qtyT.getMaxValue();

            } else if (parameterT instanceof PriceOffsetT) {
                PriceOffsetT priceOffsetT = (PriceOffsetT) parameterT;
                if (priceOffsetT.getConstValue() == null)
                    return priceOffsetT.getUse().value().equals(REQUIRED) ? priceOffsetT.getName() + " is required" : null;
                if ((priceOffsetT.getMinValue() != null && priceOffsetT.getMinValue().compareTo(priceOffsetT.getConstValue()) > 0)
                        || (priceOffsetT.getMaxValue() != null && priceOffsetT.getMaxValue().compareTo(priceOffsetT.getConstValue()) < 0))
                    return parameterT.getName() + " is out of range. Min :" + priceOffsetT.getMinValue() + "Max : " + priceOffsetT.getMaxValue();

            } else if (parameterT instanceof UTCTimestampT) {
                UTCTimestampT utcTimestampT = (UTCTimestampT) parameterT;
                if (utcTimestampT.getConstValue() == null)
                    return utcTimestampT.getUse().value().equals(REQUIRED) ? utcTimestampT.getName() + " is required" : null;
                if ((utcTimestampT.getMinValue() != null && utcTimestampT.getMinValue().compare(utcTimestampT.getConstValue()) < 0)
                        || (utcTimestampT.getMaxValue() != null && utcTimestampT.getMaxValue().compare(utcTimestampT.getConstValue()) > 0))
                    return parameterT.getName() + " is out of range. Min :" + utcTimestampT.getMinValue() + "Max : " + utcTimestampT.getMaxValue();

            }
            return null;
        }).collect(Collectors.toList());
    }

}
