package Rebalancer;


import DatabaseUtils.TableLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;


public class HoldingLoader extends TableLoader {

    private Hashtable<String, Double> holdingData = new Hashtable<String, Double>();

    public Hashtable<String, Double> getHoldingData(){
        return holdingData;
    }

    public HoldingLoader(){
        sql = "select SEC, AMT from HOLDING";
    }

    /**
     * fetches data from resultset in sql server database
     * @param rs
     * @throws SQLException
     */
    @Override
    public void getDataFromResultSet(ResultSet rs) throws SQLException {
        holdingData.clear();
        while(rs.next()){
            String sec = rs.getString("SEC");
            double amt = rs.getDouble("AMT");
            holdingData.put(sec,amt);
        }

    }


}
