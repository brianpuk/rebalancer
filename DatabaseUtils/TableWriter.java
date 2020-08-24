package DatabaseUtils;
import Rebalancer.Order;
import java.sql.*;
import java.util.ArrayList;

/**
 * methods for writing data to sql table from java
 */
public abstract class TableWriter {

    protected String sql = "";
    public ArrayList<Order> orders = new ArrayList<Order>();

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    /**
     * creates connection and writes data to sql database
     * @param database
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ProcessException
     */
    public void write(DatabaseConnection database) throws SQLException, ClassNotFoundException, ProcessException {

        Connection conn = database.openConnection();
        PreparedStatement preparedStatement = null;
        try {

            System.out.println("Creating prepared statement...");
            preparedStatement = conn.prepareStatement(sql);

            for(Order order: orders){
                writeData(preparedStatement,order);
            }

        } catch (SQLException se) {
            se.printStackTrace();
            throw new ProcessException("Table write failed", se);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProcessException("Table write failed", e);
        } finally {
            try {
                if (conn != null)
                    conn.close();
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se) {
                se.printStackTrace();
                throw new ProcessException("Table write failed", se);
            }
        }
        System.out.println("process complete");
    }

    public abstract void writeData(PreparedStatement preparedStatement, Order order) throws SQLException;

}




