package web;

public enum JspPages {

    PAGE_PATTERN,
    INDEX,

    LOGIN,
    REGISTRY_PASSENGER,
    REGISTRY_DRIVER,

    CREATE_ORDER,
    ORDER_RESPONSE,
    UNPROCESSED_ORDER_LIST,
    TRIP_LIST,

    REGISTRY_CAR,
    ACTIVATING_CAR,

    INFO;

    public String getPath(){
        return null;
    }

}
