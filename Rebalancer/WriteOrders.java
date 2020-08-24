package Rebalancer;

import DatabaseUtils.TableWriter;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WriteOrders extends TableWriter {

    public WriteOrders(){
        sql = "INSERT INTO ORD (SEC,TRANS,AMT) VALUES (?,?,?)";
    }

    /**
     * writes data according to prepared statement to sql server database
     * @param preparedStatement
     * @param order
     * @throws SQLException
     */
    @Override
    public void writeData(PreparedStatement preparedStatement, Order order) throws SQLException {

        preparedStatement.setString(1,order.getTicker());
        preparedStatement.setString(2,order.getTransactionType());
        preparedStatement.setBigDecimal(3,new BigDecimal(order.getAmount()));

        System.out.println(preparedStatement.executeUpdate());

    }


}
