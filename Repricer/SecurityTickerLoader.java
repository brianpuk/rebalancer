package Repricer;

import DatabaseUtils.TableLoader;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import yahoofinance.*;



public class SecurityTickerLoader extends TableLoader {

    private ArrayList<String> tickerData = new ArrayList<String>();

    public ArrayList<String> getTickerData(){
        return tickerData;
    }

    public SecurityTickerLoader(){sql = "SELECT TICKER FROM SECURITIES";}

    /**
     * pulls ticker data from sql database
     * @param rs
     * @throws SQLException
     */
    @Override
    public void getDataFromResultSet(ResultSet rs) throws SQLException {
        tickerData.clear();
        while(rs.next()){
            tickerData.add(rs.getString("TICKER"));
        }
    }

    /**
     * validates passed in tickers to ensure they are valid stock exchange tickers
     * if not, throw exception
     * @throws IOException
     * @throws SecurityNotFoundException
     */
    public void checkTickers() throws IOException, SecurityNotFoundException {

        ArrayList<String> invalidTickers = new ArrayList<String>();

        for(String ticker : tickerData){

            Stock stock = YahooFinance.get(ticker);
            if(stock== null){
                invalidTickers.add(ticker);
            }
        }
        if(invalidTickers.size() > 0){
            throw new SecurityNotFoundException("Invalid tickers in database", invalidTickers);
        }
    }
}
