import javax.swing.*;
import java.awt.*;

public class CustomerMenu {

    public static void showMenu(String customerId) {

        CustomerActions.setLoggedInCustomerId(customerId);

        JFrame menuFrame = new JFrame("Customer Menu");
        menuFrame.setSize(500, 400);
        menuFrame.getContentPane().setBackground(new Color(142, 174, 111));

        JButton sendPackageButton = new JButton("Send a Package");
        JButton trackPackageButton = new JButton("Track My Packages");
        JButton editProfileButton = new JButton("Edit My Profile");
        JButton backButton = new JButton("Back");

        sendPackageButton.setBackground(new Color(68, 88, 50));
        sendPackageButton.setForeground(new Color(142, 174, 111));
        trackPackageButton.setBackground(new Color(68, 88, 50));
        trackPackageButton.setForeground(new Color(142, 174, 111));
        editProfileButton.setBackground(new Color(68, 88, 50));
        editProfileButton.setForeground(new Color(142, 174, 111));
        backButton.setBackground(new Color(142, 174, 111));
        backButton.setForeground(new Color(68, 88, 50));

        sendPackageButton.addActionListener(e -> CustomerActions.sendPackage(customerId));

        trackPackageButton.addActionListener(e -> CustomerActions.trackPackages());

        editProfileButton.addActionListener(e -> CustomerActions.editProfile());

        backButton.addActionListener(e -> {
            menuFrame.dispose();
            WelcomePage.showWelcomePage();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.setBackground(new Color(142, 174, 111));
        panel.add(sendPackageButton);
        panel.add(trackPackageButton);
        panel.add(editProfileButton);
        panel.add(backButton);

        menuFrame.add(panel);
        menuFrame.setVisible(true);
    }
}