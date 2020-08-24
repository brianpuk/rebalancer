package DatabaseUtils;
import java.sql.*;

/**
 * abstract class for loading data from sql table into java
 */
public abstract class TableLoader {

    protected String sql = "";

    /**
     * loads passed in database from sql server to java
     * @param dataBase object represents database to be loaded
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ProcessException
     */
    public void load(DatabaseConnection dataBase) throws SQLException, ClassNotFoundException, ProcessException {
        Connection conn = dataBase.openConnection();
        Statement stmt = null;
        try {

            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            getDataFromResultSet(rs);
            rs.close();

        } catch (SQLException se) {

            se.printStackTrace();
            throw new ProcessException("Table load failed", se);

        } catch (Exception e) {

            e.printStackTrace();
            throw new ProcessException("Table load failed", e);

        } finally {
            try {

                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
                throw new ProcessException("Table load failed", se);
            }
        }
        System.out.println("Connection ended");
    }

    public abstract void getDataFromResultSet(ResultSet rs) throws SQLException;
}

