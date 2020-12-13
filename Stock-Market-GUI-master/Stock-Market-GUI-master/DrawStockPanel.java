import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Iterator;

/**
 * This class is a panel that draws graph for the the input ArrayList.
 * The graph is built by lines that connect every two points, and green/red bars that show the rise/fall.
 * If user moves the mouse in the panel, the nearest point would be highlighted,
 * and the details of the data point would be showed on the line graph.
 * (Actually, a resource-saving way to do this is applying JLayeredPane in the JFrame,
 *  the panel behind shows the line graph, and sends params to a transparent panel on the top,
 *  the transparent panel draws dotted lines and show the nearest point by repaint.
 *  But I think it is not very necessary in this little project.)
 * @author Sheng Liang
 */
public class DrawStockPanel extends JPanel implements MouseMotionListener, MouseListener {

    private static Color graphColor = new Color(38, 41, 43, 255);
    private static Color pointColor = new Color(186, 186, 186, 180);
    private static Color gridColor = new Color(136, 138, 141, 200);
    private static Color lineColor = new Color(186, 186, 186, 120);
    private static Stroke graphStroke = new BasicStroke(2f);
    private static int pad = 25;
    private static int labelPad = 20;
    private int i ;
    private Double min;
    private Double max;
    private ArrayList<Double> data;
    private ArrayList<String> date;
    private ArrayList<Point> graphPoints;
    private Point nearestP;
    private Point nearestX;
    private Point nearestY;
    private String textX;
    private String textY;

    /**
     * This method finds the nearest point by x axis.
     * @param x the mouse location on x axis.
     * */
    private Point getNearest(int x) {
        int dis = Integer.MAX_VALUE;
        Point nearestP = new Point();
        for (Point point : graphPoints) {
            if (Math.abs(point.x-x) < dis) {
                dis = Math.abs(point.x-x);
                nearestP = point;
            }
        }
        return nearestP;
    }

    /**
     * This method sends some new data to DrawStock object, and applies repaint method to redraw the line graph.
     * @param data a different ArrayList<Double>
     * */
    public void setData(ArrayList<Double> data) {
        this.data = data;
        this.max = StaticMethods.getMaxData(this.data);
        this.min = StaticMethods.getMinData(this.data);
        this.nearestP=this.nearestX=this.nearestY= new Point(0,0);
        invalidate();
        this.repaint();
    }

    /**
     * Constructor, initialise data, date and mouse points, and add the MouseMotionListener.
     * @param data ArrayList of a stock data, such as Open, Close and Volume.
     * @param date ArrayList of date
     * */
    public DrawStockPanel(ArrayList<Double> data, ArrayList<String> date) {
        this.data = data;
        this.date = date;
        this.max = StaticMethods.getMaxData(data);
        this.min = StaticMethods.getMinData(data);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.setBackground(StaticMethods.backgroundColor);

        //initialise the nearest point of the mouse.
        this.nearestP=this.nearestX=this.nearestY= new Point(0,0);
    }

    /**
     * This method applies Graphics2d to draw the line graph.
     * */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //create graphPoints ArrayList to store data points;
        Double xScale = ((double) getWidth() - 2*pad - labelPad) / (data.size() - 1);
        Double yScale = ((double) getHeight()- 2*pad - labelPad) / (1.1*max - 0.8*min);
        graphPoints = new ArrayList<>();
        Iterator dataIterator = data.iterator();
        i = 0;
        while (dataIterator.hasNext()) {
            Double d = (Double) dataIterator.next();
            int x1 = (int) (i * xScale + pad + labelPad);
            int y1 = (int) ((1.1*max - d) * yScale + pad);
            graphPoints.add(new Point(x1, y1));
            i++;
        }

        //draw background
        g2.setColor(graphColor);
        g2.fillRect(pad + labelPad, pad,getWidth() - 2*pad - labelPad,getHeight() - 2*pad - labelPad);

        //draw x and y boundaries
        g2.setColor(StaticMethods.stringColor);
        g2.drawLine(pad + labelPad, getHeight() - pad - labelPad,
                pad + labelPad, pad);
        g2.drawLine(getWidth() - pad, getHeight() - pad - labelPad,
                getWidth() - pad, pad);
        g2.drawLine(pad + labelPad, getHeight() - pad - labelPad,
                getWidth() - pad, getHeight() - pad - labelPad);

        //draw grid lines for y axis.
        int yDivision = 10;
        i = 0;
        String yLabel;
        while (i <= yDivision) {
            int x0 = pad + labelPad - 3;
            int y0 = getHeight() - ((i * (getHeight() - pad * 2 - labelPad)) / yDivision + pad + labelPad);
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(pad + labelPad, y0, getWidth() - pad, y0);
                g2.setColor(StaticMethods.stringColor);
                //show label number with "M" (Million) if it is too big.
                DecimalFormat df = new DecimalFormat("0");
                if ((0.8*min + (1.1*max - 0.8*min) * ((i * 1.0) / yDivision)) > 100000){
                    yLabel = df.format((0.8*min + (1.1*max - 0.8*min) * ((i * 1.0) / yDivision))/1000000) + "M";
                }
                else{yLabel = df.format(0.8*min + (1.1*max - 0.8*min) * ((i * 1.0) / yDivision));}
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
                i++;
            }
        }

        //draw lines graph and bars
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(graphStroke);
        i = 0;
        while (i < graphPoints.size() - 1) {
            //draw lines!
            g2.setColor(lineColor);
            g2.drawLine(graphPoints.get(i).x, graphPoints.get(i).y,
                    graphPoints.get(i+1).x, graphPoints.get(i+1).y);
            //draw bars!
            if (graphPoints.get(i).y - graphPoints.get(i+1).y >= 0) {//raise
                g2.setColor(StaticMethods.riseColor);
                g2.fillRect(graphPoints.get(i).x, graphPoints.get(i+1).y,
                        graphPoints.get(i+1).x - graphPoints.get(i).x,
                        graphPoints.get(i).y - graphPoints.get(i+1).y);
            }
            else{//fall
                g2.setColor(StaticMethods.fallColor);
                g2.fillRect(graphPoints.get(i).x, graphPoints.get(i).y,
                        graphPoints.get(i+1).x - graphPoints.get(i).x,
                        graphPoints.get(i+1).y - graphPoints.get(i).y);
            }
            i++;
        }//can't use Iterator here because it has to get the i+1 element

        //if user moves the mouse, nearestP would be updated to >0, then redraw the graph.
        if(this.nearestP.x != 0){
            //draw strings
            g2.setColor(StaticMethods.stringColor);
            g2.drawString(textX, this.nearestX.x-23, this.nearestX.y + g2.getFontMetrics().getHeight());//x axis label
            g2.drawString(textY, this.nearestY.x, this.nearestY.y);//y axis label
            //draw points
            g2.setStroke(oldStroke);
            g2.setColor(pointColor);
            int pointDiameter = 8;
            g2.fillOval(nearestP.x - pointDiameter/2, nearestP.y - pointDiameter/2, pointDiameter, pointDiameter);
            //draw dotted lines
            g2.setColor(lineColor);
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,0, new float[]{8,12},0));
            g2.drawLine(this.nearestX.x, this.nearestX.y, this.nearestP.x, this.nearestP.y);//to x axis
            g2.drawLine(this.nearestY.x, this.nearestY.y, this.nearestP.x, this.nearestP.y);//to y axis
        }
    }

    /**
     * Implements the MouseMotionListener.
     * Apply repaint method to redraw the graph when user moves the mouse.
     * */
    @Override
    public void mouseDragged(MouseEvent e) {}//do nothing!
    @Override
    public void mouseMoved(MouseEvent e) {
        nearestP = getNearest(e.getX());
        i = graphPoints.indexOf(nearestP);
        //Volume number is too big so it would be change to scientific notation with E automatically,
        //However that's not beautiful. So, codes bellow would change it to original form.
        if (data.get(i) > 100000){
            DecimalFormat df = new DecimalFormat("0");
            textY = df.format(data.get(i)) + "";
        }
        else{textY = data.get(i) + "";}
        textX = date.get(i) + "";
        nearestX = new Point(nearestP.x,getHeight() - pad - labelPad);
        nearestY = new Point(pad + labelPad, nearestP.y);
        this.repaint();
    }

    /**
     * Implements the MouseListener.
     * Apply repaint method to redraw the graph when user'mouse exit.
     * */
    @Override
    public void mouseClicked(MouseEvent e) {}//do nothing!
    @Override
    public void mousePressed(MouseEvent e) {}//do nothing!
    @Override
    public void mouseReleased(MouseEvent e) {}//do nothing!
    @Override
    public void mouseEntered(MouseEvent e) {}//do nothing!
    @Override
    public void mouseExited(MouseEvent e) {
        this.nearestP=this.nearestX=this.nearestY= new Point(0,0);
        invalidate();
        this.repaint();
    }

    /**
     * Main method for testing
     * */
    public static void main(String[] args) throws IOException {
        DataRetriever dr = new DataRetriever("AAPL", "01/01/2018", "12/31/2018");
        StockData stockData = dr.getStockData();
        ArrayList<Double> openList = stockData.getOpenList();
        ArrayList<String> dateList = stockData.getDateList();
        DrawStockPanel drawStock = new DrawStockPanel(openList, dateList);
        drawStock.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(drawStock);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}