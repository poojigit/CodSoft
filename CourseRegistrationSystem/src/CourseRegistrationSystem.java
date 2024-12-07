import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationSystem extends JFrame {

    private JComboBox<String> courseComboBox;
    private JButton registerButton;
    private JButton dropButton;
    private JTextArea displayArea;
    private Connection connection;

    public CourseRegistrationSystem() {
        setTitle("Course Registration System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            String url = "jdbc:mysql://"; // Replace with your database URL
            String user = "root"; // Replace with your database username
            String password = "root"; // Replace with your database password
            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Initialize UI components
        courseComboBox = new JComboBox<>();
        registerButton = new JButton("Register");
        dropButton = new JButton("Drop");
        displayArea = new JTextArea();
        displayArea.setEditable(false);

        // Event listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerForCourse();
            }
        });

        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropCourse();
            }
        });

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(dropButton);

        // Add components to the main frame
        add(new JLabel("Select Course:"), BorderLayout.NORTH);
        add(courseComboBox, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(displayArea), BorderLayout.EAST);

        // Load available courses
        loadAvailableCourses();

        setVisible(true);
    }

    private void loadAvailableCourses() {
        try {
            String query = "SELECT course_code, title, capacity FROM courses WHERE capacity > 0";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            courseComboBox.removeAllItems();
            while (resultSet.next()) {
                String courseCode = resultSet.getString("course_code");
                String title = resultSet.getString("title");
                int capacity = resultSet.getInt("capacity");
                courseComboBox.addItem(courseCode + " - " + title + " (Available Slots: " + capacity + ")");
            }

        } catch (SQLException e) {
            displayError("Error loading courses: " + e.getMessage());
        }
    }

    private void registerForCourse() {
        try {
            String selectedCourse = (String) courseComboBox.getSelectedItem();
            String courseCode = selectedCourse.split(" - ")[0];

            // Get student ID (you can implement a login or user selection here)
            String studentID = "12345"; // Replace with actual student ID logic

            // Check if student is already registered
            String checkQuery = "SELECT * FROM student_courses WHERE student_id = ? AND course_code = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, studentID);
            checkStatement.setString(2, courseCode);
            ResultSet checkResult = checkStatement.executeQuery();

            if (checkResult.next()) {
                displayError("You are already registered for this course.");
            } else {
                // Register student for the course
                String registerQuery = "INSERT INTO student_courses (student_id, course_code) VALUES (?, ?)";
                PreparedStatement registerStatement = connection.prepareStatement(registerQuery);
                registerStatement.setString(1, studentID);
                registerStatement.setString(2, courseCode);
                registerStatement.executeUpdate();

                // Update course capacity
                String updateQuery = "UPDATE courses SET capacity = capacity - 1 WHERE course_code = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, courseCode);
                updateStatement.executeUpdate();

                displayMessage("Registration successful!");
                loadAvailableCourses();
            }

        } catch (SQLException e) {
            displayError("Error registering for course: " + e.getMessage());
        }
    }

    private void dropCourse() {
        try {
            String selectedCourse = (String) courseComboBox.getSelectedItem();
            String courseCode = selectedCourse.split(" - ")[0];

            // Get student ID (you can implement a login or user selection here)
            String studentID = "12345"; // Replace with actual student ID logic

            // Check if student is registered for the course
            String checkQuery = "SELECT * FROM student_courses WHERE student_id = ? AND course_code = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, studentID);
            checkStatement.setString(2, courseCode);
            ResultSet checkResult = checkStatement.executeQuery();

            if (checkResult.next()) {
                // Drop the course
                String dropQuery = "DELETE FROM student_courses WHERE student_id = ? AND course_code = ?";
                PreparedStatement dropStatement = connection.prepareStatement(dropQuery);
                dropStatement.setString(1, studentID);
                dropStatement.setString(2, courseCode);
                dropStatement.executeUpdate();

                // Update course capacity
                String updateQuery = "UPDATE courses SET capacity = capacity + 1 WHERE course_code = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, courseCode);
                updateStatement.executeUpdate();

                displayMessage("Course dropped successfully!");
                loadAvailableCourses();
            } else {
                displayError("You are not registered for this course.");
            }

        } catch (SQLException e) {
            displayError("Error dropping course: " + e.getMessage());
        }
    }

    private void displayMessage(String message) {
        displayArea.setText(message);
    }

    private void displayError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        new CourseRegistrationSystem();
    }
}