package number.game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameTimer {

    private Timer timer;
    private int remainingTime;
    private JLabel timerLabel;
    private final int timeDecrement;
    private int bonusPoints;
    private boolean isRunning = false;

    public GameTimer(JLabel timerLabel, int timeDecrement, int initialTime) {
        this.timerLabel = timerLabel;
        this.timeDecrement = timeDecrement;
        this.remainingTime = initialTime;

        timer = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingTime > 0) {
                    remainingTime -= timeDecrement;  // уменьшение оставшегося времени
                    updateTimerLabel(); // обновление метки времени
                } else {
                    stop();
                }
            }
        });
    }

    private void updateTimerLabel() {
        timerLabel.setText(String.valueOf(remainingTime));
    }

    public void start() {
        if (!isRunning) {
            timer.start();
            isRunning = true;
        }
    }

    public void stop() {
        if (isRunning) {
            timer.stop();
            isRunning = false;
        }
    }

    public void reset(int initialTime) {
        this.remainingTime = initialTime;
        this.bonusPoints = 0;
        updateTimerLabel();
//        stop();
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
