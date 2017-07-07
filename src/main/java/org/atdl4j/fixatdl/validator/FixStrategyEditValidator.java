package org.atdl4j.fixatdl.validator;

import org.atdl4j.fixatdl.model.validation.StrategyEditT;

import java.util.List;

public interface FixStrategyEditValidator {

    List<String> validateStrategyEditRuleAndGetErrorMessage();

    void setStrategyEditTS(List<StrategyEditT> editTS);

}
