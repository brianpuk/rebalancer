package DatabaseUtils;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class to create connection with jdbc between java and SQL server
 */
public class DatabaseConnection {

    Connection conn = null;
    String serverName = "";//name of your personal machine in network
    String database_Name = "TRADESYS";//name of database you wish to read from
    String driver = "net.sourceforge.jtds.jdbc.Driver";
    String connectString = driver +"://"+serverName+":1433/"+database_Name;

    /**
     * creates and returns connection
     * @return connection object
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Connection openConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        connectString = "jdbc:jtds:sqlserver://"+serverName+":1433;databaseName=TRADESYS;user=;password=;";//also add username and password here
        conn = java.sql.DriverManager.getConnection(connectString, "", "");//add relevant machine username and password here
        return conn;
    }

}