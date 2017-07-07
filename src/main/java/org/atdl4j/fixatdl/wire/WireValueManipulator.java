package org.atdl4j.fixatdl.wire;

import org.atdl4j.fixatdl.converter.TypeConverter;
import org.atdl4j.fixatdl.converter.TypeConverterRepo;
import org.atdl4j.fixatdl.model.core.StrategiesT;
import org.atdl4j.fixatdl.model.core.StrategyT;

public class WireValueManipulator implements WireValueGenerator, WireValueInterpreter {

    // -- Repeating Group count --
    public static final String TAG_NO_STRATEGY_PARAMETERS = "957";

    public static final String TAG_STRATEGY_PARAMETER_NAME = "958";

    public static final String TAG_STRATEGY_PARAMETER_TYPE = "959";

    public static final String TAG_STRATEGY_PARAMETER_VALUE = "960";

    private static final char DELIMITER = '|';

    public static final char EQUAL = '=';

    private WireValueManipulator() {
    }

    private static WireValueManipulator _singleTon;

    public static synchronized WireValueManipulator getInstance() {
        return _singleTon == null ? _singleTon = new WireValueManipulator() : _singleTon;
    }


    public String generateWireValue(StrategiesT strategies, StrategyT selectedStrategy) {
        final StringBuilder wireBuilder = new StringBuilder();
        final CountIncrement repeatCount = new CountIncrement();
        selectedStrategy.getParameter().forEach(parameterT -> {
            TypeConverter<?, ?> typeConverter = TypeConverterRepo.get(parameterT);
            if (typeConverter != null && typeConverter.convertParameterConstToFixWireValue() != null) {
                if (strategies.isTag957Support())
                    wireBuilder.append(DELIMITER)
                            .append(TAG_STRATEGY_PARAMETER_NAME)
                            .append(EQUAL)
                            .append(parameterT.getName())
                            .append(DELIMITER)
                            .append(TAG_STRATEGY_PARAMETER_TYPE)
                            .append(EQUAL)
                            .append(parameterT.getTag959())
                            .append(DELIMITER)
                            .append(TAG_STRATEGY_PARAMETER_VALUE)
                            .append(EQUAL)
                            .append(typeConverter.convertParameterConstToFixWireValue());
                else
                    wireBuilder.append(DELIMITER).append(parameterT.getFixTag().intValue())
                            .append(EQUAL)
                            .append(typeConverter.convertParameterConstToFixWireValue());
                repeatCount.incrementByOne();
            }
        });

        String preambleWire = strategies.getStrategyIdentifierTag().toString()
                + EQUAL
                + selectedStrategy.getWireValue()
                + (strategies.isTag957Support() ? (DELIMITER + TAG_NO_STRATEGY_PARAMETERS + EQUAL + repeatCount.count)
                : strategies.getVersionIdentifierTag() != null ?
                DELIMITER + strategies.getVersionIdentifierTag().toString() + EQUAL + selectedStrategy.getVersion().toString() :
                "");
        return preambleWire + wireBuilder.toString();
    }



    @Override
    public void consumeWireString(String aa, StrategyT strategyT, StrategiesT strategiesT) {
        // TODO to be done

    }

    private static class CountIncrement {
        int count = 0;

        public void incrementByOne() {
            count++;
        }

        public int getValue() {
            return count;
        }
    }
}
