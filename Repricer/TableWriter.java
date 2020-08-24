package Repricer;

import DatabaseUtils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

/**
 * class to hold methods for writing data to sql server database
 */
public abstract class TableWriter {

    protected String sql = "";
    public ArrayList<Security> securities = new ArrayList<Security>();

    public void setSecurities(ArrayList<Security> securities){
        this.securities = securities;
    }

    /**
     * writes data to specified database
     * @param database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void write(DatabaseConnection database) throws SQLException, ClassNotFoundException {

        Connection conn = database.openConnection();
        try {

            System.out.println("Creating prepared statement...");
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            for(Security security: securities){
                writeData(preparedStatement,security);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        System.out.println("Connection closed");
    }

    /**
     * class to write data specified on which database using prepared statement
     * @param preparedStatement
     * @param security
     * @throws SQLException
     */
    public abstract void writeData(PreparedStatement preparedStatement, Security security) throws SQLException;

}

