import javax.swing.*;
import java.awt.*;

/**
 * This class draws a logo with metal texture by applying GradientPaint.
 * @author Sheng Liang
 * */
public class LogoPanel extends JPanel {

    private static Color logoColor1 = new Color(242, 255, 255,255);
    private static Color logoColor2 = new Color(91, 96, 98,255);

    /**
     * This method applies Graphics2d to draw the line graph.
     * */
    @Override
    protected void paintComponent(Graphics g) {

        //These would be draw.
        String logo = "MarketGUI";
        String me = "github.com/liangsheng02";

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //set font and location of the text, and then draw it!
        Font font = StaticMethods.getFont("Arial", Font.BOLD, 48, g2.getFont());
        if (font != null) {
            g2.setFont(font);
        }
        FontMetrics metrics = g2.getFontMetrics();
        int logoX = getWidth()/2 - metrics.stringWidth(logo)/2;
        int logoY = getHeight() - 50;
        GradientPaint paint = new GradientPaint(20, 20, logoColor1, 40,40, logoColor2, true);
        g2.setPaint(paint);
        g2.drawString(logo, logoX, logoY);

        //set font and location of "me", and then draw it!
        Font font2 = StaticMethods.getFont("Arial", Font.BOLD, 16, g.getFont());
        if (font2 != null) {
            g2.setFont(font2);
        }
        FontMetrics metrics2 = g2.getFontMetrics();
        int meX = getWidth()/2 - metrics2.stringWidth(me)/2;
        int meY = logoY + 30;
        GradientPaint paint2 = new GradientPaint(40, 40, logoColor1, 20,20, logoColor2, true);
        g2.setPaint(paint2);
        g2.drawString(me, meX, meY);

    }

    /**
     * Constructor only set a background color.
     * */
    public LogoPanel() {
        this.setBackground(StaticMethods.backgroundColor);
    }

    /**
     * Main method for testing
     * */
    public static void main(String[] args) {
        LogoPanel logoPanel = new LogoPanel();
        logoPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(logoPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}