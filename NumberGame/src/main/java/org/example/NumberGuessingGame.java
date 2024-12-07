package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGame extends JFrame {

    private JTextField guessField;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JButton guessButton;
    private JButton newGameButton;
    private int secretNumber;
    private int maxAttempts;
    private int attempts;
    private int score;
    private int roundsPlayed;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        guessField = new JTextField(10);
        feedbackLabel = new JLabel("Enter your guess:");
        attemptsLabel = new JLabel("Attempts: 0");
        scoreLabel = new JLabel("Score: 0");
        guessButton = new JButton("Guess");
        newGameButton = new JButton("New Game");

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int userGuess = Integer.parseInt(guessField.getText());
                    attempts++;

                    if (userGuess == secretNumber) {
                        feedbackLabel.setText("Congratulations! You guessed it!");
                        score += maxAttempts - attempts + 1; // Award bonus for fewer attempts
                        attemptsLabel.setText("Attempts: " + attempts);
                        scoreLabel.setText("Score: " + score);
                        guessButton.setEnabled(false);
                        roundsPlayed++;
                    } else if (userGuess < secretNumber) {
                        feedbackLabel.setText("Too low!");
                    } else {
                        feedbackLabel.setText("Too high!");
                    }

                    if (attempts >= maxAttempts) {
                        feedbackLabel.setText("You ran out of attempts. The number was: " + secretNumber);
                        guessButton.setEnabled(false);
                        roundsPlayed++;
                    }

                    attemptsLabel.setText("Attempts: " + attempts);
                } catch (NumberFormatException ex) {
                    feedbackLabel.setText("Invalid input. Please enter a number.");
                }
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });

        add(feedbackLabel);
        add(guessField);
        add(guessButton);
        add(attemptsLabel);
        add(scoreLabel);
        add(newGameButton);

        newGame();
        setVisible(true);
    }

    private void newGame() {
        secretNumber = new Random().nextInt(100) + 1;
        maxAttempts = 5;
        attempts = 0;
        guessField.setText("");
        feedbackLabel.setText("Enter your guess:");
        attemptsLabel.setText("Max.Attempts: 5");
        guessButton.setEnabled(true);
    }

    public static void main(String[] args) {
        new NumberGuessingGame();
    }
}