package fouriting.flockproject.domain.enumClass;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StarCalculatorTest {
    @Test
    @DisplayName(value = "값 계산이 잘 되는지 테스트")
    void 두_값_빼기(){
        // given
        Double result = StarCalculator.CALCULATE_DIFFERENCE.calculate(1.0, 2.0);

        // when then
        Assertions.assertEquals(result, (2.0 - 1.0), 0.0001);
    }

    @Test
    @DisplayName(value = "음수로 잘 변환되는지 테스트")
    void 음수로_만들기(){
        // given
        Double result = StarCalculator.MAKE_NEGATIVE.calculate(5.0, -1.0);

        // when then
        Assertions.assertEquals(result, (5.0 * -1.0), 0.0001);
    }
}