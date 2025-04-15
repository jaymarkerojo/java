import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class LoginUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new GridLayout(1, 2));
        frame.setVisible(true);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(255, 239, 222));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(80, 40, 80, 40));

        JLabel welcomeLabel = new JLabel("WELCOME BACK TO");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel brandLabel = new JLabel("SUIREN");
        brandLabel.setFont(new Font("Serif", Font.BOLD, 40));
        brandLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel byFowler = new JLabel("BY FOWLER");
        byFowler.setFont(new Font("SansSerif", Font.PLAIN, 12));
        byFowler.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel signupPrompt = new JLabel("Don't have an account?");
        signupPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setBackground(new Color(140, 80, 50));
        signupButton.setForeground(Color.WHITE);

        leftPanel.add(welcomeLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(brandLabel);
        leftPanel.add(byFowler);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(signupPrompt);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(signupButton);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(157, 97, 65));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        JButton loginButton = new RoundedButton("Sign In");
        loginButton.setBackground(new Color(240, 200, 180));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel forgotPassword = new JLabel("Forgot your password?");
        forgotPassword.setForeground(Color.WHITE);
        forgotPassword.setFont(new Font("SansSerif", Font.ITALIC, 12));
        forgotPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ForgotPasswordUI.showForgotPasswordPage(frame);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                forgotPassword.setForeground(new Color(240, 200, 180));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                forgotPassword.setForeground(Color.WHITE);
            }
        });

        rightPanel.add(loginLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(5, 20)));
        rightPanel.add(usernameField);
        rightPanel.add(Box.createRigidArea(new Dimension(5, 10)));
        rightPanel.add(passwordField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(loginButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(forgotPassword);

        frame.add(leftPanel);
        frame.add(rightPanel);
        frame.setVisible(true);

        signupButton.addActionListener(e -> {
            SignUpUI.showSignUpPage(frame);
        });

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (validateLogin(username, password)) {
                frame.dispose();
                LandingPageUI.showLandingPage();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static boolean validateLogin(String username, String password) {
        try {
            File file = new File("userAccounts.txt");

            if (!file.exists()) {
                return false;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            String storedUsername = null;
            String storedPassword = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Username:")) {
                    storedUsername = line.split(":")[1].trim();
                    if (storedUsername.equals(username)) {
                        // Continue checking for password
                    } else {
                        storedUsername = null; // Reset if username doesn't match
                    }
                }
                if (line.startsWith("Password:") && storedUsername != null) {
                    storedPassword = line.split(":")[1].trim();
                    break;
                }
            }

            reader.close();

            System.out.println("Stored Username: " + storedUsername);
            System.out.println("Stored Password: " + storedPassword);
            System.out.println("Input Username: " + username);
            System.out.println("Input Password: " + password);

            return storedUsername != null && storedPassword != null && storedPassword.equals(password);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    static class RoundedButton extends JButton {
        private int radius = 30;

        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(150, 50);
        }
    }
}
