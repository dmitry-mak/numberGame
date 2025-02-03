package number.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreCalculatorTest {

    @Test
    void calculateTotalPoints() {
        ScoreCalculator scoreCalculator = new ScoreCalculator(21, 100, 90);

        int actual = scoreCalculator.calculateTotalPoints(21, 91);
        int nexpected = 100;

        Assertions.assertEquals(nexpected, actual);
    }
}