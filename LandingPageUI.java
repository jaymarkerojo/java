
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LandingPageUI {
    public static void showLandingPage() {
        JFrame landingFrame = new JFrame("SUIREN - Home");
        landingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        landingFrame.setSize(1000, 650);
        landingFrame.setLayout(new BorderLayout());

        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(new Color(216, 183, 151));
        navBar.setPreferredSize(new Dimension(800, 70));

        JLabel logo = new JLabel("SUIREN BY FOWLER");
        logo.setFont(new Font("Serif", Font.BOLD, 15));
        logo.setForeground(Color.DARK_GRAY);
        logo.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JPanel navButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 20));
        navButtons.setOpaque(false);

        JButton shopBtn = new JButton("Shop");
        shopBtn.setContentAreaFilled(false);
        shopBtn.setBorderPainted(false);
        shopBtn.setFocusPainted(false);
        shopBtn.setOpaque(false);
        shopBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        shopBtn.setForeground(Color.DARK_GRAY);
        shopBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton bestSellersBtn = new JButton("Best Sellers");
        bestSellersBtn.setContentAreaFilled(false);
        bestSellersBtn.setBorderPainted(false);
        bestSellersBtn.setFocusPainted(false);
        bestSellersBtn.setOpaque(false);
        bestSellersBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bestSellersBtn.setForeground(Color.DARK_GRAY);
        bestSellersBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton aboutUsBtn = new JButton("About Us");
        aboutUsBtn.setContentAreaFilled(false);
        aboutUsBtn.setBorderPainted(false);
        aboutUsBtn.setFocusPainted(false);
        aboutUsBtn.setOpaque(false);
        aboutUsBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        aboutUsBtn.setForeground(Color.DARK_GRAY);
        aboutUsBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton loginBtn = new JButton("Login");
        loginBtn.setContentAreaFilled(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setOpaque(false);
        loginBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        loginBtn.setForeground(Color.DARK_GRAY);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton getStartedBtn = new RoundedButton("Get Started");
        getStartedBtn.setBackground(new Color(90, 55, 40));
        getStartedBtn.setForeground(Color.WHITE);

        navButtons.add(shopBtn);
        navButtons.add(bestSellersBtn);
        navButtons.add(aboutUsBtn);
        navButtons.add(loginBtn);
        navButtons.add(getStartedBtn);

        navBar.add(logo, BorderLayout.WEST);
        navBar.add(navButtons, BorderLayout.EAST);

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(255, 239, 222));

        JPanel leftImagePanel = new JPanel();
        leftImagePanel.setBackground(new Color(255, 239, 222));

        ImageIcon modelIcon = new ImageIcon("C:\\Users\\Pc4\\Downloads\\1.jpg");
        Image img = modelIcon.getImage().getScaledInstance(350, 450, Image.SCALE_SMOOTH);
        JLabel modelLabel = new JLabel(new ImageIcon(img));
        leftImagePanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 10));
        leftImagePanel.add(modelLabel);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(255, 239, 222));
        textPanel.setBorder(BorderFactory.createEmptyBorder(100, 30, 100, 80));

        JLabel headline = new JLabel("<html><div style='width:300px;'>Simple Steps. Radiant Skin.<br>Real Confidence</div></html>");
        headline.setFont(new Font("Serif", Font.BOLD, 26));
        headline.setForeground(new Color(50, 30, 20));

        JLabel subtext = new JLabel("START NOW AND MAKE YOUR NATURAL BEAUTY SHINE.");
        subtext.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtext.setForeground(new Color(70, 50, 40));

        JButton startNowBtn = new RoundedButton("Start Now");
        startNowBtn.setBackground(new Color(130, 80, 50));
        startNowBtn.setForeground(Color.WHITE);

        addHoverEffect(startNowBtn, new Color(130, 80, 50), new Color(110, 60, 40));

        textPanel.add(headline);
        textPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        textPanel.add(subtext);
        textPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        textPanel.add(startNowBtn);

        mainContent.add(leftImagePanel, BorderLayout.WEST);
        mainContent.add(textPanel, BorderLayout.CENTER);

        landingFrame.add(navBar, BorderLayout.NORTH);
        landingFrame.add(mainContent, BorderLayout.CENTER);
        landingFrame.setLocationRelativeTo(null);
        landingFrame.setVisible(true);
    }

    private static void addHoverEffect(JButton button, Color originalColor, Color hoverColor) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
    }

    static class RoundedButton extends JButton {
        private int radius = 30;

        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(new Font("SansSerif", Font.PLAIN, 14));
            setMargin(new Insets(10, 20, 10, 20));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isPressed()) {
                g2.setColor(getBackground().darker());
            } else {
                g2.setColor(getBackground());
            }
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public void paintBorder(Graphics g) {
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(140, 40);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> showLandingPage());
    }
}

