import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerificationCodeUI {

    private static String savedEmail; // To store the email from previous screen
    private static String generatedCode; // To store the verification code
    
    public static void showVerificationPage(JFrame parentFrame, String email) {
        // Save the email for later use
        savedEmail = email;
        
        // Generate a random 4-digit code
        generatedCode = generateRandomCode();
        System.out.println("Generated code: " + generatedCode); // For testing purposes
        
        // Store parent frame for returning
        final JFrame previousFrame = parentFrame;
        
        // Create new frame for verification
        JFrame frame = new JFrame("Password Recovery");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());
        
        // Main panel with the verification content
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 239, 222)); // Same background color
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        
        // Email icon at the top
        JPanel iconPanel = new JPanel();
        iconPanel.setOpaque(false);
        iconPanel.setMaximumSize(new Dimension(70, 70));
        iconPanel.setPreferredSize(new Dimension(70, 70));
        iconPanel.setLayout(new BorderLayout());
        
        JLabel emailIcon = new JLabel("✉", SwingConstants.CENTER); // Unicode for email
        emailIcon.setFont(new Font("SansSerif", Font.PLAIN, 24));
        emailIcon.setForeground(new Color(157, 97, 65)); // Brown color
        
        // Circle border for the icon
        iconPanel.setBorder(BorderFactory.createLineBorder(new Color(157, 97, 65), 2));
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconPanel.add(emailIcon);
        
        // Title
        JLabel titleLabel = new JLabel("Password Recovery");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(157, 97, 65));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Instruction text
        JLabel instructionLabel1 = new JLabel("We've sent a verification code to your Gmail.");
        instructionLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel instructionLabel2 = new JLabel("Please enter the code below to reset your password");
        instructionLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Code input fields panel
        JPanel codeFieldsPanel = new JPanel();
        codeFieldsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        codeFieldsPanel.setOpaque(false);
        codeFieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create 4 code input fields
        List<JTextField> codeFields = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            JTextField digitField = new JTextField(1);
            digitField.setFont(new Font("SansSerif", Font.BOLD, 24));
            digitField.setHorizontalAlignment(JTextField.CENTER);
            digitField.setPreferredSize(new Dimension(60, 60));
            
            // Circle border for the digit field
            digitField.setBorder(BorderFactory.createLineBorder(new Color(157, 97, 65), 2));
            
            // Add focus listener to move to next field when digit is entered
            final int fieldIndex = i;
            digitField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!Character.isDigit(c)) {
                        e.consume(); // Only allow digits
                        return;
                    }
                    
                    // Auto-move to next field after typing a digit
                    SwingUtilities.invokeLater(() -> {
                        if (fieldIndex < codeFields.size() - 1) {
                            codeFields.get(fieldIndex + 1).requestFocus();
                        }
                    });
                }
            });
            
            codeFields.add(digitField);
            codeFieldsPanel.add(digitField);
        }
        
        // Continue button
        LoginUI.RoundedButton continueButton = new LoginUI.RoundedButton("Continue");
        continueButton.setBackground(new Color(157, 97, 65)); // Brown color
        continueButton.setForeground(Color.WHITE);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // "Send Code Again" link
        JLabel resendCodeLink = new JLabel("Send Code Again");
        resendCodeLink.setForeground(new Color(157, 97, 65));
        resendCodeLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resendCodeLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // "Back to log in" link
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backPanel.setOpaque(false);
        backPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel backArrow = new JLabel("←");
        JLabel backLabel = new JLabel(" Back to log in");
        backArrow.setForeground(new Color(157, 97, 65));
        backLabel.setForeground(new Color(157, 97, 65));
        
        backPanel.add(backArrow);
        backPanel.add(backLabel);
        backPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add components to main panel with spacing
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(iconPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(instructionLabel1);
        mainPanel.add(instructionLabel2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(codeFieldsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(continueButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(resendCodeLink);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(backPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        frame.add(mainPanel, BorderLayout.CENTER);
        
        // Event listeners
        continueButton.addActionListener(e -> {
            // Collect the entered code
            StringBuilder enteredCode = new StringBuilder();
            for (JTextField field : codeFields) {
                enteredCode.append(field.getText());
            }
            
            // Validate the code
            if (enteredCode.length() < 4) {
                JOptionPane.showMessageDialog(frame, "Please enter all 4 digits of the code.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (enteredCode.toString().equals(generatedCode)) {
                // Code is correct, proceed to reset password
                frame.dispose();
                ResetPasswordUI.showResetPasswordPage(previousFrame, savedEmail);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid verification code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        resendCodeLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Generate a new code
                generatedCode = generateRandomCode();
                System.out.println("New code generated: " + generatedCode);
                JOptionPane.showMessageDialog(frame, "A new verification code has been sent to your email.", "Code Sent", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear all fields
                for (JTextField field : codeFields) {
                    field.setText("");
                }
                codeFields.get(0).requestFocus();
            }
        });
        
        backPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                previousFrame.setVisible(true);
            }
        });
        
        // Center frame on screen
        frame.setLocationRelativeTo(null);
        
        // Hide the parent frame
        previousFrame.setVisible(false);
        
        // Show the verification frame
        frame.setVisible(true);
        
        // Focus on first field
        codeFields.get(0).requestFocus();
    }
    
    private static String generateRandomCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // Generates a number between 1000 and 9999
        return String.valueOf(code);
    }
}
