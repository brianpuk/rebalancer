package Repricer;

import yahoofinance.*;
import java.io.IOException;
import java.util.*;


public class GetSecurities{

    private ArrayList<Security> securities = new ArrayList<Security>();

    /**
     * fetches tickers from database
     * @param tickers
     * @return hashmap of all tickers with respective stock details
     * @throws IOException
     */
    public Map<String, Stock> getStocks(ArrayList<String> tickers) throws IOException {
        String[] tickersArray = tickers.toArray(new String[0]);
        Map<String, Stock> stocks = YahooFinance.get(tickersArray);
        return stocks;
    }

    /**
     * creates security objects from stocks passed in
     * @param stocks
     * @return arraylist of stock objects from passed in tickers
     */
    public ArrayList<Security> reprice(Map<String, Stock> stocks){

        ArrayList<Security> securities = new ArrayList<Security>();
        for(String ticker: stocks.keySet()){
            Security newSec = new Security();

            newSec.setPrice(stocks.get(ticker).getQuote().getPrice());
            newSec.setTicker(ticker);
            newSec.setVolume(Math.toIntExact(stocks.get(ticker).getQuote().getVolume()));

            securities.add(newSec);
        }
        return securities;
    }

}
