import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class CustomerActions {

    private static final String URL = "jdbc:postgresql://localhost:5432/oliexpress";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private static String loggedInCustomerId;

    public static void setLoggedInCustomerId(String customerId) {
        loggedInCustomerId = customerId;
    }

    public static void sendPackage() {
        JFrame sendPackageFrame = new JFrame("Send a Package");
        sendPackageFrame.setSize(400, 300);

        sendPackageFrame.getContentPane().setBackground(new Color(142, 174, 111));

        JTextField senderField = new JTextField(15);
        JTextField recipientField = new JTextField(15);
        JTextField weightField = new JTextField(10);
        JComboBox<String> deliveryClassCombo = new JComboBox<>(new String[]{"Standard", "Express", "Same-Day"});

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(68, 88, 50));
        submitButton.setForeground(new Color(142, 174, 111));
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(68, 88, 50));
        backButton.setForeground(new Color(142, 174, 111));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            sendPackageFrame.dispose();
            CustomerMenu.showMenu(loggedInCustomerId);
        });

        submitButton.addActionListener(e -> {
            String senderName = senderField.getText();
            String recipientName = recipientField.getText();
            double weight = Double.parseDouble(weightField.getText());
            String deliveryClass = (String) deliveryClassCombo.getSelectedItem();

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "INSERT INTO package (customerid, sendername, recipientname, weight, deliveryclass, status) VALUES (?, ?, ?, ?, ?, 'Booked')";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, loggedInCustomerId);
                preparedStatement.setString(2, senderName);
                preparedStatement.setString(3, recipientName);
                preparedStatement.setDouble(4, weight);
                preparedStatement.setString(5, deliveryClass);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(sendPackageFrame, "Package sent successfully!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(sendPackageFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.setBackground(new Color(142, 174, 111));
        panel.add(new JLabel("Sender Name:"));
        panel.add(senderField);
        panel.add(new JLabel("Recipient Name:"));
        panel.add(recipientField);
        panel.add(new JLabel("Weight (kg):"));
        panel.add(weightField);
        panel.add(new JLabel("Delivery Class:"));
        panel.add(deliveryClassCombo);
        panel.add(new JLabel());
        panel.add(submitButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(142, 174, 111));
        buttonPanel.add(backButton);

        sendPackageFrame.add(panel, BorderLayout.CENTER);
        sendPackageFrame.add(buttonPanel, BorderLayout.SOUTH);

        sendPackageFrame.setVisible(true);
    }

    public static void trackPackages() {
        JFrame packagesFrame = new JFrame("Customer Packages");
        packagesFrame.setSize(800, 600);

        packagesFrame.getContentPane().setBackground(new Color(142, 174, 111));

        String[] columnNames = {"Tracking ID", "Sender Name", "Recipient Name", "Weight", "Delivery Class", "Status"};
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

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String status = table.getValueAt(row, 5).toString();
                if ("Delivered".equalsIgnoreCase(status)) {
                    c.setBackground(Color.PINK);
                } else {
                    c.setBackground(new Color(142, 174, 111));
                }
                setForeground(new Color(68, 88, 50));
                return c;
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(68, 88, 50));
        backButton.setForeground(new Color(142, 174, 111));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            packagesFrame.dispose();
            CustomerMenu.showMenu(loggedInCustomerId);
        });

        JPanel panel = new JPanel();
        panel.setBackground(new Color(142, 174, 111));
        panel.add(backButton);
        JScrollPane scrollPane = new JScrollPane(table);
        packagesFrame.add(scrollPane, BorderLayout.CENTER);
        packagesFrame.add(panel, BorderLayout.SOUTH);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT trackingid, sendername, recipientname, weight, deliveryclass, status FROM package WHERE customerid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, loggedInCustomerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String trackingId = resultSet.getString("trackingid");
                String senderName = resultSet.getString("sendername");
                String recipientName = resultSet.getString("recipientname");
                double weight = resultSet.getDouble("weight");
                String deliveryClass = resultSet.getString("deliveryclass");
                String status = resultSet.getString("status");

                tableModel.addRow(new Object[]{trackingId, senderName, recipientName, weight, deliveryClass, status});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(packagesFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        packagesFrame.setVisible(true);
    }
    public static void editProfile() {
        JFrame editProfileFrame = new JFrame("Edit Profile");
        editProfileFrame.setSize(400, 300);

        editProfileFrame.getContentPane().setBackground(new Color(142, 174, 111));

        String[] fields = {"Name", "Email", "Phone Number"};
        JComboBox<String> fieldComboBox = new JComboBox<>(fields);

        JTextField newValueField = new JTextField(15);

        JButton updateButton = new JButton("Update");
        updateButton.setBackground(new Color(68, 88, 50));
        updateButton.setForeground(new Color(142, 174, 111));
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(68, 88, 50));
        backButton.setForeground(new Color(142, 174, 111));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            editProfileFrame.dispose();
            CustomerMenu.showMenu(loggedInCustomerId);
        });

        updateButton.addActionListener(e -> {
            String selectedField = (String) fieldComboBox.getSelectedItem();
            String newValue = newValueField.getText();

            if (newValue.isEmpty()) {
                JOptionPane.showMessageDialog(editProfileFrame, "Please enter a new value.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "";

                switch (selectedField) {
                    case "Name":
                        sql = "UPDATE customer SET name = ? WHERE customerid = ?";
                        break;
                    case "Email":
                        sql = "UPDATE customer SET email = ? WHERE customerid = ?";
                        break;
                    case "Phone Number":
                        sql = "UPDATE customer SET phone = ? WHERE customerid = ?";
                        break;
                }

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, newValue);
                preparedStatement.setString(2, loggedInCustomerId);

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(editProfileFrame, selectedField + " updated successfully!");
                    editProfileFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(editProfileFrame, "Failed to update " + selectedField + ". Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(editProfileFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBackground(new Color(142, 174, 111));
        panel.add(new JLabel("Select Field to Edit:"));
        panel.add(fieldComboBox);
        panel.add(new JLabel("Enter New Value:"));
        panel.add(newValueField);
        panel.add(updateButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(142, 174, 111));
        buttonPanel.add(backButton);

        editProfileFrame.add(panel, BorderLayout.CENTER);
        editProfileFrame.add(buttonPanel, BorderLayout.SOUTH);

        editProfileFrame.setVisible(true);
    }

    public static void sendPackage(String customerId) {
        JFrame sendPackageFrame = new JFrame("Send a Package");
        sendPackageFrame.setSize(400, 400);

        sendPackageFrame.getContentPane().setBackground(new Color(142, 174, 111));

        JTextField trackingIdField = new JTextField(15);

        JTextField senderField = new JTextField(15);
        senderField.setText(getSenderNameByCustomerId(customerId));
        senderField.setEditable(false);

        JTextField recipientField = new JTextField(15);
        JTextField weightField = new JTextField(10);
        JComboBox<String> deliveryClassCombo = new JComboBox<>(new String[]{"Standard", "Express", "Same-Day"});

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(68, 88, 50));
        submitButton.setForeground(new Color(142, 174, 111));
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(68, 88, 50));
        backButton.setForeground(new Color(142, 174, 111));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            sendPackageFrame.dispose();
            CustomerMenu.showMenu(loggedInCustomerId);
        });

        submitButton.addActionListener(e -> {
            String trackingId = trackingIdField.getText();
            String senderName = senderField.getText();
            String recipientName = recipientField.getText();
            double weight = Double.parseDouble(weightField.getText());
            String deliveryClass = (String) deliveryClassCombo.getSelectedItem();

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "INSERT INTO package (trackingid, customerid, sendername, recipientname, weight, deliveryclass, status) VALUES (?, ?, ?, ?, ?, ?, 'Booked')";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, trackingId);
                preparedStatement.setString(2, customerId);
                preparedStatement.setString(3, senderName);
                preparedStatement.setString(4, recipientName);
                preparedStatement.setDouble(5, weight);
                preparedStatement.setString(6, deliveryClass);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(sendPackageFrame, "Package sent successfully!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(sendPackageFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.setBackground(new Color(142, 174, 111));
        panel.add(new JLabel("Tracking ID:"));
        panel.add(trackingIdField);
        panel.add(new JLabel("Sender Name:"));
        panel.add(senderField);
        panel.add(new JLabel("Recipient Name:"));
        panel.add(recipientField);
        panel.add(new JLabel("Weight (kg):"));
        panel.add(weightField);
        panel.add(new JLabel("Delivery Class:"));
        panel.add(deliveryClassCombo);
        panel.add(new JLabel());
        panel.add(submitButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(142, 174, 111));
        buttonPanel.add(backButton);

        sendPackageFrame.add(panel, BorderLayout.CENTER);
        sendPackageFrame.add(buttonPanel, BorderLayout.SOUTH);

        sendPackageFrame.setVisible(true);
    }

    private static String getSenderNameByCustomerId(String customerId) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT name FROM customer WHERE customerid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return "Unknown";
    }
}
