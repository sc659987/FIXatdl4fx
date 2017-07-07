package org.atdl4j.fixatdl.wire;

import org.atdl4j.fixatdl.model.core.StrategiesT;
import org.atdl4j.fixatdl.model.core.StrategyT;

public interface WireValueInterpreter {

    void consumeWireString(String aa, StrategyT strategyT, StrategiesT strategiesT);
}
