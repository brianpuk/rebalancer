package Repricer;

import java.util.ArrayList;

/**
 * exception for when an invalid security is passed into rebalancer
 */
public class SecurityNotFoundException extends Exception{

    private ArrayList<String> SecuritiesNotFound = new ArrayList<String>();

    public ArrayList<String> getSecuritiesNotFound() {
        return SecuritiesNotFound;
    }

    public SecurityNotFoundException(String s, ArrayList<String> SecuritiesNotFound){
        super(s);
        this.SecuritiesNotFound = SecuritiesNotFound;
    }


}
