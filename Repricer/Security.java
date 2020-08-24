package Repricer;
import java.math.BigDecimal;

/**
 * Represents one security with ticker price, and volume in stock market
 */
public class Security {

    private String ticker = "";
    private BigDecimal price;
    private int volume = 0;

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }
}
