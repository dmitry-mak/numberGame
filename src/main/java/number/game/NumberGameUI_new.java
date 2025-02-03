package number.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

public class NumberGameUI_new {

    private static int[] numbers;
    private static JLabel resultLabel;
    private static JLabel timerLabel;
    private static GameTimer timer;

    public static void main(String[] args) {

        // создание окна
        JFrame frame = new JFrame("Number Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        // генерация случайных чисел
        Random random = new Random();
        numbers = NumberGame.getRandomNumbers(random);

        // Панель таймера
        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        timerLabel = new JLabel("Countdown: ");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.RED);
        timerPanel.add(timerLabel);

        timer = new GameTimer(timerLabel, 1, 1500);
        timer.start();

        // панель отображения случайных чисел
        JPanel numbersPanel = new JPanel();
        numbersPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel numbersLabel = new JLabel("Ваши числа: ");
        numbersPanel.add(numbersLabel);

        for (int number : numbers) {
            numbersPanel.add(new JLabel(String.valueOf(number)));
        }

//        long startTime = System.currentTimeMillis();

        // панель для ввода выражения пользователем
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel inputLabel = new JLabel("Введите ваш вариант решения: ");
        JTextField inputField = new JTextField(20);
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);

        // кнопка отправки ответа
        JButton submitButton = new JButton("Отправить");
        inputPanel.add(submitButton);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitButton.doClick();
            }
        });


        // кнопка обновления чисел
        JButton refreshButton = new JButton("Обновить");
        inputPanel.add(refreshButton);

        // панель итогового количества очков в середине страницы
        JPanel finalResultPanel = new JPanel();
        finalResultPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel finalResultLabel = new JLabel("Итоговый результат");
        finalResultPanel.add(finalResultLabel);

        // панель отображения результата внизу страницы
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        resultLabel = new JLabel("Результат");
        resultPanel.add(resultLabel);

//        центральная панель для компоновки всех элементов
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));


        centerPanel.add(timerPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(numbersPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(inputPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(finalResultPanel);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(resultPanel, BorderLayout.SOUTH);

        long startTime = System.currentTimeMillis();

        // обработчик кнопки "Отправить"
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = inputField.getText();
                if (!NumberGame.isValidExpression(expression, numbers)) {
                    JOptionPane.showMessageDialog(frame, "Некорректное выражение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Integer result = NumberGame.calculateResult(expression);
                long endTime = System.currentTimeMillis();
                int timeSpentOnSolution = (int) ((endTime - startTime) / 1000);

                if (result == null) {
                    JOptionPane.showMessageDialog(frame, "Результат не является целым числом", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                timer.stop();
                resultLabel.setText("Результат " + result + ", Время: " + timeSpentOnSolution + " секунд");
                finalResultLabel.setText(String.valueOf(timer.getRemainingTime() * result));
            }
        });

        // обработчик кнопки "Обновить"
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                timer.reset(1500);

                numbers = NumberGame.getRandomNumbers(random);
                numbersPanel.removeAll();
                numbersPanel.add(numbersLabel);
                for (int number : numbers) {
                    numbersPanel.add(new JLabel(String.valueOf(number)));
                }
                numbersPanel.revalidate();
                numbersPanel.repaint();

                resultLabel.setText("Result");
                finalResultLabel.setText("Final Resut");
                inputField.setText("");

                timer.start();
            }
        });

        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exit");
        frame.getRootPane().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);

    }
}
