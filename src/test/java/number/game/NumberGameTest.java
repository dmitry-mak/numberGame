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

    @Test
    void isValidNumberTest() {
        int[] numbers = new int[]{1, 2, 3, 4};
        String expression = "1+2+3+4";
        Assertions.assertTrue(NumberGame.isValidNumber(expression, numbers));
        String expression2 = "1+2+3+5";
        Assertions.assertFalse(NumberGame.isValidNumber(expression2, numbers));
        String expression3 = "1+1+2+2";
        Assertions.assertFalse(NumberGame.isValidNumber(expression3,numbers));
        String expression4 = "1+2+3";
        Assertions.assertFalse(NumberGame.isValidNumber(expression4,numbers));
        String expression5 = "1+2+3+4+4";
//        Assertions.assertFalse(NumberGame.isValidNumber(expression5,numbers));
    }

    @Test
    public void shouldUseOnlyFourNumbers (){
        int [] numbers = new int[] {1,2,3,4,};

        String expression = "1+2+3+4+4";
        Assertions.assertFalse(NumberGame.isValidNumber(expression,numbers));
    }

    @Test
    void isValidOperator() {
        int[] numbers = new int[]{1, 2, 3, 4};
        String expression = "1+2-3/4";
        Assertions.assertTrue(NumberGame.isValidOperator(expression, numbers));

        String expression2 = "(1+2)*3-4+5";
        Assertions.assertTrue(NumberGame.isValidOperator(expression2, numbers));

        String expression3 = "1+2:3/4+5";
        Assertions.assertFalse(NumberGame.isValidOperator(expression3, numbers));
    }
}