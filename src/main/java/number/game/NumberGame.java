package number.game;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Random;
import java.util.Scanner;

public class NumberGame {

    private static final int TARGET = 21;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

//        генерация случайных чисел
        int[] numbers = new int[4];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(10);
        }

        System.out.println("Ваши числа: ");
        for (int number : numbers) {
            System.out.println(number + " ");
        }
        System.out.println();

//        ввод выражения от пользователя
        System.out.println("Введите ваш вариант ответа: ");
        String expression = scanner.nextLine();
//        проверка корректности выражения
        if (isValidExpression(expression, numbers)) {
            System.out.println("Некорректное выражение");
            return;
        }

        //    вычисление результата
        Integer result = calculateResult(expression);
        if (result == null) {
            System.out.println("Результат не является целым числом");
            return;
        }

        // Вывод результата
        System.out.println("Ваш результат: " + result);
        System.out.println("Ближайшее число к " + TARGET + " : " + getClosestNumber(result, TARGET));
    }

    //    проверка того, чтобы использовались все цифры из массива
    private static boolean isValidExpression(String expression, int[] numbers) {
//        String[] parts = expression.split("\\+");
//        if (parts.length != 2) {
//            return true;
//        }
//        int first = Integer.parseInt(parts[0]);
//        int second = Integer.parseInt(parts[1]);
//        return first + second != TARGET;
        String sanitizedExpression = expression.replaceAll("[^0-9+]", "");
        for (int number : numbers) {
            if (sanitizedExpression.contains(Integer.toString(number))) {
                return false;
            }
        }
        for (char c : expression.toCharArray()) {
            if (!Character.isDigit(c) && "+-*/()".indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

//    private static Integer calculateResult(String expression) {
//        try {
//            javax.script.ScriptEngineManager manager = new javax.script.ScriptEngineManager();
//            javax.script.ScriptEngine engine = manager.getEngineByName("JavaScript");
//            Object result = engine.eval(expression);
//
//            if (result instanceof Double) {
//                double doubleResult = (double) result;
//                if (doubleResult == Math.floor(doubleResult)) {
//                    return (int) doubleResult;
//                } else {
//                    return null;
//                }
//            } else if (result instanceof Integer) {
//                return (Integer) result;
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            System.out.println("Ошибка при вычислении выражения: " + e.getMessage());
//            return null;
//        }
//    }

    private static Integer calculateResult(String expression) {
        try {
            Expression e = new ExpressionBuilder(expression).build();
            double result = e.evaluate();
            if (result == Math.floor(result)) {
                return (int) result;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Ошибка при вычислении выражения: " + e.getMessage());
            return null;
        }
    }

    private static int getClosestNumber(int result, int target) {
        return Math.abs(result - target) < Math.abs(-result - target) ? result : -result;
    }
}
