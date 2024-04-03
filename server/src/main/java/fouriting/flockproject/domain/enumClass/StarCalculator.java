package fouriting.flockproject.domain.enumClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum StarCalculator {
    MAKE_NEGATIVE((target, coef) -> (target * coef)),
    CALCULATE_DIFFERENCE((source, target) -> (target - source));

    private final BiFunction<Double, Double, Double> expression;
    public Double calculate(Double source, Double target){
        return expression.apply(source, target);
    }
}