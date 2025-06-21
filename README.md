Pomodoro Timer

A simple and elegant Pomodoro Timer desktop application built with Java Swing.
This timer helps you boost productivity by alternating between focused work sessions and breaks using the Pomodoro Technique.

Features:
  1.Work sessions: 25 minutes of focused work.
  2.Short breaks: 5 minutes break after each work session.
  3.Long breaks: 15 minutes break after every 4th work session.
  4.Visual timer display with large countdown.
  5.Progress bar showing completion percentage.
  6.Start, Pause/Resume, Reset, and Skip controls.
  7.Session state indicated with color-coded labels.
  8.Popup notifications after each session completion.
  9.Sound notification using system beep.

![Screenshot From 2025-06-22 01-39-10](https://github.com/user-attachments/assets/aabb696e-d459-4897-87cc-b3d92580cc38)
![Screenshot From 2025-06-22 01-40-04](https://github.com/user-attachments/assets/09f7c857-debc-4a49-bc67-065f856054e1)
![Screenshot From 2025-06-22 01-39-56](https://github.com/user-attachments/assets/fd6b9f23-2d01-4ec5-a4d5-6bd00c5b15d9)

How to Run:

Prerequisites:
  1.Java JDK 8 or higher installed on your system.
  2.Compatible OS (Windows, Mac, Linux).

Compile and Run:
javac -d out src/org/example/PomodoroTimer.java
java -cp out org.example.PomodoroTimer
Alternatively, import the project into your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse) and run the main method of PomodoroTimer class.

How It Works:
The timer starts in Work state by default with a 25-minute countdown.

When the timer finishes:
  If the current session was Work, it increments the Pomodoro count.
  
  After every 4 Pomodoros, a Long Break (15 mins) starts.
  
  Otherwise, a Short Break (5 mins) starts.
  
  If the session was a break, the timer switches back to Work.
  
  You can pause, reset, or skip the current session anytime.
  
  UI updates dynamically with session progress and state changes.

Code Structure:
  PomodoroTimer.java — main JFrame window and logic.
  Uses Swing components: JLabel, JButton, JProgressBar, JOptionPane.
  Timer logic implemented using javax.swing.Timer.
  Enum TimerState defines session types with labels, durations, and colors.

Customization:

You can customize the timer durations by modifying constants in the source code:

  private static final int WORK_TIME = 25 * 60; // seconds
  private static final int SHORT_BREAK = 5 * 60;
  private static final int LONG_BREAK = 15 * 60;
  Colors and UI elements can also be tweaked in the TimerState enum and UI setup methods.

License:
This project is open source and available under the MIT License.
Feel free to use and modify it for your own needs!

Contact:
Created by IslamNizami
Feel free to open issues or submit pull requests!
