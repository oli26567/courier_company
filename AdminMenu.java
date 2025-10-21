import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminMenu {

    public static void showAdminMenu() {
        JFrame adminFrame = new JFrame("Admin Menu");
        adminFrame.setSize(600, 400);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel adminLabel = new JLabel("Admin Menu", SwingConstants.CENTER);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 20));
        adminLabel.setForeground(new Color(68, 88, 50));

        JButton viewCustomersButton = new JButton("View All Customers");
        JButton viewCouriersButton = new JButton("View All Couriers");
        JButton addDeleteCustomersButton = new JButton("Add/Delete Customers");
        JButton addDeleteCouriersButton = new JButton("Add/Delete Couriers");
        JButton backButton = new JButton("Back");

        customizeButton(viewCustomersButton);
        customizeButton(viewCouriersButton);
        customizeButton(addDeleteCustomersButton);
        customizeButton(addDeleteCouriersButton);
        customizeButton(backButton);

        viewCustomersButton.addActionListener(e -> AdminActions.viewAllCustomers(adminFrame));
        viewCouriersButton.addActionListener(e -> AdminActions.viewAllCouriers(adminFrame));

        addDeleteCustomersButton.addActionListener(e -> {
            String action = JOptionPane.showInputDialog(adminFrame, "Enter 'add' to add a customer or 'delete' to delete a customer:");
            if ("add".equalsIgnoreCase(action)) {
                AdminActions.addCustomer(adminFrame);
            } else if ("delete".equalsIgnoreCase(action)) {
                AdminActions.deleteCustomer(adminFrame);
            } else {
                JOptionPane.showMessageDialog(adminFrame, "Invalid action.");
            }
        });

        addDeleteCouriersButton.addActionListener(e -> {
            String action = JOptionPane.showInputDialog(adminFrame, "Enter 'add' to add a courier or 'delete' to delete a courier:");
            if ("add".equalsIgnoreCase(action)) {
                AdminActions.addCourier(adminFrame);
            } else if ("delete".equalsIgnoreCase(action)) {
                AdminActions.deleteCourier(adminFrame);
            } else {
                JOptionPane.showMessageDialog(adminFrame, "Invalid action.");
            }
        });

        backButton.addActionListener(e -> {
            adminFrame.dispose();
            WelcomePage.showWelcomePage();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBackground(new Color(142, 174, 111));
        buttonPanel.add(viewCustomersButton);
        buttonPanel.add(viewCouriersButton);
        buttonPanel.add(addDeleteCustomersButton);
        buttonPanel.add(addDeleteCouriersButton);
        buttonPanel.add(backButton);

        adminFrame.setLayout(new BorderLayout());
        adminFrame.add(adminLabel, BorderLayout.NORTH);
        adminFrame.add(buttonPanel, BorderLayout.CENTER);

        adminFrame.setVisible(true);
    }

    private static void customizeButton(JButton button) {
        button.setBackground(new Color(68, 88, 50));
        button.setForeground(new Color(142, 174, 111));
        button.setFocusPainted(false); // Remove focus border
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }
}
