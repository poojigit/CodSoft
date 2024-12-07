import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class QuizApplication extends JFrame {

    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup optionGroup;
    private JButton nextButton;
    private JLabel timerLabel;
    private int currentQuestionIndex;
    private int score;
    private List<Question> questions;
    private Timer quizTimer;
    private int timeRemaining;

    public QuizApplication() {
        setTitle("Quiz Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", new String[]{"Berlin", "London", "Paris", "Rome"}, "Paris"));
        questions.add(new Question("Which planet is known as the Red Planet?", new String[]{"Venus", "Mars", "Jupiter", "Saturn"}, "Mars"));
        questions.add(new Question("Who painted the Mona Lisa?", new String[]{"Michelangelo", "Leonardo da Vinci", "Raphael", "Donatello"}, "Leonardo da Vinci"));
        questions.add(new Question("What is the largest mammal on Earth?", new String[]{"Elephant", "Giraffe", "Blue Whale", "Hippopotamus"}, "Blue Whale"));
        questions.add(new Question("What is the chemical symbol for gold?", new String[]{"Au", "Ag", "Fe", "Cu"}, "Au"));

        // Initialize UI components
        questionLabel = new JLabel();
        optionGroup = new ButtonGroup();
        options = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            optionGroup.add(options[i]);
        }
        nextButton = new JButton("Next");
        timerLabel = new JLabel("Time Remaining: ");

        // Create a panel for options
        JPanel optionPanel = new JPanel(new GridLayout(4, 1));
        for (JRadioButton option : options) {
            optionPanel.add(option);
        }

        // Create a panel for the timer and next button
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(timerLabel);
        bottomPanel.add(nextButton);

        // Add components to the main frame
        add(questionLabel, BorderLayout.NORTH);
        add(optionPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Event listeners
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                nextQuestion();
            }
        });

        currentQuestionIndex = 0;
        score = 0;
        timeRemaining = 30; // Set initial time in seconds
        startTimer();
        displayQuestion();

        setVisible(true);
    }

    private void startTimer() {
        quizTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Time Remaining: " + timeRemaining + "s");
                if (timeRemaining == 0) {
                    quizTimer.stop();
                    nextQuestion(); // Move to next question even if time runs out
                }
            }
        });
        quizTimer.start();
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion.getQuestion());
            for (int i = 0; i < 4; i++) {
                options[i].setText(currentQuestion.getOptions()[i]);
                options[i].setSelected(false);
            }
            timeRemaining = 30; // Reset timer for each question
            timerLabel.setText("Time Remaining: " + timeRemaining + "s");
            quizTimer.start();
        } else {
            quizTimer.stop();
            JOptionPane.showMessageDialog(this, "Quiz Completed! Your Score: " + score + "/" + questions.size());
            dispose(); // Close the window
        }
    }

    private void checkAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        String selectedOption = "";
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selectedOption = options[i].getText();
                break;
            }
        }
        if (selectedOption.equals(currentQuestion.getCorrectAnswer())) {
            score++;
        }
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        optionGroup.clearSelection();
        displayQuestion();
    }

    public static void main(String[] args) {
        new QuizApplication();
    }
}

class Question {
    private String question;
    private String[] options;
    private String correctAnswer;

    public Question(String question, String[] options, String correctAnswer) {
        this.question = question;
        this.options = options;
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
}