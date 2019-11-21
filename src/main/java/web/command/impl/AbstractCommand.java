package web.command.impl;

import appException.controller.AppRequestParameterException;
import entity.user.User;
import entity.user.UserBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractCommand{
    private static final Logger logger = Logger.getLogger(AbstractCommand.class);
    HttpServletRequest req;

    AbstractCommand(HttpServletRequest req){
        this.req = req;
    }

    String getParameter(String name, boolean notNull) throws AppRequestParameterException {
        logger.debug("start getParameter(Str, bool)");

        String param = req.getParameter(name);
        if (notNull && (param == null || param.trim().isEmpty())) {
            String message = String.format("parameter '%s' is empty or null", name);
            logger.info("getParameter(Str, bool) " + message + " throw new AppRequestParameterException");
            throw new AppRequestParameterException(message);
        }
        if( param == null || param.trim().isEmpty()){
            logger.debug("getParameter(Str, bool) parameter " + name + " is empty or null");
            return null;
        }

        logger.debug("finish getParameter(Str, bool)");
        return param.trim();

    }
    int getPhone() throws AppRequestParameterException {
        logger.debug("start getPhone()");
        String phone = getParameter("phone", true);
        Pattern pattern = Pattern.compile("^\\+375-(29|33|44)-(\\d{3})-(\\d{2})-(\\d{2})$");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.find()) {
            StringBuilder num = new StringBuilder();
            num.append(matcher.group(1));
            num.append(matcher.group(2));
            num.append(matcher.group(3));
            num.append(matcher.group(4));

            logger.debug("start getPhone()");
            return Integer.valueOf(num.toString());
        }
        logger.info("getPhone() phone format is incorrect");
        throw new AppRequestParameterException("phone format is incorrect");
    }
    User userByRequest() throws AppRequestParameterException {
        logger.debug("start userByRequest()");

        int phone = getPhone();
        String name = getParameter("name", true);
        String surname = getParameter("surname", true);
        String password = getParameter("password", true);
        String status = getParameter("status", false);

        logger.debug("finish userByRequest()");
        return UserBuilder.createUser().setPhone(phone).setName(name).setSurname(surname).setPassword(password).setStatus(status).getUser();

    }

}
