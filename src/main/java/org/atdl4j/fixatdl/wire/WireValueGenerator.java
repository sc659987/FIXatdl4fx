package org.atdl4j.fixatdl.wire;

import org.atdl4j.fixatdl.model.core.StrategiesT;
import org.atdl4j.fixatdl.model.core.StrategyT;

/****
 *
 */
public interface WireValueGenerator {

    String generateWireValue(StrategiesT strategies, StrategyT selectedStrategy);

}
