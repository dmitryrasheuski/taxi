package web;

public enum JspPages {

    PAGE_PATTERN,
    INDEX,

    LOGIN,
    PASSENGER_REGISTRATION,
    DRIVER_REGISTRATION,

    CREATE_ORDER,
    ORDER_RESPONSE,
    CURRENT_ORDER,
    UNPROCESSED_ORDER_LIST,
    TRIP_LIST,

    CAR_REGISTRATION,
    CAR_ACTIVATION,

    INFO;

    public String getPath(){
        return null;
    }

}
