import javax.swing.*;
import java.awt.*;

public class CourierMenu {

    public static void showMenu(String courierId) {

        CourierActions.setLoggedInCourierId(courierId);

        JFrame menuFrame = new JFrame("Courier Menu");
        menuFrame.setSize(500, 400);
        menuFrame.getContentPane().setBackground(new Color(142, 174, 111));


        JButton viewPackagesButton = new JButton("View My Packages");
        JButton updateStatusButton = new JButton("Update Package Status");
        JButton backButton = new JButton("Back");

        viewPackagesButton.setBackground(new Color(68, 88, 50));
        viewPackagesButton.setForeground(new Color(142, 174, 111));
        updateStatusButton.setBackground(new Color(68, 88, 50));
        updateStatusButton.setForeground(new Color(142, 174, 111));
        backButton.setBackground(new Color(142, 174, 111));
        backButton.setForeground(new Color(68, 88, 50));

        viewPackagesButton.addActionListener(e -> CourierActions.viewPackages());

        updateStatusButton.addActionListener(e -> CourierActions.updatePackageStatus());

        backButton.addActionListener(e -> {
            menuFrame.dispose();
            WelcomePage.showWelcomePage();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.setBackground(new Color(142, 174, 111));
        panel.add(viewPackagesButton);
        panel.add(updateStatusButton);
        panel.add(backButton);

        menuFrame.add(panel);
        menuFrame.setVisible(true);
    }
}
