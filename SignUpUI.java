import javax.swing.*;
import java.awt.*;
import java.io.*;

public class SignUpUI {
    public static void showSignUpPage(JFrame frame) {
        // JFrame frame = new JFrame("Sign Up");
        // frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // frame.setSize(800, 500);
        // frame.setLayout(new GridLayout(1, 2));
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(1, 2));

        // Left panel (reuse styling)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(255, 239, 222));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(80, 40, 80, 40));

        JLabel welcomeLabel = new JLabel("WELCOME TO");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel brandLabel = new JLabel("SUIREN");
        brandLabel.setFont(new Font("Serif", Font.BOLD, 40));
        brandLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel byFowler = new JLabel("BY FOWLER");
        byFowler.setFont(new Font("SansSerif", Font.PLAIN, 12));
        byFowler.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel loginPrompt = new JLabel("Already have an account?");
        loginPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginButton = new JButton("Log In");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(140, 80, 50));
        loginButton.setForeground(Color.WHITE);

        leftPanel.add(welcomeLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(brandLabel);
        leftPanel.add(byFowler);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(loginPrompt);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(loginButton);

        // Right panel (sign-up form)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(157, 97, 65));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        JLabel signUpLabel = new JLabel("Create an Account");
        signUpLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        signUpLabel.setForeground(Color.WHITE);
        signUpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));

        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        confirmPasswordField.setBorder(BorderFactory.createTitledBorder("Confirm Password"));

        JButton registerBtn = new RoundedButton("Register");
        registerBtn.setBackground(new Color(240, 200, 180));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(signUpLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(usernameField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(emailField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(passwordField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(confirmPasswordField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(registerBtn);

        frame.add(leftPanel);
        frame.add(rightPanel);
        frame.setVisible(true);

        // Register Action
        registerBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(frame, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (isAccountExist(username, email)) {
                JOptionPane.showMessageDialog(frame, "Account with this username/email already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            saveAccountToFile(username, email, password);
            JOptionPane.showMessageDialog(frame, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            LandingPageUI.showLandingPage();
        });

        // Optional: Login button could return to login page or close the sign-up frame
        loginButton.addActionListener(e -> {
            frame.dispose(); // You can also call LoginUI.main(null); if needed
        });
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private static boolean isAccountExist(String username, String email) {
        try {
            File file = new File("userAccounts.txt");
            if (!file.exists()) return false;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username:") && line.contains(username)) {
                    reader.close();
                    return true;
                }
                if (line.startsWith("Email:") && line.contains(email)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void saveAccountToFile(String username, String email, String password) {
        try {
            FileWriter writer = new FileWriter("userAccounts.txt", true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("Username: " + username + "\n");
            bw.write("Email: " + email + "\n");
            bw.write("Password: " + password + "\n");
            bw.write("------------\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class RoundedButton extends JButton {
        private int radius = 30;

        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor(Color.DARK_GRAY);
            } else {
                g.setColor(getBackground());
            }

            g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(150, 50);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sign Up");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);
            frame.setLayout(new GridLayout(1, 2));
            showSignUpPage(frame);
            frame.setVisible(true);
        });
    }
    
    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(SignUpUI::showSignUpPage);
    // }
}
