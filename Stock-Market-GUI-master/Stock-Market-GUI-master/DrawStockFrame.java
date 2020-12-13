import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

/**
 * This class is a JFrame that draws the stock data.
 * The buttons on the west choose which data would be showed on the graph (default: Close).
 * The JLabel shows the ticker symbol and number of Close changes during the period,
 * if stock raise, the label would be green, else red.
 * @author Sheng Liang
 * */
public class DrawStockFrame extends JFrame implements ActionListener {

    private DrawStockPanel drawStock;
    private StockData stockData;
    private String dateOnTitle;

    /**
     * This method creates JRadioButton and add ActionListener to it.
     * @param p JPanel that this button will add to
     * @param name name of the button
     * @param on true/false
     * @param group ButtonGroup
     * @param target ActionListener
     * */
    private void setRadioButton(JPanel p, String name, boolean on, ButtonGroup group, ActionListener target) {
        JRadioButton b = new JRadioButton(name, on);
        b.setBackground(StaticMethods.backgroundColor);
        Font f = StaticMethods.getFont("Arial Black", -1, -1, this.getFont());
        if (f != null){b.setFont(f);}
        b.setForeground(StaticMethods.stringColor);
        // add it to the specified ButtonGroup and JPanel and make the JPanel listen
        group.add(b);
        p.add(b);
        b.addActionListener(target);
    }

    /**
     * Constructor, add a DrawStock JPanel and a columnOfButtons JPanel on the Frame.
     * @param stockData StockData object
     * */
    public DrawStockFrame(StockData stockData) {

        this.stockData = stockData;
        dateOnTitle = " from " +this.stockData.getDateList().get(0)
                + " to " + this.stockData.getDateList().get(this.stockData.getDateList().size()-1);
        //initialise the title with "Close"
        this.setTitle("Stock Trend - " + this.stockData.getTicker_symbol() + " Close" + dateOnTitle);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        this.setBounds((int) width/4, (int) height/4, (int) width/2, (int) height/2);

        Container contentPane = this.getContentPane();
        drawStock = new DrawStockPanel(this.stockData.getCloseList(), this.stockData.getDateList());
        contentPane.add(drawStock, BorderLayout.CENTER);

        //Create a label to show the stock's ticker_symbol.
        JLabel ticker_symbol = new JLabel("",JLabel.CENTER);
        Font labelFont = StaticMethods.getFont("Arial Black", -1, 16, ticker_symbol.getFont());
        if (labelFont != null) {ticker_symbol.setFont(labelFont);}

        //Close0: the Close of the stock on Start Day
        //Close1: the Close of the stock on End Day
        Double Close0 = stockData.getCloseList().get(0);
        Double Close1 = stockData.getCloseList().get(stockData.getCloseList().size()-1);
        String labelText;

        //if the stock falls, use rad color and "-" on the label. Use HTML to change line.
        if (Close0 > Close1) {
            labelText = "<html><body><p align=\"center\">" + stockData.getTicker_symbol() + "<br/>"
                    + "-" + String.format("%.2f",Close0-Close1) + "</p></body></html>";
            ticker_symbol.setForeground(StaticMethods.fallColor);
        }
        //if the stock rises, use green and "+".
        else{
            labelText = "<html><body><p align=\"center\">" + stockData.getTicker_symbol() + "<br/>"
                    + "+" + String.format("%.2f",Close1-Close0) + "</p></body></html>";
            ticker_symbol.setForeground(StaticMethods.riseColor);
        }
        ticker_symbol.setText(labelText);

        //add buttons and label to a JPanel columnOfButtons
        JPanel columnOfButtons = new JPanel(new GridLayout(8,1,0,20));
        columnOfButtons.setBackground(StaticMethods.backgroundColor);
        columnOfButtons.add(new Label());//add empty label to get an empty place
        columnOfButtons.add(ticker_symbol);
        ButtonGroup group = new ButtonGroup();
        setRadioButton(columnOfButtons, "Open",false, group, this);
        setRadioButton(columnOfButtons, "Close",true, group, this);
        setRadioButton(columnOfButtons, "High",false, group, this);
        setRadioButton(columnOfButtons, "Low",false, group, this);
        setRadioButton(columnOfButtons, "Volume",false, group, this);
        columnOfButtons.add(new Label());//add empty label to get an empty place
        contentPane.add(columnOfButtons, BorderLayout.EAST);
    }

    /**
     * This method detects which button is clicked by User,
     * then send corresponding ArrayList to drawStock, repaint the line graph, and change the title.
     * */
    public void actionPerformed(ActionEvent e) {
        //Do the setData method and change title thing depending on which button is pressed.
        String command = e.getActionCommand();
        switch (command){
            case "Open":{
                drawStock.setData(stockData.getOpenList());
                this.setTitle("Stock Trend - " + this.stockData.getTicker_symbol() + " Open" + dateOnTitle);
                break;
            }
            case "Close":{
                drawStock.setData(stockData.getCloseList());
                this.setTitle("Stock Trend - " + this.stockData.getTicker_symbol() + " Close" + dateOnTitle);
                break;
            }
            case "High":{
                drawStock.setData(stockData.getHighList());
                this.setTitle("Stock Trend - " + this.stockData.getTicker_symbol() + " High" + dateOnTitle);
                break;
            }
            case "Low":{
                drawStock.setData(stockData.getLowList());
                this.setTitle("Stock Trend - " + this.stockData.getTicker_symbol() + " Low" + dateOnTitle);
                break;
            }
            case "Volume":{
                drawStock.setData(stockData.getVolumeList());
                this.setTitle("Stock Trend - " + this.stockData.getTicker_symbol() + " Volume" + dateOnTitle);
                break;
            }
        }
    }

    /**
     * Main method for testing.
     * */
    public static void main(String[] args) throws IOException {
        DataRetriever dr = new DataRetriever("FB", "01/01/2018", "12/31/2018");
        StockData stockData = dr.getStockData();
        JFrame frm = new DrawStockFrame(stockData);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }
}