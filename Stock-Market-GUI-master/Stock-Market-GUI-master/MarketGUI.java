import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * This class is the main GUI of the project.
 * It contains three panels on the ContentPane: logoPanel, centerPanel and messagePanel.
 * The logoPanel show a logo "MarketGUI" and my github URL. The messagePanel show some messages after user operates.
 * The centerPanel is where the main features are. It contains 7 JComboBoxes and a JButton.
 * After select all JComboBoxes and click the JButton, a new JFrame drawStockFrame will be opened.
 * User could open 3 new drawStockFrame windows at most.
 * In drawStockFrame, a graph of the stock Close data will be showed.
 * And user can select which data would be display by clicking the JButtons on the right.
 * This GUI uses NimbusLookAndFeel if it's available on user's computer,
 * which is supported by javax.swing.plaf.nimbus.NimbusLookAndFeel.
 * @author Sheng Liang
 * */
public class MarketGUI extends JFrame {

    private MessagePanel messagePanel;
    private CenterPanel centerPanel;
    private ArrayList<String> stockLog;

    /**
     * This method would be used in centerPanel, to check whether a new window would be opened.
     * */
    public ArrayList<String> getStockLog() {
        return stockLog;
    }

    /**
     * This method would be used in the centerPanel. It receives a status from the centerPanel,
     * if the status is OK, then gets the stockData and open a new DrawStockFrame to draw the graph,
     * otherwise displays error messages by using messagePanel.
     * @param status The 8 status that get from centerPanel.
     */
    public void getMessage(int status){
        if (status == 0){//OK
            StockData stockData = centerPanel.getStockData();
            String lastStock = stockData.getTicker_symbol()
                    + " from " + stockData.getDateList().get(0)
                    + " to " + stockData.getDateList().get(stockData.getDateList().size()-1);
            //print the ticker symbol and data duration on messagePanel,
            //the gotten duration depends on the csv file on the URL,
            //which is not always the same with selected since no data on holidays.
            messagePanel.setDisplayString("Last Gotten Data : " + lastStock);
            //create new window
            DrawStockFrame drawStockFrame= new DrawStockFrame(stockData);
            //close drawStockFrame when exit it, not all windows.
            drawStockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            drawStockFrame.setVisible(true);
            MarketGUI This=this;
            //add WindowListener to the new window., countWindows-- when it closed.
            drawStockFrame.addWindowListener(new WindowAdapter() {
                public void windowOpened(WindowEvent e) {//countWindows++, add new gotten duration when it opened.
                    This.stockLog.add(lastStock);
                }
                public void windowClosed(WindowEvent e) {//countWindows--, remove gotten duration when it opened.
                    This.stockLog.remove(lastStock);
                }
            });
        }
        else if (status == 1){//Error1
            messagePanel.setDisplayString("Opened Too Many Windows.");
        }
        else if (status == 2){//Error2
            messagePanel.setDisplayString("Please Select Stock, Start Date And End Date.");
        }
        else if (status == 3){//Error3
            messagePanel.setDisplayString("End Date Error: Later Than Today.");
        }
        else if (status == 4){//Error4
            messagePanel.setDisplayString("Start Date Error: Not Before End Date.");
        }
        else if (status == 5){//Error5
            messagePanel.setDisplayString("Don't Have Any Data.");
        }
        else if (status == 6){//Error6
            messagePanel.setDisplayString("Only Have One Data Point, Not Enough For A Graph.");
        }
        else if (status == 7){//Error7
            messagePanel.setDisplayString("You Have Gotten The Same Data.");
        }
    }

    private MarketGUI() {
        JPanel MainPanel = new JPanel();
        MainPanel.setLayout(new GridBagLayout());
        this.setContentPane(MainPanel);
        this.stockLog = new ArrayList<>();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        this.setBounds((int) width/4, (int) height/4, (int) width/2, (int) height/2);
        this.setTitle("MarketGUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //There's no need to resize, since it's only some ComboBoxes here, it would be not beautiful after resize.
        this.setResizable(false);

        //North: logoPanel
        JPanel logoPanel = new LogoPanel();
        logoPanel.setLayout(new GridBagLayout());
        GridBagConstraints MainGBC;
        MainGBC = StaticMethods.setGbc(null, 0,0,10,1,0,0,0,0);
        MainPanel.add(logoPanel, MainGBC);
        logoPanel.setPreferredSize(new Dimension(800, 150));

        //South: messagePanel
        messagePanel = new MessagePanel();
        MainGBC = StaticMethods.setGbc(MainGBC, 0,2,10,1,0,0,0,0);
        messagePanel.setDisplayString("Welcome to MarketGUI !");
        MainPanel.add(messagePanel, MainGBC);
        messagePanel.setPreferredSize(new Dimension(800, 40));

        //Center: centerPanel
        centerPanel= new CenterPanel(this);
        MainGBC = StaticMethods.setGbc(MainGBC, 0,1,10,1,0,0,0,0);
        MainPanel.add(centerPanel, MainGBC);
        centerPanel.setPreferredSize(new Dimension(800, 220));
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        //Use UIManager to set LookAndFeel to NimbusLookAndFeel, if it's available on users' computer.
        UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
        for(UIManager.LookAndFeelInfo theme:lookAndFeels){
           if (theme.toString().equals("javax.swing.UIManager$LookAndFeelInfo[Nimbus javax.swing.plaf.nimbus.NimbusLookAndFeel]")){
               UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
               break;
           }
        }
        //Start!
        MarketGUI frame = new MarketGUI();
        frame.pack();
        frame.setVisible(true);
    }
}