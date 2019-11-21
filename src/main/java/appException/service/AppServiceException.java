package appException.service;

public class AppServiceException extends Exception {

    public AppServiceException(String message){
        super(message);
    }
    public AppServiceException(Exception cause){
        super(cause);
    }
    public AppServiceException(String message, Exception cause){
        super(message, cause);
    }
}
