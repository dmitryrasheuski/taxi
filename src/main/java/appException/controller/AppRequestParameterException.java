package appException.controller;

public class AppRequestParameterException extends Exception {

    public AppRequestParameterException(String message){
        super(message);
    }
    public AppRequestParameterException(Exception cause){
        super(cause);
    }
    public AppRequestParameterException(String message, Exception cause){
        super(message, cause);
    }

}
