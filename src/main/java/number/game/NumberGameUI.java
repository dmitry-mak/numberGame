package number.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

public class NumberGameUI {

    private static int[] numbers;
    private static JLabel resultLabel;
    private static JLabel timerLabel;
    private static GameTimer timer;

    public static void main(String[] args) {

//        создание окна
        JFrame frame = new JFrame("Number Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLayout(new BorderLayout());

//        генерация случайных чисел
        Random random = new Random();
        numbers = NumberGame.getRandomNumbers(random);

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

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            submitButton.doClick();
            }
        });

//        кнопка обновления цифр
        JButton refreshButton = new JButton("Обновить");
        inputPanel.add(refreshButton);

//        панель отображения таймера
        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout());
        timerLabel = new JLabel("Countdown: ");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.RED);
        timerPanel.add(timerLabel);

//        инициализация и запуск таймера
        timer = new GameTimer(timerLabel, 1, 1500);
        timer.start();

//        панель отображения результата
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout());
//        JLabel resultLabel = new JLabel("Результат: ");
        resultLabel = new JLabel("Результат: ");
        resultPanel.add(resultLabel);

        long startTime = System.currentTimeMillis();

//        обработчик нажатия кнопки
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = inputField.getText();
                if (!NumberGame.isValidExpression(expression, numbers)) {
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
                timer.stop();
                resultLabel.setText("Результат " + result + ", Время: " + timeSpentOnSolution + "секунд");
            }
        });

//        обработчик нажатия кнопки "Обновить"
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                timer.reset(1500);
                numbers = NumberGame.getRandomNumbers(random);
                numbersPanel.removeAll();
                numbersPanel.add(numbersLabel);
                displayNumbers(numbersPanel);
                numbersPanel.revalidate();
                numbersPanel.repaint();

                resultLabel.setText("Результат: ");
                inputField.setText("");

                timer.start();
            }
        });

//        обработчик нажатия клавиши esc для выхода из программы
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exit");
        frame.getRootPane().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
//        центральная панель для компоновки inputPanel и timerPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(inputPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(timerPanel);


        frame.add(numbersPanel, BorderLayout.NORTH);
        //   frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(resultPanel, BorderLayout.SOUTH);
        //  frame.add(timerPanel, BorderLayout.PAGE_END);

        frame.setVisible(true);
    }

    private static void displayNumbers(JPanel panel) {
        for (int number : numbers) {
            panel.add(new JLabel(String.valueOf(number)));
        }
    }
}
