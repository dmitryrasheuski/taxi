package appException.dao;

public class AppSqlException extends Exception {

    public AppSqlException(String message){
        super(message);
    }
    public AppSqlException(Exception cause){
        super(cause);
    }
    public AppSqlException(String message, Exception cause){
        super(message, cause);
    }

}
