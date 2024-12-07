package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATM extends JFrame {

    private JTextField amountField;
    private JLabel balanceLabel;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton checkBalanceButton;
    private BankAccount account;

    public ATM() {
        setTitle("ATM Machine");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        amountField = new JTextField(10);
        balanceLabel = new JLabel("Balance: ");
        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        checkBalanceButton = new JButton("Check Balance");

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount > 0 && amount <= account.getBalance()) {
                        account.withdraw(amount);
                        balanceLabel.setText("Balance: $" + account.getBalance());
                        JOptionPane.showMessageDialog(null, "Withdrawal successful!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient balance or invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount > 0) {
                        account.deposit(amount);
                        balanceLabel.setText("Balance: $" + account.getBalance());
                        JOptionPane.showMessageDialog(null, "Deposit successful!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a positive amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                balanceLabel.setText("Balance: $" + account.getBalance());
            }
        });

        add(amountField);
        add(withdrawButton);
        add(depositButton);
        add(checkBalanceButton);
        add(balanceLabel);

        account = new BankAccount(1000.00);
        balanceLabel.setText("Balance: $" + account.getBalance());

        setVisible(true);
    }

    public static void main(String[] args) {
        new ATM();
    }
}

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}