package Rebalancer;

/**
 * Represents a singular financial order
 */
public class Order {

    private String ticker = "";//stock ticker for transaction
    private String transactionType ="";//type of transaction, buy or sell
    private double amount = 0.0;//dollar amount for how much the order is for

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public String getTicker() {
        return ticker;
    }

}
