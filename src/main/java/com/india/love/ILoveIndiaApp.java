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
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
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
        if (args.length > 0 && "--web".equals(args[0])) {
            startWebServer();
            return;
        }
        if (GraphicsEnvironment.isHeadless()) {
            printHeadlessOutput();
            return;
        }
        SwingUtilities.invokeLater(ILoveIndiaApp::showWindow);
    }

    private static void startWebServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/", ILoveIndiaApp::handleWebRequest);
            server.setExecutor(null);
            server.start();
            System.out.println("I Love India is running at http://localhost:8080");
            System.out.println("Press Ctrl+C to stop the server.");
        } catch (IOException exception) {
            System.err.println("Could not start the web server: " + exception.getMessage());
        }
    }

    private static void handleWebRequest(HttpExchange exchange) throws IOException {
        String response = "<!doctype html><html><head><meta charset=\"UTF-8\"><title>I Love India</title>"
                + "<style>body{margin:0;background:#f8f7f2;color:#000080;font-family:Georgia,serif;text-align:center}"
                + ".flag{width:90%;max-width:720px;height:360px;margin:48px auto 34px;box-shadow:0 12px 30px #999}"
                + ".saffron,.white,.green{height:33.33%}.saffron{background:#ff9933}.white{background:#fff;display:flex;align-items:center;justify-content:center}"
                + ".green{background:#138808}.chakra{width:115px;height:115px;border:5px solid #000080;border-radius:50%}"
                + ".chakra:after{content:'✦';font-size:72px;line-height:105px}.tag{color:#555;font:20px Arial,sans-serif}"
                + "button{margin:24px;padding:14px 25px;background:#ff9933;border:0;color:white;font-weight:bold;font-size:16px;border-radius:4px}</style>"
                + "</head><body><div class=\"flag\"><div class=\"saffron\"></div><div class=\"white\"><div class=\"chakra\"></div></div><div class=\"green\"></div></div>"
                + "<h1>I LOVE INDIA</h1><p class=\"tag\">A celebration of our beautiful nation</p>"
                + "<p>Unity in diversity is India's greatest strength.</p><button onclick=\"alert('Proud to be Indian!')\">Celebrate India</button>"
                + "<h3 style=\"background:#138808;color:white;padding:16px\">Proud to be Indian</h3></body></html>";
        byte[] bytes = response.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
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