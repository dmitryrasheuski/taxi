package web;

public enum Servlets {

    LOGIN ("/login", JspPages.CREATE_ORDER),
    LOGOUT ("/logout", JspPages.CREATE_ORDER),
    REGISTER_PASSENGER ("/registryPassenger", JspPages.CREATE_ORDER),
    REGISTRY_DRIVER ("/registryDriver", JspPages.REGISTRY_DRIVER),

    CREATE_ORDER ("/createOrder", JspPages.ORDER_RESPONSE),
    SERVE_ORDER ("/serveOrder", null),
    CLOSE_ORDER ("/closeOrder", null),
    GET_UNPROCESSED_ORDER_LIST ("/getUnprocessedOrderList",  JspPages.UNPROCESSED_ORDER_LIST),
    GET_TRIP_LIST ("/getTripList", JspPages.TRIP_LIST),

    REGISTRY_CAR ("/registryCar", JspPages.REGISTRY_CAR),
    ACTIVATE_CAR ("/activateCar", JspPages.UNPROCESSED_ORDER_LIST),
    DEACTIVATE_CAR ("/deactivateCar", JspPages.ACTIVATING_CAR);

    private String servletPath;
    private JspPages nextPage;

    Servlets(String servletPath, JspPages nextPage) {
        this.servletPath = servletPath;
        this.nextPage = nextPage;
    }

    public static JspPages getNextJspPage(String servletPath){
        return null;
    }

}
