import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * This Class retrieves stock data by URL and store them as a StockData object.
 * The StockData object contains the ticker symbol, and an ArrayList of the stock data during a period.
 * Each element of the stock data ArrayList is a StockEachDay object, which contains 6 attributes.
 * @author Sheng Liang
 */
public class DataRetriever {

    private String ticker_symbol;
    private String URL;

    /**
     * This getter retrieves data from the URL, and store each line as a StockEachDay object into an ArrayList.
     * @return StockData object of the stock data.
     * @exception IOException On input error.
     */
    public StockData getStockData() throws IOException {
        ArrayList<Object> StockList = new ArrayList<>();
        URL obj = new URL(this.URL);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {// success
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String eachLine = reader.readLine();
            boolean flag = false;
            while (eachLine != null) {
                if (flag){//Use a flag to skip the first line
                    String[] Each = eachLine.split(",");
                    StockEachDay eachDay = new StockEachDay(Each[0], Each[1], Each[2], Each[3], Each[4], Each[5]);
                    //missed date is not allowed, other missing data would be handled in StockEachDay.
                    if(Each[0] != null && !Each[0].equals("")){
                        StockList.add(eachDay);
                    }
                }
                else{
                    flag = true;
                }
                eachLine = reader.readLine();
            }
            reader.close();
            //reverse the ArrayList to make the earliest date in the front, since the latest date is on the top in the URL.
            Collections.reverse(StockList);
            return new StockData(StockList, ticker_symbol);
        } else {//Wrong URL
            return null;
        }
    }

    /**
     * Constructor, input the three params of a stock to get its full URL.
     * @param ticker_symbol String such as "FB"
     * @param startDate String date such as 01/01/2019
     * @param endDate String date such as 01/01/2019
     */
    public DataRetriever(String ticker_symbol, String startDate, String endDate) {
        //Set num_rows that allow to download.
        int num = 0;
        try {
            Date start = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
            Date end = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
            num = (int) ((end.getTime() - start.getTime()) / (1000*3600*24));//convert ms to day
        }
        catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        this.ticker_symbol = ticker_symbol;
        this.URL = "https://quotes.wsj.com/" + ticker_symbol
                + "/historical-prices/download?MOD_VIEW=page&num_rows=" +
                + num
                + "&startDate="
                + startDate + "&endDate=" + endDate;
    }

    /**
     * Main method for testing.
     * */
    public static void main(String[] args) throws IOException {
        DataRetriever dr=new DataRetriever("AAPL", "01/01/2018", "12/31/2018");
        StockData Stock = dr.getStockData();
        System.out.println(Stock.getCloseList());
    }
}