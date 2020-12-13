import javax.swing.*;
import java.awt.*;

/**
 * This Class shows messages after click the button on centerPanel in MarketGUI.
 * It's background uses gradient color, to show an effect that opposite from the centerPanel.
 * @author Sheng Liang
 */
public class MessagePanel extends JPanel {

    private String displayString = "";

    /**
     * This method draws the message and a gradient background.
     * */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw gradient background
        GradientPaint gradientPaint =new GradientPaint(0, 0,
                StaticMethods.gradientColor, 0, getHeight(), StaticMethods.backgroundColor,false);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(gradientPaint);
        g2.fillRect(0, 0, getWidth(), getHeight());
        //draw massage
        Font displayStringFont = StaticMethods.getFont("Arial Black", -1, 14, g2.getFont());
        if (displayStringFont != null) {
            g2.setFont(displayStringFont);
        }
        FontMetrics metrics = g2.getFontMetrics();
        int labelWidth = metrics.stringWidth(displayString);
        int x = getWidth()/2 - labelWidth/2;
        g2.setColor(StaticMethods.stringColor);
        g2.drawString(displayString, x, 25);
    }

    /**
     * This method is used to reset the displayString, and then repaint.
     * @param s String that would be reset.
     * */
    public void setDisplayString(String s) {
        displayString = s;
        repaint();
    }
}