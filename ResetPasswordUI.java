import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ResetPasswordUI {
    
    public static void showResetPasswordPage(JFrame parentFrame, String email) {
        // Store parent frame for returning
        final JFrame previousFrame = parentFrame;
        
        // Create new frame for reset password
        JFrame frame = new JFrame("Reset Password");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());
        
        // Main panel with the reset password content
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 239, 222)); // Same background color
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        
        // Lock icon at the top
        JPanel iconPanel = new JPanel();
        iconPanel.setOpaque(false);
        iconPanel.setMaximumSize(new Dimension(70, 70));
        iconPanel.setPreferredSize(new Dimension(70, 70));
        iconPanel.setLayout(new BorderLayout());
        
        JLabel lockIcon = new JLabel("🔒", SwingConstants.CENTER); // Unicode for lock
        lockIcon.setFont(new Font("SansSerif", Font.PLAIN, 24));
        lockIcon.setForeground(new Color(157, 97, 65)); // Brown color
        
        // Circle border for the icon
        iconPanel.setBorder(BorderFactory.createLineBorder(new Color(157, 97, 65), 2));
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconPanel.add(lockIcon);
        
        // Title
        JLabel titleLabel = new JLabel("Reset Password");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(157, 97, 65));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Instruction text
        JLabel instructionLabel = new JLabel("Please enter your new password");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // New password field
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        newPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        newPasswordField.setBorder(BorderFactory.createTitledBorder("New Password"));
        
        // Confirm password field
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        confirmPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPasswordField.setBorder(BorderFactory.createTitledBorder("Confirm Password"));
        
        // Reset button
        LoginUI.RoundedButton resetButton = new LoginUI.RoundedButton("Reset Password");
        resetButton.setBackground(new Color(157, 97, 65)); // Brown color
        resetButton.setForeground(Color.WHITE);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Back to login link
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
        mainPanel.add(instructionLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(newPasswordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(confirmPasswordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(resetButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(backPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        frame.add(mainPanel, BorderLayout.CENTER);
        
        // Event listeners
        resetButton.addActionListener(e -> {
            String newPassword = new String(newPasswordField.getPassword()).trim();
            String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
            
            // Validate the passwords
            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both passwords.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (newPassword.length() < 6) {
                JOptionPane.showMessageDialog(frame, "Password should be at least 6 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Update the password in the file
                if (updatePassword(email, newPassword)) {
                    JOptionPane.showMessageDialog(frame, "Password has been reset successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Go back to login
                    frame.dispose();
                    // Go back to original login screen
                    LoginUI.main(new String[0]);
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to update password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        backPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                // Go back to original login screen
                LoginUI.main(new String[0]);
            }
        });
        
        // Center frame on screen
        frame.setLocationRelativeTo(null);
        
        // Hide the parent frame
        previousFrame.setVisible(false);
        
        // Show the reset password frame
        frame.setVisible(true);
    }
    
    private static boolean updatePassword(String email, String newPassword) {
        try {
            File inputFile = new File("userAccounts.txt");
            File tempFile = new File("tempAccounts.txt");
            
            if (!inputFile.exists()) {
                return false;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            
            String line;
            boolean foundUser = false;
            boolean updatedPassword = false;
            String username = null;
            
            // First, find the username associated with the email
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Email:") && line.contains(email)) {
                    foundUser = true;
                } else if (foundUser && line.startsWith("Username:")) {
                    username = line.split(":")[1].trim();
                    break;
                }
            }
            
            reader.close();
            
            // If username found, update the password
            if (username != null) {
                reader = new BufferedReader(new FileReader(inputFile));
                
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Username:") && line.contains(username) && !updatedPassword) {
                        // Write username line as is
                        writer.write(line + System.lineSeparator());
                        
                        // Read next line which should be the password
                        line = reader.readLine();
                        if (line != null && line.startsWith("Password:")) {
                            // Write updated password
                            writer.write("Password: " + newPassword + System.lineSeparator());
                            updatedPassword = true;
                        }
                    } else {
                        // Write all other lines as is
                        writer.write(line + System.lineSeparator());
                    }
                }
                
                writer.close();
                reader.close();
                
                // Replace the original file with the temp file
                if (!inputFile.delete()) {
                    return false;
                }
                return tempFile.renameTo(inputFile);
            }
            
            writer.close();
            return false;
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}