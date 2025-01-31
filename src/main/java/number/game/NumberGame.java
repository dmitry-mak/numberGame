package number.game;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.*;

public class NumberGame {

    private static final int TARGET = 21;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

//        генерация случайных чисел
        int[] numbers = getRandomNumbers(random);
        printNumbers(numbers);

//        ввод выражения от пользователя
        System.out.println("Введите ваш вариант ответа: ");
        String expression = scanner.nextLine();

//        проверка корректности выражения
        if (!isValidExpression(expression, numbers)) {
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
    }

    //    проверка того, чтобы использовались все цифры из массива
//    public static boolean isValidExpression(String expression, int[] numbers) {
//        String sanitizedExpression = expression.replaceAll("[^0-9+]", "");
//        for (int number : numbers) {
//            if (sanitizedExpression.contains(Integer.toString(number))) {
//                return false;
//            }
//        }
//        for (char c : expression.toCharArray()) {
//            if (!Character.isDigit(c) && "+-*/()".indexOf(c) == -1) {
//                return false;
//            }
//        }
//        return true;
//    }

//    public static boolean isValidExpression(String expression, int[] numbers) {
//        return isValidNumber(expression, numbers) && isValidOperator(expression, numbers);
//    }

//    public static boolean isValidExpression(String expression, int[] numbers) {
//        Set<Integer> usedNumbers = new HashSet<>();
//        for (char c : expression.toCharArray()) {
//            if (Character.isDigit(c)) {
//                usedNumbers.add(Character.getNumericValue(c));
//            } else if ("+-*/() ".indexOf(c) == -1) {
//                return false;
//            }
//        }
//        for (int number : numbers) {
//            if (!usedNumbers.contains(number)) {
//                return false;
//            }
//        }
//        return true;
//    }

    public static boolean isValidExpression(String expression, int[] numbers) {
        List<String> numbersList = new ArrayList<>();
        for (int number : numbers) {
            numbersList.add(String.valueOf(number));
        }

        String sanitizedExpression = expression.replaceAll("[^\\d]", "");
        for (String number : numbersList) {
            if (sanitizedExpression.contains(number)) {
                sanitizedExpression = sanitizedExpression.replaceFirst(number, "");
            } else {
                return false;
            }
        }

        return sanitizedExpression.isEmpty();
    }

    //    Проверка, что в выражении используются только цифры, сгенерированные программой
//    public static boolean isValidNumber(String expression, int[] numbers) {
////        Set<Integer> usedNumbers = new HashSet<>();
//        Map<Integer, Integer> usedNumbers = new HashMap<>();
//        for (char c : expression.toCharArray()) {
//            if (Character.isDigit(c)) {
//                int digit = Character.getNumericValue(c);
//                usedNumbers.put(digit, usedNumbers.getOrDefault(digit, 0) + 1);
//            }
//        }
////        Проверка, что цифры, сгенерированные программой, используются хотя бы раз
//        for (int number : numbers) {
//            if (!usedNumbers.containsKey(number)) {
//                return false;
//            }
////            Проверка, что каждое из чисел не используется больше одного раза
//            if (usedNumbers.get(number) > 1) {
//                return false;
//            }
//        }
////        Проверка, что нет лишних цифр
//        for (Map.Entry<Integer, Integer> entry : usedNumbers.entrySet()) {
//            if (containsNumber(numbers, entry.getKey())) {
//                return false;
//            }
//            if (entry.getValue() > 1) {
//                return false;
//            }
//        }
//        return true;
//    }
//    public static boolean isValidNumber(String expression, int[] numbers) {
//        String sanitizedExpression = expression.replaceAll("[^\\d]", "");
//        Map<Integer, Integer> frequencyMap = new HashMap<>();
//        for (char digit : sanitizedExpression.toCharArray()) {
//            int parsedDigit = Character.getNumericValue(digit);
//            frequencyMap.put(parsedDigit, frequencyMap.getOrDefault(parsedDigit, 0) + 1);
//        }
//
//        for (int number : numbers) {
//            if (frequencyMap.getOrDefault(number, 0) != 1) {
//                return false;
//            }
//        }
//        for (int digit : frequencyMap.keySet()) {
//            if (!containsNumber(numbers, digit)) {
//                return false;
//            }
//        }
//        return true;
//    }
    public static boolean isValidNumber(String expression, int[] numbers) {
        // Сохраняем частоту использования сгенерированных чисел
        Map<Integer, Integer> numberCounts = new HashMap<>();
        for (int number : numbers) {
            numberCounts.put(number, numberCounts.getOrDefault(number, 0) + 1);
        }
        // Подсчет цифр в выражении
        Map<Integer, Integer> usedNumbers = new HashMap<>();
        for (char ch : expression.toCharArray()) {
            if (Character.isDigit(ch)) {
                int digit = Character.getNumericValue(ch);
                usedNumbers.put(digit, usedNumbers.getOrDefault(digit, 0) + 1);
            }
        }
        // Проверка использования всех чисел ровно один раз
        for (Map.Entry<Integer, Integer> entry : numberCounts.entrySet()) {
            if (!usedNumbers.containsKey(entry.getKey()) || !usedNumbers.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        // Проверка отсутствия лишних цифр
        for (int digit : usedNumbers.keySet()) {
            if (!numberCounts.containsKey(digit)) {
                return false;
            }
        }
        return true;
    }


//    public static boolean isValidOperator(String expression, int[] numbers) {
//        for (char c : expression.toCharArray()) {
//            if (!Character.isDigit(c) && "+-*/() ".indexOf(c) == -1) {
//                return false;
//            }
//        }
//        return true;
//    }
public static boolean isValidOperator(String expression, int[] numbers) {
    for (char c : expression.toCharArray()) {
        if (!Character.isDigit(c) && "+-*/() ".indexOf(c) == -1) {
            return false;
        }
    }
    return true;
}

    public static Integer calculateResult(String expression) {
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

    public static int[] getRandomNumbers(Random random) {
        int[] numbers = new int[4];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(10);
        }
        return numbers;
    }

    private static void printNumbers(int[] numbers) {
        System.out.println("Ваши числа: ");
        for (int number : numbers) {
            System.out.println(number + " ");
        }
        System.out.println();
    }

    private static boolean containsNumber(int[] numbers, int number) {
        for (int n : numbers) {
            if (n == number) {
                return true;
            }
        }
        return false;
    }
}
