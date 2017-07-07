package org.atdl4j.fixatdl.validator;

import org.atdl4j.fixatdl.evaluator.FixAtdlEditEvaluator;
import org.atdl4j.fixatdl.evaluator.RecursiveFixAtdlEditEvaluator;
import org.atdl4j.fixatdl.model.core.ParameterT;
import org.atdl4j.fixatdl.model.validation.StrategyEditT;
import javafx.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class FixStrategyEditValidatorImpl implements FixStrategyEditValidator {

    private FixAtdlEditEvaluator FixAtdlEditEvaluator;
    private List<StrategyEditT> strategyEditTS;
    private ParameterNameToConstValueCachedMap fieldToComparableMapperParameterCache;

    public FixStrategyEditValidatorImpl(List<ParameterT> parameterTS) {
        assert (strategyEditTS != null && parameterTS != null);
        this.fieldToComparableMapperParameterCache = new ParameterNameToConstValueCachedMap(parameterTS);
        this.FixAtdlEditEvaluator = new RecursiveFixAtdlEditEvaluator(this.fieldToComparableMapperParameterCache);
    }

    public void setStrategyEditTS(List<StrategyEditT> editTS) {
        this.strategyEditTS = editTS;
    }

    @Override
    public List<String> validateStrategyEditRuleAndGetErrorMessage() {
        return this.strategyEditTS.stream()
                .map(strategyEditT -> new Pair<>(this.FixAtdlEditEvaluator
                        .validate(strategyEditT.getEdit()), strategyEditT.getErrorMessage()))
                .filter(booleanStringPair -> !booleanStringPair.getKey())
                .map(booleanStringPair -> booleanStringPair.getValue())
                .collect(Collectors.toList());
    }
}
