package web;

public enum Servlets {
    LOGIN,
    LOGOUT,
    REGISTER,

    ADD_DRIVER,
    ADD_CAR,

    CREATE_ORDER,
    SERVE_ORDER,
    CLOSE_ORDER,
    GET_ORDER_LIST,
    GET_TRIP_LIST,

    ACTIVATE_CAR,
    DEACTIVATE_CAR;

    private String servletPath;
    private JspPages nextPage;

    public static JspPages getNextJspPage(String servletPath){
        return null;
    }

}
