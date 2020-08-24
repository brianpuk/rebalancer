package Repricer;

import DatabaseUtils.DatabaseConnection;
import DatabaseUtils.ProcessException;
import yahoofinance.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * class to reprice and fetch real time price of securities
 */
public class Reprice {

    /**
     * fetches real time price of all valid passed in securities
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ProcessException
     */
    public void Reprice() throws IOException, SQLException, ClassNotFoundException, ProcessException {

        try {

            DatabaseConnection dataBase = new DatabaseConnection();
            SecurityTickerLoader securityTable = new SecurityTickerLoader();

            securityTable.load(dataBase);
            securityTable.checkTickers();

            ArrayList<String> securityTickers = securityTable.getTickerData();

            System.out.println(securityTickers.toString());

            GetSecurities refreshPrice = new GetSecurities();
            Map<String, Stock> newPrices = refreshPrice.getStocks(securityTickers);

            ArrayList<Security> newSecurities = refreshPrice.reprice(newPrices);

            WriteTickers updateTable = new WriteTickers();
            updateTable.setSecurities(newSecurities);

            updateTable.write(dataBase);

        } catch (SecurityNotFoundException EXC) {
            System.out.println("failed run, Invalid tickers at: " + EXC.getMessage() + ":" + EXC.getSecuritiesNotFound().toString());
        }
    }


}
