package number.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NumberGameTest {

    @Test
    public void testRandomNumbersGeneration() {
        Random random = new Random(0);
        int[] numbers = NumberGame.getRandomNumbers(random);
        Assertions.assertEquals(4, numbers.length);

        for (int number : numbers) {
            assertTrue(number >= 0 && number <= 10);
        }
    }

}