package com.india.love;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.List;

public final class ILoveIndiaApp {
    private static final Color SAFFRON = new Color(255, 153, 51);
    private static final Color INDIA_GREEN = new Color(19, 136, 8);
    private static final Color NAVY = new Color(0, 0, 128);
    private static final List<String> QUOTES = Arrays.asList(
            "Unity in diversity is India's greatest strength.",
            "Let every heart beat with pride for our nation.",
            "Together, we make India brighter every day."
    );

    private ILoveIndiaApp() {
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            printHeadlessOutput();
            return;
        }
        SwingUtilities.invokeLater(ILoveIndiaApp::showWindow);
    }

    private static void printHeadlessOutput() {
        System.out.println("I LOVE INDIA");
        System.out.println("A celebration of our beautiful nation");
        System.out.println(QUOTES.get(0));
        System.out.println("Proud to be Indian");
    }

    private static void showWindow() {
        JFrame frame = new JFrame("I Love India");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(createContent());
        frame.setMinimumSize(new Dimension(700, 620));
        frame.setSize(820, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);

        FlagPanel flag = new FlagPanel();
        content.add(flag, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.WHITE);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(8, 30, 8, 30);

        JLabel title = new JLabel("I LOVE INDIA", SwingConstants.CENTER);
        title.setForeground(NAVY);
        title.setFont(new Font("Serif", Font.BOLD, 48));
        constraints.gridy = 0;
        center.add(title, constraints);

        JLabel subtitle = new JLabel("A celebration of our beautiful nation", SwingConstants.CENTER);
        subtitle.setForeground(new Color(85, 85, 85));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 19));
        constraints.gridy = 1;
        center.add(subtitle, constraints);

        JLabel quote = new JLabel(QUOTES.get(0), SwingConstants.CENTER);
        quote.setForeground(new Color(55, 55, 55));
        quote.setFont(new Font("Serif", Font.ITALIC, 20));
        quote.setBorder(BorderFactory.createEmptyBorder(22, 0, 10, 0));
        constraints.gridy = 2;
        center.add(quote, constraints);

        JButton button = new JButton("Celebrate India");
        button.setBackground(SAFFRON);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(13, 28, 13, 28));
        final int[] quoteIndex = {0};
        button.addActionListener(event -> {
            quoteIndex[0] = (quoteIndex[0] + 1) % QUOTES.size();
            quote.setText(QUOTES.get(quoteIndex[0]));
        });
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.NONE;
        center.add(button, constraints);

        content.add(center, BorderLayout.CENTER);

        JLabel footer = new JLabel("Proud to be Indian", SwingConstants.CENTER);
        footer.setOpaque(true);
        footer.setBackground(INDIA_GREEN);
        footer.setForeground(Color.WHITE);
        footer.setFont(new Font("SansSerif", Font.BOLD, 17));
        footer.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 0));
        content.add(footer, BorderLayout.SOUTH);
        return content;
    }

    private static final class FlagPanel extends JPanel {
        private FlagPanel() {
            setPreferredSize(new Dimension(700, 230));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int flagWidth = Math.min(getWidth() - 100, 620);
            int flagHeight = flagWidth * 2 / 3;
            int x = (getWidth() - flagWidth) / 2;
            int y = (getHeight() - flagHeight) / 2;
            int bandHeight = flagHeight / 3;

            g.setColor(SAFFRON);
            g.fillRect(x, y, flagWidth, bandHeight);
            g.setColor(Color.WHITE);
            g.fillRect(x, y + bandHeight, flagWidth, bandHeight);
            g.setColor(INDIA_GREEN);
            g.fillRect(x, y + bandHeight * 2, flagWidth, flagHeight - bandHeight * 2);

            int centerX = x + flagWidth / 2;
            int centerY = y + flagHeight / 2;
            int radius = Math.max(22, flagHeight / 5);
            g.setColor(NAVY);
            g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
            for (int spoke = 0; spoke < 24; spoke++) {
                double angle = spoke * Math.PI / 12;
                int endX = centerX + (int) (radius * Math.cos(angle));
                int endY = centerY + (int) (radius * Math.sin(angle));
                g.drawLine(centerX, centerY, endX, endY);
            }
            g.dispose();
        }
    }
}