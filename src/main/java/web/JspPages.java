package web;

import java.util.HashMap;
import java.util.Map;

public enum JspPages {
    DEFAULT("default.jsp", "/view/jsp/"),
    ORDER("order.jsp", "/view/jsp/content/"),
    ORDER_RESPONSE("orderResponse.jsp", "/view/jsp/content/"),
    REGISTRY("registry.jsp", "/view/jsp/content/"),
    REGISTRY_RESPONSE("registryResponse.jsp", "/view/jsp/content/"),
    LOGIN("login.jsp", "/view/jsp/content/"),
    LOGOUT("logout.jsp", "/view/jsp/content/"),
    TRIP_LIST("tripList.jsp", "/view/jsp/content/"),
    INFO("info.jsp", "/view/jsp/content/");

    private String pagePath;
    private String pageName;

    JspPages(String pageName, String folder){
        this.pageName = pageName;
        this.pagePath = folder + pageName;
    }

    public String getPagePath(){
        return pagePath;
    }
    public String getPageName() {
        return pageName;
    }

    private static Map<String, JspPages> map = new HashMap<>();
    static {
        for(JspPages jspPages : values()){
            map.put(jspPages.pageName, jspPages);
        }
    }
    public static JspPages getJspPageByPageName(String pageName){
        if(pageName != null){
            return map.get(pageName);
        }
        return null;
    }
}
