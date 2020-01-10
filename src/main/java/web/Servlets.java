package web;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PUBLIC)
public enum Servlets {

    LOGIN                       ("/login",                      JspPages.CREATE_ORDER),
    LOGOUT                      ("/logout",                     JspPages.CREATE_ORDER),
    REGISTER_PASSENGER          ("/registryPassenger",          JspPages.CREATE_ORDER),
    REGISTRY_DRIVER             ("/registryDriver",             JspPages.REGISTRY_DRIVER),

    CREATE_ORDER                ("/createOrder",                JspPages.ORDER_RESPONSE),
    SERVE_ORDER                 ("/serveOrder",                 JspPages.CURRENT_ORDER),
    CLOSE_ORDER                 ("/closeOrder",                 JspPages.UNPROCESSED_ORDER_LIST),
    GET_UNPROCESSED_ORDER_LIST  ("/getUnprocessedOrderList",    JspPages.UNPROCESSED_ORDER_LIST),
    GET_TRIP_LIST               ("/getTripList",                JspPages.TRIP_LIST),

    REGISTRY_CAR                ("/registryCar",                JspPages.REGISTRY_CAR),
    ACTIVATE_CAR                ("/activateCar",                JspPages.UNPROCESSED_ORDER_LIST),
    DEACTIVATE_CAR              ("/deactivateCar",              JspPages.ACTIVATING_CAR);

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
