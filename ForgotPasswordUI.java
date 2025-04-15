import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ForgotPasswordUI {
    
    public static void showForgotPasswordPage(JFrame parentFrame) {
        // Store parent frame for returning
        final JFrame previousFrame = parentFrame;
        
        // Create new frame for forgot password
        JFrame frame = new JFrame("Forgot Password");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());
        
        // Main panel with the forgot password content
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 239, 222)); // Same background as login left panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        
        // Icon at the top (key icon represented by a circle)
        JPanel iconPanel = new JPanel();
        iconPanel.setOpaque(false);
        iconPanel.setMaximumSize(new Dimension(70, 70));
        iconPanel.setPreferredSize(new Dimension(70, 70));
        iconPanel.setLayout(new BorderLayout());
        
        JLabel keyIcon = new JLabel("\uD83D\uDD11", SwingConstants.CENTER); // Unicode for key
        keyIcon.setFont(new Font("SansSerif", Font.PLAIN, 24));
        keyIcon.setForeground(new Color(157, 97, 65)); // Brown color from the right panel
        
        // Circle border for the icon - fixed the syntax error here
        iconPanel.setBorder(BorderFactory.createLineBorder(new Color(157, 97, 65), 2));
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconPanel.add(keyIcon);
        
        // Title
        JLabel titleLabel = new JLabel("Forgot Password?");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(157, 97, 65));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
       
        JLabel instructionLabel1 = new JLabel("Enter the email address you used to create the account,");
        instructionLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel instructionLabel2 = new JLabel("and we will email you instructions to reset your password.");
        instructionLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
       
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        LoginUI.RoundedButton sendEmailButton = new LoginUI.RoundedButton("Send Email");
        sendEmailButton.setBackground(new Color(157, 97, 65));
        sendEmailButton.setForeground(Color.WHITE);
        sendEmailButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        JPanel linkPanel = new JPanel();
        linkPanel.setOpaque(false);
        linkPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel rememberLabel = new JLabel("Remember Password? ");
        JLabel loginLink = new JLabel("Login");
        loginLink.setForeground(new Color(157, 97, 65));
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        linkPanel.add(rememberLabel);
        linkPanel.add(loginLink);
        
        
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(iconPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(instructionLabel1);
        mainPanel.add(instructionLabel2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(emailLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(emailField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(sendEmailButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(linkPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        frame.add(mainPanel, BorderLayout.CENTER);
        
       
        sendEmailButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your email address.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!checkEmailExists(email)) {
                JOptionPane.showMessageDialog(frame, "This email address is not registered.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Instead of just showing a message, proceed to verification code screen
                JOptionPane.showMessageDialog(frame, 
                    "Verification code has been sent to your email.", 
                    "Code Sent", JOptionPane.INFORMATION_MESSAGE);
                
                // Navigate to verification code screen
                VerificationCodeUI.showVerificationPage(frame, email);
            }
        });
        
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                previousFrame.setVisible(true);
            }
        });
        
        
        frame.setLocationRelativeTo(null);
        
       
        previousFrame.setVisible(false);
        
        
        frame.setVisible(true);
    }
    
    private static boolean isValidEmail(String email) {
       
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    private static boolean checkEmailExists(String email) {
        
        try {
            File file = new File("userAccounts.txt");
            if (!file.exists()) {
                return false;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = reader.readLine()) != null) {
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

    public static void showResetPasswordPage(JFrame previousFrame, String savedEmail) {
        throw new UnsupportedOperationException("Unimplemented method 'showResetPasswordPage'");
    }
}