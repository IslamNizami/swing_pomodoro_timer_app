package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PomodoroTimer extends JFrame {
    private static final int WORK_TIME = 25 * 60; // 25 minutes in seconds
    private static final int SHORT_BREAK = 5 * 60; // 5 minutes in seconds
    private static final int LONG_BREAK = 15 * 60; // 15 minutes in seconds

    private Timer timer;
    private int timeRemaining;
    private int pomodoroCount = 0;
    private boolean isRunning = false;
    private TimerState currentState = TimerState.WORK;

    // UI Components
    private JLabel timeLabel;
    private JLabel stateLabel;
    private JLabel pomodoroCountLabel;
    private JButton startPauseButton;
    private JButton resetButton;
    private JButton skipButton;
    private JProgressBar progressBar;

    enum TimerState {
        WORK("Work Time", WORK_TIME, new Color(220, 53, 69)),
        SHORT_BREAK("Short Break", PomodoroTimer.SHORT_BREAK, new Color(40, 167, 69)),
        LONG_BREAK("Long Break", PomodoroTimer.LONG_BREAK, new Color(23, 162, 184));

        final String label;
        final int duration;
        final Color color;

        TimerState(String label, int duration, Color color) {
            this.label = label;
            this.duration = duration;
            this.color = color;
        }
    }

    public PomodoroTimer() {
        initializeTimer();
        setupUI();
        resetTimer();
    }

    private void initializeTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                updateUI();

                if (timeRemaining <= 0) {
                    timerFinished();
                }
            }
        });
    }

    private void setupUI() {
        setTitle("Pomodoro Timer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Top panel - State and count
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.setBackground(Color.WHITE);

        stateLabel = new JLabel("Work Time", SwingConstants.CENTER);
        stateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        stateLabel.setOpaque(true);
        stateLabel.setBackground(currentState.color);
        stateLabel.setForeground(Color.WHITE);
        stateLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pomodoroCountLabel = new JLabel("Pomodoros: 0", SwingConstants.CENTER);
        pomodoroCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        topPanel.add(stateLabel);
        topPanel.add(pomodoroCountLabel);

        // Center panel - Timer display
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);

        timeLabel = new JLabel("25:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 48));
        timeLabel.setForeground(new Color(52, 58, 64));

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(100);
        progressBar.setStringPainted(true);
        progressBar.setString("Ready to start");
        progressBar.setPreferredSize(new Dimension(300, 25));
        progressBar.setBackground(Color.LIGHT_GRAY);
        progressBar.setForeground(currentState.color);

        centerPanel.add(timeLabel, BorderLayout.CENTER);
        centerPanel.add(progressBar, BorderLayout.SOUTH);

        // Bottom panel - Controls
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        startPauseButton = new JButton("Start");
        startPauseButton.setPreferredSize(new Dimension(100, 35));
        startPauseButton.setFont(new Font("Arial", Font.BOLD, 14));
        startPauseButton.setBackground(new Color(40, 167, 69));
        startPauseButton.setForeground(Color.WHITE);
        startPauseButton.setFocusPainted(false);
        startPauseButton.addActionListener(e -> toggleTimer());

        resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(100, 35));
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setBackground(new Color(108, 117, 125));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(e -> resetTimer());

        skipButton = new JButton("Skip");
        skipButton.setPreferredSize(new Dimension(100, 35));
        skipButton.setFont(new Font("Arial", Font.BOLD, 14));
        skipButton.setBackground(new Color(255, 193, 7));
        skipButton.setForeground(Color.BLACK);
        skipButton.setFocusPainted(false);
        skipButton.addActionListener(e -> skipTimer());

        buttonPanel.add(startPauseButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(skipButton);

        // Add panels to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void toggleTimer() {
        if (isRunning) {
            pauseTimer();
        } else {
            startTimer();
        }
    }

    private void startTimer() {
        isRunning = true;
        timer.start();
        startPauseButton.setText("Pause");
        startPauseButton.setBackground(new Color(220, 53, 69));
        progressBar.setString("Running...");
    }

    private void pauseTimer() {
        isRunning = false;
        timer.stop();
        startPauseButton.setText("Resume");
        startPauseButton.setBackground(new Color(40, 167, 69));
        progressBar.setString("Paused");
    }

    private void resetTimer() {
        timer.stop();
        isRunning = false;
        timeRemaining = currentState.duration;
        startPauseButton.setText("Start");
        startPauseButton.setBackground(new Color(40, 167, 69));
        updateUI();
        progressBar.setString("Ready to start");
    }

    private void skipTimer() {
        timer.stop();
        isRunning = false;
        timerFinished();
    }

    private void timerFinished() {
        timer.stop();
        isRunning = false;

        // Play notification sound (if available)
        Toolkit.getDefaultToolkit().beep();

        // Show notification
        String message = "";
        if (currentState == TimerState.WORK) {
            pomodoroCount++;
            if (pomodoroCount % 4 == 0) {
                currentState = TimerState.LONG_BREAK;
                message = "Great job! Time for a long break.";
            } else {
                currentState = TimerState.SHORT_BREAK;
                message = "Pomodoro completed! Time for a short break.";
            }
        } else {
            currentState = TimerState.WORK;
            message = "Break's over! Ready for another pomodoro?";
        }

        JOptionPane.showMessageDialog(this, message, "Timer Finished", JOptionPane.INFORMATION_MESSAGE);

        // Reset for next session
        timeRemaining = currentState.duration;
        startPauseButton.setText("Start");
        startPauseButton.setBackground(new Color(40, 167, 69));
        updateUI();
        progressBar.setString("Ready to start");
    }

    private void updateUI() {
        // Update time display
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timeLabel.setText(String.format("%02d:%02d", minutes, seconds));

        // Update state label
        stateLabel.setText(currentState.label);
        stateLabel.setBackground(currentState.color);

        // Update pomodoro count
        pomodoroCountLabel.setText("Pomodoros: " + pomodoroCount);

        // Update progress bar
        int totalTime = currentState.duration;
        int progress = (int) (((double) (totalTime - timeRemaining) / totalTime) * 100);
        progressBar.setValue(progress);
        progressBar.setForeground(currentState.color);

        if (isRunning) {
            progressBar.setString(String.format("%.1f%% complete", (double) progress));
        }

        // Update window title
        setTitle(String.format("Pomodoro Timer - %02d:%02d", timeRemaining / 60, timeRemaining % 60));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Use default look and feel
            }

            new PomodoroTimer().setVisible(true);
        });
    }
}