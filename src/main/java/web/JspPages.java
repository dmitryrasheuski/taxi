package web;

public enum JspPages {

    PAGE_PATTERN("webapp/view/jsp/pagePattern.jsp"),
    INDEX("webapp/index.jsp"),

    LOGIN(""),
    PASSENGER_REGISTRATION(""),
    DRIVER_REGISTRATION(""),

    CREATE_ORDER(""),
    ORDER_RESPONSE(""),
    CURRENT_ORDER(""),
    ORDER_WAITING_LIST(""),
    TRIP_LIST(""),

    CAR_REGISTRATION(""),
    CAR_ACTIVATION(""),

    INFO("");

    private String path;

    JspPages(String path){
        this.path = path;
    }

    public String getPath(){
        return null;
    }

}
