import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class CourierActions {

    private static final String URL = "jdbc:postgresql://localhost:5432/oliexpress";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private static String loggedInCourierId;

    public static void setLoggedInCourierId(String courierId) {
        CourierActions.loggedInCourierId = courierId;
    }

    public static void viewPackages() {
            JFrame packagesFrame = new JFrame("Courier Packages");
            packagesFrame.setSize(800, 600);

            packagesFrame.getContentPane().setBackground(new Color(142, 174, 111));

            String[] columnNames = {"Tracking ID", "Sender Name", "Recipient Name", "Weight", "Delivery Class", "Status", "Customer ID"};
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

            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    String status = (String) table.getValueAt(row, 5);

                    if ("Delivered".equalsIgnoreCase(status)) {
                        component.setBackground(Color.PINK);
                        component.setForeground(Color.BLACK);
                    } else {
                        component.setBackground(new Color(142, 174, 111));
                        component.setForeground(new Color(68, 88, 50));
                    }
                    return component;
                }
            };

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }

            JButton backButton = new JButton("Back");
            backButton.setBackground(new Color(68, 88, 50));
            backButton.setForeground(new Color(142, 174, 111));
            backButton.setFont(new Font("Arial", Font.BOLD, 16));
            backButton.addActionListener(e -> {
                packagesFrame.dispose();
                CourierMenu.showMenu(loggedInCourierId);
            });

            JPanel panel = new JPanel();
            panel.setBackground(new Color(142, 174, 111));
            panel.add(backButton);
            JScrollPane scrollPane = new JScrollPane(table);
            packagesFrame.add(scrollPane, BorderLayout.CENTER);
            packagesFrame.add(panel, BorderLayout.SOUTH);

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT trackingid, sendername, recipientname, weight, deliveryclass, status, customerid FROM package WHERE courierid = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, loggedInCourierId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String trackingId = resultSet.getString("trackingid");
                    String senderName = resultSet.getString("sendername");
                    String recipientName = resultSet.getString("recipientname");
                    double weight = resultSet.getDouble("weight");
                    String deliveryClass = resultSet.getString("deliveryclass");
                    String status = resultSet.getString("status");
                    String customerId = resultSet.getString("customerid");

                    tableModel.addRow(new Object[]{trackingId, senderName, recipientName, weight, deliveryClass, status, customerId});
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(packagesFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            packagesFrame.setVisible(true);

    }


    public static void updatePackageStatus() {
        JFrame updateStatusFrame = new JFrame("Update Package Status");
        updateStatusFrame.setSize(400, 200);

        updateStatusFrame.getContentPane().setBackground(new Color(142, 174, 111));

        JTextField trackingIdField = new JTextField(15);
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Booked", "In Transit", "Delivered"});
        JButton updateButton = new JButton("Update");

        updateButton.setBackground(new Color(68, 88, 50));
        updateButton.setForeground(new Color(142, 174, 111));
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(68, 88, 50));
        backButton.setForeground(new Color(142, 174, 111));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.addActionListener(e -> {
            updateStatusFrame.dispose();
            CourierMenu.showMenu(loggedInCourierId);
        });

        updateButton.addActionListener(e -> {
            String trackingId = trackingIdField.getText();
            String status = (String) statusCombo.getSelectedItem();

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "UPDATE package SET status = ? WHERE trackingid = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, status);
                preparedStatement.setString(2, trackingId);

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(updateStatusFrame, "Status updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(updateStatusFrame, "Tracking ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(updateStatusFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBackground(new Color(142, 174, 111));
        panel.add(new JLabel("Tracking ID:"));
        panel.add(trackingIdField);
        panel.add(new JLabel("Status:"));
        panel.add(statusCombo);
        panel.add(new JLabel());
        panel.add(updateButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(142, 174, 111));
        buttonPanel.add(backButton);

        updateStatusFrame.add(panel, BorderLayout.CENTER);
        updateStatusFrame.add(buttonPanel, BorderLayout.SOUTH);

        updateStatusFrame.setVisible(true);
    }
}
