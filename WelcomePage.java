import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WelcomePage {

    public static void showWelcomePage() {
        JFrame welcomeFrame = new JFrame("Welcome to OliExpress");
        welcomeFrame.setSize(600, 500);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.getContentPane().setBackground(new Color(142, 174, 111));

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\OOP-lab\\PROIECT\\poza2.jpg"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
        }
        Image scaledImage = image != null ? image.getScaledInstance(400, -1, Image.SCALE_SMOOTH) : null;
        ImageIcon welcomeImage = scaledImage != null ? new ImageIcon(scaledImage) : null;
        JLabel imageLabel = new JLabel(welcomeImage);

        JLabel welcomeLabel = new JLabel("Welcome to OliExpress", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(68, 88, 50));

        JButton customerButton = new JButton("Customer Menu");
        JButton courierButton = new JButton("Courier Menu");
        JButton adminButton = new JButton("Admin Menu");

        customizeButton(customerButton);
        customizeButton(courierButton);
        customizeButton(adminButton);

        customerButton.addActionListener(e -> handleCustomerLogin(welcomeFrame));
        courierButton.addActionListener(e -> handleCourierLogin(welcomeFrame));
        adminButton.addActionListener(e -> handleAdminLogin(welcomeFrame));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(new Color(142, 174, 111));
        buttonPanel.add(customerButton);
        buttonPanel.add(courierButton);
        buttonPanel.add(adminButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(142, 174, 111));
        centerPanel.add(Box.createVerticalStrut(15)); // Spacer
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(buttonPanel);

        welcomeFrame.setLayout(new BorderLayout());
        welcomeFrame.add(imageLabel, BorderLayout.NORTH);
        welcomeFrame.add(centerPanel, BorderLayout.CENTER);

        welcomeFrame.setVisible(true);
    }

    private static void customizeButton(JButton button) {
        button.setBackground(new Color(68, 88, 50));
        button.setForeground(new Color(142, 174, 111));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private static void handleCustomerLogin(JFrame frame) {
        String customerId = JOptionPane.showInputDialog(frame, "Enter your Customer ID:");
        if (validateCustomerId(customerId)) {
            frame.dispose();
            CustomerMenu.showMenu(customerId);
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Customer ID. Access denied.");
        }
    }

    private static void handleCourierLogin(JFrame frame) {
        String courierId = JOptionPane.showInputDialog(frame, "Enter your Courier ID:");
        if (validateCourierId(courierId)) {
            frame.dispose();
            CourierMenu.showMenu(courierId);
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Courier ID. Access denied.");
        }
    }

    private static void handleAdminLogin(JFrame frame) {
        String adminCode = JOptionPane.showInputDialog(frame, "Enter admin code:");
        if ("admin123".equals(adminCode)) {
            frame.dispose();
            AdminMenu.showAdminMenu();
        } else {
            JOptionPane.showMessageDialog(frame, "Incorrect code. Access denied.");
        }
    }

    private static boolean validateCustomerId(String customerId) {
        String query = "SELECT COUNT(*) FROM customer WHERE customerid = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private static boolean validateCourierId(String courierId) {
        String query = "SELECT COUNT(*) FROM courier WHERE courierid = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, courierId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void main(String[] args) {
        showWelcomePage();
    }
}
