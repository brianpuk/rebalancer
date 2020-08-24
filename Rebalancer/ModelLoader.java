package Rebalancer;

import DatabaseUtils.TableLoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class ModelLoader extends TableLoader {

    private Hashtable<String, Double> modelData = new Hashtable<String, Double>();

    public Hashtable<String, Double> getModelData(){
        return modelData;
    }

    /**
     * set sql commands to be run
     */
    public ModelLoader(){
        sql = "select SEC, PER from MODEL";
    }

    /**
     * fetches data from resultset in sql server database
     * @param rs
     * @throws SQLException
     */
    @Override
    public void getDataFromResultSet(ResultSet rs) throws SQLException {
        modelData.clear();
        while(rs.next()){
            String sec = rs.getString("SEC");
            double amt = rs.getDouble("PER");
            modelData.put(sec,amt);
        }
    }

}

