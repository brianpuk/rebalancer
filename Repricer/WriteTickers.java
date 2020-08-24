package Repricer;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * class to write tickers to sql server
 */
public class WriteTickers extends TableWriter{

    /**
     * holder constructor for sql command to be run
     */
    public WriteTickers(){sql = "update SECURITIES set PRICE = (?), volume = (?) where TICKER = (?)";}

    /**
     * writes data to sql server from java
     * @param preparedStatement
     * @param security
     * @throws SQLException
     */
    @Override
    public void writeData(PreparedStatement preparedStatement, Security security) throws SQLException {
        preparedStatement.setBigDecimal(1,security.getPrice());
        preparedStatement.setInt(2, security.getVolume());
        preparedStatement.setString(3, security.getTicker());
        System.out.println(preparedStatement.executeUpdate());
    }
}
