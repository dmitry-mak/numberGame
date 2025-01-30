package number.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGameUI {

    public static void main(String[] args) {

//        создание окна
        JFrame frame = new JFrame("Number Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

//        генерация случайных чисел
        Random random = new Random();
        int[] numbers = NumberGame.getRandomNumbers(random);

//        панель отображения случайных чисел
        JPanel numbersPanel = new JPanel();
        numbersPanel.setLayout(new FlowLayout());
        JLabel numbersLabel = new JLabel("Ваши числа: ");
        numbersPanel.add(numbersLabel);
        for (int number : numbers) {
            numbersPanel.add(new JLabel(String.valueOf(number)));
        }

//        панель для ввода выражения пользователем
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JLabel inputLabel = new JLabel("Введите ваш вариант решения:");
        JTextField inputField = new JTextField(20);
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);

//        кнопка отправки ответа
        JButton submitButton = new JButton("Отправить");
        inputPanel.add(submitButton);

//        панель отображения результата
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout());
        JLabel resultLabel = new JLabel("Результат: ");
        resultPanel.add(resultLabel);

        long startTime = System.currentTimeMillis();
//        обработчик нажатия кнопки
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = inputField.getText();
                if (NumberGame.isValidExpression(expression, numbers)) {
                    JOptionPane.showMessageDialog(frame, "Некорректное выражение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
//                long startTime = System.currentTimeMillis();
                Integer result = NumberGame.calculateResult(expression);
                long endTime = System.currentTimeMillis();
                int timeSpentOnSolution = (int) ((endTime - startTime) / 1000);

                if (result == null) {
                    JOptionPane.showMessageDialog(frame, "Результат не является целым числом", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                resultLabel.setText("Результат " + result + ", Время: " + timeSpentOnSolution + "секунд");
            }
        });

        frame.add(numbersPanel, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(resultPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
