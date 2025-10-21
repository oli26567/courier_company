import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class AdminActions {

    private static final String URL = "jdbc:postgresql://localhost:5432/oliexpress";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Courier> couriers = new ArrayList<>();

    public static void viewAllCustomers(JFrame adminFrame) {
        JFrame customerFrame = new JFrame("Customer List");
        customerFrame.setSize(800, 600);
        customerFrame.getContentPane().setBackground(new Color(142, 174, 111));

        String[] columnNames = {"Customer ID", "Name", "Email", "Phone", "Address"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        table.setBackground(new Color(142, 174, 111));
        table.setForeground(new Color(68, 88, 50));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(68, 88, 50));
        tableHeader.setForeground(new Color(142, 174, 111));
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(68, 88, 50));
        backButton.setForeground(new Color(142, 174, 111));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            customerFrame.dispose();
            AdminMenu.showAdminMenu();
        });

        JPanel panel = new JPanel();
        panel.setBackground(new Color(142, 174, 111));
        panel.add(backButton);
        JScrollPane scrollPane = new JScrollPane(table);
        customerFrame.add(scrollPane, BorderLayout.CENTER);
        customerFrame.add(panel, BorderLayout.SOUTH);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT customerid, name, email, phone, address FROM customer";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String customerId = resultSet.getString("customerid");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                tableModel.addRow(new Object[]{customerId, name, email, phone, address});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(customerFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        customerFrame.setVisible(true);
    }

    public static void viewAllCouriers(JFrame adminFrame) {
        JFrame courierFrame = new JFrame("Courier List");
        courierFrame.setSize(800, 600);
        courierFrame.getContentPane().setBackground(new Color(142, 174, 111));

        String[] columnNames = {"Courier ID", "Name", "Email", "Phone", "Address"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        table.setBackground(new Color(142, 174, 111));
        table.setForeground(new Color(68, 88, 50));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(68, 88, 50));
        tableHeader.setForeground(new Color(142, 174, 111));
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(68, 88, 50));
        backButton.setForeground(new Color(142, 174, 111));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            courierFrame.dispose();
            AdminMenu.showAdminMenu();
        });

        JPanel panel = new JPanel();
        panel.setBackground(new Color(142, 174, 111));
        panel.add(backButton);
        JScrollPane scrollPane = new JScrollPane(table);
        courierFrame.add(scrollPane, BorderLayout.CENTER);
        courierFrame.add(panel, BorderLayout.SOUTH);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT courierid, name, email, phone, address FROM courier";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String courierId = resultSet.getString("courierid");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                tableModel.addRow(new Object[]{courierId, name, email, phone, address});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(courierFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        courierFrame.setVisible(true);
    }

    public static void addCustomer(JFrame adminFrame) {
        JFrame addCustomerFrame = new JFrame("Add Customer");
        addCustomerFrame.setSize(400, 350);

        addCustomerFrame.getContentPane().setBackground(new Color(142, 174, 111));

        JTextField customerIdField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField phoneField = new JTextField(10);
        JTextField addressField = new JTextField(15);

        JButton submitButton = new JButton("Submit");

        submitButton.setBackground(new Color(68, 88, 50));
        submitButton.setForeground(new Color(142, 174, 111));
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(68, 88, 50));
        backButton.setForeground(new Color(142, 174, 111));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            addCustomerFrame.dispose();
            AdminMenu.showAdminMenu();
        });

        submitButton.addActionListener(e -> {
            String customerId = customerIdField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "INSERT INTO customer (customerid, name, email, phone, address) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, customerId);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, address);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(addCustomerFrame, "Customer added successfully!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(addCustomerFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.setBackground(new Color(142, 174, 111));
        panel.add(new JLabel("Customer ID:"));
        panel.add(customerIdField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel());
        panel.add(submitButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(142, 174, 111));
        buttonPanel.add(backButton);

        addCustomerFrame.add(panel, BorderLayout.CENTER);
        addCustomerFrame.add(buttonPanel, BorderLayout.SOUTH);

        addCustomerFrame.setVisible(true);
    }

    public static void deleteCustomer(JFrame adminFrame) {
        String customerId = JOptionPane.showInputDialog(adminFrame, "Enter Customer ID to delete:");

        if (customerId == null || customerId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(adminFrame, "Customer ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String checkSql = "SELECT customerid FROM customer WHERE customerid = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, customerId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(adminFrame, "Customer with ID " + customerId + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String deleteSql = "DELETE FROM customer WHERE customerid = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setString(1, customerId);

            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(adminFrame, "Customer with ID " + customerId + " deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(adminFrame, "Failed to delete customer. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(adminFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void addCourier(JFrame adminFrame) {
        JFrame addCourierFrame = new JFrame("Add Courier");
        addCourierFrame.setSize(400, 350);

        addCourierFrame.getContentPane().setBackground(new Color(142, 174, 111));

        JTextField courierIdField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField phoneField = new JTextField(10);
        JTextField addressField = new JTextField(15);

        JButton submitButton = new JButton("Submit");

        submitButton.setBackground(new Color(68, 88, 50));
        submitButton.setForeground(new Color(142, 174, 111));
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(68, 88, 50));
        backButton.setForeground(new Color(142, 174, 111));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            addCourierFrame.dispose();
            AdminMenu.showAdminMenu();
        });

        submitButton.addActionListener(e -> {
            String courierId = courierIdField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "INSERT INTO courier (courierid, name, email, phone, address) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, courierId);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, address);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(addCourierFrame, "Courier added successfully!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(addCourierFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.setBackground(new Color(142, 174, 111));
        panel.add(new JLabel("Courier ID:"));
        panel.add(courierIdField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel());
        panel.add(submitButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(142, 174, 111));
        buttonPanel.add(backButton);

        addCourierFrame.add(panel, BorderLayout.CENTER);
        addCourierFrame.add(buttonPanel, BorderLayout.SOUTH);

        addCourierFrame.setVisible(true);
    }

    public static void deleteCourier(JFrame adminFrame) {
        String courierId = JOptionPane.showInputDialog(adminFrame, "Enter Courier ID to delete:");

        if (courierId == null || courierId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(adminFrame, "Courier ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String checkSql = "SELECT courierid FROM courier WHERE courierid = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, courierId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) {
                JOptionPane.showMessageDialog(adminFrame, "Courier with ID " + courierId + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String deleteSql = "DELETE FROM courier WHERE courierid = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setString(1, courierId);

            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(adminFrame, "Courier with ID " + courierId + " deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(adminFrame, "Failed to delete courier. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(adminFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
