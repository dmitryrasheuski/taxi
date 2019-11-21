package web.command.impl;

import web.command.Command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public enum CommandTypes {
    CREATE_ORDER("createOrder"){
        @Override
        Command createCommand(HttpServletRequest req) {
            return new CreateOrderCommand(req);
        }
    },
    GET_TRIP_LIST("getTripList"){
        @Override
        Command createCommand(HttpServletRequest req) {
            return new GetTripListCommand(req);
        }
    },
    GO_TO("goTo") {
        @Override
        Command createCommand(HttpServletRequest req) {
            return new GoToCommand(req);
        }
    },
    LOGIN("login") {
        @Override
        Command createCommand(HttpServletRequest req) {
            return new LoginCommand(req);
        }
    },
    LOGOUT("logout") {
        @Override
        Command createCommand(HttpServletRequest req) {
            return new LogoutCommand(req);
        }
    },
    REGISTRY("registry") {
        @Override
        Command createCommand(HttpServletRequest req) {
            return new RegistryCommand(req);
        }
    };

    private String strPattern;

    CommandTypes(String strPattern) {
        this.strPattern = strPattern;
    }

    abstract Command createCommand(HttpServletRequest req);

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
        return type != null ? type.createCommand(req) : GO_TO.createCommand(req);
    }
}
