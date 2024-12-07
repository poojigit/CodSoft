/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class QuizApplication extends JFrame {

    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup group;
    private JButton nextButton;
    private JLabel timerLabel;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int totalQuestions = 10; // Adjust as needed
    private Question[] questions;
    private Timer timer;

    public QuizApplication() {
        setTitle("Quiz Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sample Quiz Questions (replace with your own)
        questions = new Question[totalQuestions];
        questions[0] = new Question("What is the capital of France?", "Paris", "Berlin", "Rome", "Madrid", "Paris");
        questions[1] = new Question("Who painted the Mona Lisa?", "Michelangelo", "Leonardo da Vinci", "Raphael", "Donatello", "Leonardo da Vinci");
        // Add more questions here...

        questionLabel = new JLabel("Question " + (currentQuestionIndex + 1) + ": " + questions[currentQuestionIndex].getQuestion());
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionPanel = new JPanel(new GridLayout(4, 1));
        options = new JRadioButton[4];
        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton(questions[currentQuestionIndex].getOptions()[i]);
            group.add(options[i]);
            optionPanel.add(options[i]);
        }
        add(optionPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                nextQuestion();
            }
        });
        buttonPanel.add(nextButton);
        timerLabel = new JLabel("Time Left: 30s");
        buttonPanel.add(timerLabel);
        add(buttonPanel, BorderLayout.SOUTH);

        // Start the timer
        startTimer();

        setVisible(true);
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int timeLeft = Integer.parseInt(timerLabel.getText().substring(10));
                if (timeLeft > 0) {
                    timerLabel.setText("Time Left: " + --timeLeft + "s");
                } else {
                    timer.stop();
                    timerLabel.setText("Time's Up!");
                    nextButton.setEnabled(false);
                    checkAnswer(); // Check answer even if time's up
                    nextQuestion();
                }
            }
        });
        timer.start();
    }

    private void checkAnswer() {
        String selectedOption = "";
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selectedOption = options[i].getText();
                break;
            }
        }

        if (selectedOption.equals(questions[currentQuestionIndex].getCorrectAnswer())) {
            score++;
        }
    }

    private void nextQuestion() {
        currentQuestionIndex++;

        if (currentQuestionIndex < totalQuestions) {
            questionLabel.setText("Question " + (currentQuestionIndex + 1) + ": " + questions[currentQuestionIndex].getQuestion());
            for (int i = 0; i < 4; i++) {
                options[i].setText(questions[currentQuestionIndex].getOptions()[i]);
                options[i].setSelected(false);
            }
            startTimer();
        } else {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Quiz Completed!\nYour Score: " + score + "/" + totalQuestions);
            dispose(); // Close the window
        }
    }

    public static void main(String[] args) {
        new QuizApplication();
    }
}

class Question {
    private String question;
    private String[] options;
    private String correctAnswer;

    public Question(String question, String option1, String option2, String option3, String option4, String correctAnswer) {
        this.question = question;
        this.options = new String[]{option1, option2, option3, option4};
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}*/





