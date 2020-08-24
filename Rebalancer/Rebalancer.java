package Rebalancer;

import DatabaseUtils.DatabaseConnection;
import DatabaseUtils.ProcessException;
import Repricer.SecurityNotFoundException;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.*;

/**
 * class to rebalance a set of securities against a model
 */
public class Rebalancer {

    private Hashtable<String, Double> holdingData = new Hashtable<String,Double>();
    private Hashtable<String, Double> modelData = new Hashtable<String,Double>();
    private ArrayList<Double> percentVals = new ArrayList<Double>();// percent change for each security from
    private ArrayList <Double> subbedVal = new ArrayList<Double>();// amount of change needed for each holding security to match model
    private ArrayList<String> buyOrSell = new ArrayList<String>();
    private ArrayList<Order> orders = new ArrayList<Order>();

    public static void main(String[] args) throws SQLException, ClassNotFoundException, SecurityNotFoundException {
        try{
            DatabaseConnection dataBase = new DatabaseConnection();

            HoldingLoader holdingTable = new HoldingLoader();

            ModelLoader modelTable = new ModelLoader();

            holdingTable.load(dataBase);
            modelTable.load(dataBase);

            Hashtable<String, Double> holdingData = holdingTable.getHoldingData();
            Hashtable<String, Double> modelData = modelTable.getModelData();

            Rebalancer q = new Rebalancer();
            ArrayList<Order> finalOrderList =  q.rebalance(holdingData, modelData);

            q.writeOrders(finalOrderList, dataBase);

        } catch(ProcessException e){
            System.out.println("Process failed" + e.getMessage());
        }
    }

    /**
     * Merges holdings securities against model securities to ensure no overlap/duplicates
     * @param holdingData
     * @param modelData
     * @param totalValHoldings
     * @return hashtable of target amounts
     */
    private Hashtable<String, Double> mergeModelHoldings(Hashtable<String, Double> holdingData, Hashtable<String, Double> modelData, double totalValHoldings) {
        Hashtable<String, Double> targetAmounts = new Hashtable<String,Double>();

        for(String security : modelData.keySet()){
            targetAmounts.put(security,((modelData.get(security)/100) * totalValHoldings));
        }

        for(String security : holdingData.keySet()){
            if(!targetAmounts.containsKey(security)) {
                targetAmounts.put(security,0.0);
            }
        }
        return targetAmounts;
    }

    /**
     * aggregates total dollar value of holdings securities
     * @param holdingData
     * @return double of total dollar value of holdings securities
     */
    public double getTotalHoldings(Hashtable<String,Double> holdingData){
        double totalValHoldings = 0.0;

        for(String security:holdingData.keySet()){

            totalValHoldings+=holdingData.get(security);

        }
        return totalValHoldings;
    }


    /**
     * Calculates how much has to subtracted/added from original holdings to match model
     * @param targetAmounts
     * @param holdingData
     * @return hashtable of how much each security has to change to fit model
     */
    private Hashtable<String, Double> calcSubVal(Hashtable<String,Double> targetAmounts, Hashtable<String,Double> holdingData){
        Hashtable<String, Double> subVal = new Hashtable<String, Double>();
        for(String ticker : holdingData.keySet()){
            subVal.put(ticker, targetAmounts.get(ticker) - (holdingData.get(ticker)));
        }
        return subVal;
    }

    /**
     * determine for each security in the holdings whether or not a purchase or sale must be made to match the model
     * @param subVal
     * @return hashtable of each security and its correlating transaction type
     */
    private Hashtable<String,String> calcTransactionType(Hashtable<String, Double> subVal){
        Hashtable<String, String> transType = new Hashtable<String, String>();
        for(String ticker : subVal.keySet()){

            if(subVal.get(ticker) > 0){
                transType.put(ticker,"B");
            }

            else if(subVal.get(ticker) == 0){
                transType.put(ticker,"N/A");
            }

            else{
                transType.put(ticker,"S");
            }

        }
        return transType;
    }

    /**
     *creates order object according to transaction value and type
     * @param subVal
     * @param transType
     */
    private void createOrders(Hashtable<String,Double> subVal, Hashtable<String,String> transType){
        for(String ticker : subVal.keySet()){
            Order addOrder = new Order();
            addOrder.setTicker(ticker);
            addOrder.setAmount(subVal.get(ticker));
            addOrder.setTransactionType(transType.get(ticker));
            orders.add(addOrder);
        }
    }

    /**
     * rebalances holdings and creates all orders
     * @param holdingData
     * @param modelData
     * @return arraylist of orders
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<Order> rebalance(Hashtable<String,Double> holdingData, Hashtable<String,Double> modelData) throws SQLException, ClassNotFoundException {

        double totalValHoldings = getTotalHoldings(holdingData);
        Hashtable<String, Double> targetAmounts = mergeModelHoldings(holdingData, modelData, totalValHoldings);
        Hashtable<String, Double> subVal = calcSubVal(targetAmounts,holdingData);
        Hashtable<String,String> transType = calcTransactionType(subVal);

        createOrders(subVal, transType);

        return orders;

    }

    /**
     * writes orders to sql server database
     * @param orders
     * @param dataBase
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ProcessException
     */
    public void writeOrders(ArrayList<Order> orders, DatabaseConnection dataBase) throws SQLException, ClassNotFoundException, ProcessException {
        WriteOrders write1 = new WriteOrders();

        write1.setOrders(orders);
        write1.write(dataBase);

    }
}
