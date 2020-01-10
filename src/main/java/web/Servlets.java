package web;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PUBLIC)
public enum Servlets {

    LOGIN                       ("/login",                      null),
    LOGOUT                      ("/logout",                     JspPages.CREATE_ORDER),
    PASSENGER_REGISTRATION      ("/passengerRegistration",      JspPages.CREATE_ORDER),
    DRIVER_REGISTRATION         ("/driverRegistration",         JspPages.DRIVER_REGISTRATION),

    ORDER_CREATION              ("/orderCreation",              JspPages.ORDER_RESPONSE),
    ORDER_SERVICE               ("/orderService",               JspPages.CURRENT_ORDER),
    ORDER_CLOSING               ("/orderClosing",               JspPages.UNPROCESSED_ORDER_LIST),
    GET_UNPROCESSED_ORDER_LIST  ("/getUnprocessedOrderList",    JspPages.UNPROCESSED_ORDER_LIST),
    GET_TRIP_LIST               ("/getTripList",                JspPages.TRIP_LIST),

    CAR_REGISTRATION            ("/carRegistration",            JspPages.CAR_REGISTRATION),
    CAR_ACTIVATION              ("/carActivation",              JspPages.UNPROCESSED_ORDER_LIST),
    CAR_DEACTIVATION            ("/carDeactivation",            JspPages.CAR_ACTIVATION);

    private String urlPattern;
    private JspPages nextPage;

    Servlets(String urlPattern, JspPages nextPage) {
        this.urlPattern = urlPattern;
        this.nextPage = nextPage;
    }

    public static JspPages getNextJspPage(String urlPattern){
        return null;
    }

}
