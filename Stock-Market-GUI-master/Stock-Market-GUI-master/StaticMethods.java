import java.awt.*;
import java.util.ArrayList;

/**
 * This Class is a public class with some methods and attributes which would be used in many files of this project.
 * @author Sheng Liang
 * */
public class StaticMethods {

    /**
     * This method is used to get a font.
     * @param fontName a font, such as "Ariel Black"
     * @param style the style of the font
     * @param size the size of the font
     * @param currentFont current font
     * @return a Font
     */
    public static Font getFont(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * This method is used to set a basic GridBagConstraints more easily.
     * @param x the gridx in GridBagConstraints
     * @param y the gridy in GridBagConstraints
     * @param anchor the anchor in GridBagConstraints
     * @param fill the fill in GridBagConstraints
     * @param top the param 'top' of  Insets object
     * @param left the param 'left' of  Insets object
     * @param bottom the param 'bottom' of  Insets object
     * @param right the param 'right' of  Insets object
     * @return a GridBagConstraints
     */
    public static GridBagConstraints setGbc(GridBagConstraints gbc, int x, int y, int anchor, int fill,
                                            int top, int left, int bottom, int right) {
        if (gbc == null){
            gbc = new GridBagConstraints();
        }
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.insets = new Insets(top, left, bottom, right);
        return gbc;
    }

    /**
     * This method finds the minimum in an ArrayList.
     * @param data ArrayList<Double>
     * @return the minimum in data
     * */
    public static double getMinData(ArrayList<Double> data) {
        double minData = Double.MAX_VALUE;
        for (Double Data : data) {
            minData = Math.min(minData, Data);
        }
        return minData;
    }

    /**
     * This method finds the maximum in an ArrayList.
     * @param data ArrayList<Double>
     * @return the maximum in data
     * */
    public static double getMaxData(ArrayList<Double> data) {
        double maxData = Double.MIN_VALUE;
        for (Double Data : data) {
            maxData = Math.max(maxData, Data);
        }
        return maxData;
    }

    /**
     * Some Colors that would be used in many files.
     * */
    public static final Color backgroundColor = new Color(60, 63, 65, 255);
    public static final Color riseColor = new Color(0, 255, 0, 120);
    public static final Color fallColor = new Color(255, 0, 0, 120);
    public static final Color stringColor = new Color(215, 215, 215, 180);
    public static final Color gradientColor = new Color(112, 112, 112);
}