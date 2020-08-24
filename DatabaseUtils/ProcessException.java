package DatabaseUtils;

/**
 * Exception class for when there is an error in processsing data to sql server
 */
public class ProcessException extends Exception {

    public ProcessException(String message, Throwable inner){
        super(message, inner);
    }

}
