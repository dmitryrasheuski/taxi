package web.command.impl;

import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum CommandTypes {
    CREATE_ORDER("createOrder", CreateOrderCommand::new),
    GET_TRIP_LIST("getTripList", GetTripListCommand::new),
    GO_TO("goTo", GoToCommand::new),
    LOGIN("login", LoginCommand::new),
    LOGOUT("logout", LogoutCommand::new),
    REGISTRY("registry", RegistryCommand::new);

    private String strPattern;
    private Function<HttpServletRequest, Command> function;

    CommandTypes(String strPattern, Function<HttpServletRequest, Command> function) {
        this.strPattern = strPattern;
        this.function = function;
    }

    private static Map<String, CommandTypes> map = new HashMap<>();
    static {
        for (CommandTypes command : values()) {
            map.put(command.strPattern, command);
        }
    }
    public static Command getCommand(HttpServletRequest req) {
        String strPattern = req.getParameter("command");
        CommandTypes type = null;
        if (strPattern != null){
            type = map.get(strPattern);
        }
        return type != null ? type.function.apply(req) : GO_TO.function.apply(req);
    }
}
