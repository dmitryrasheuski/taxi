package web;

import entity.user.User;
import entity.user.UserStatus;
import entity.user.UserStatusType;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestParser {
    public static Optional<String> getParameter(String name, HttpServletRequest req){

        if(name == null) return Optional.empty();
        String param = req.getParameter(name);
        return param != null ? Optional.of(param.trim()) : Optional.empty();

    }
    public static Optional<Integer> getPhone(HttpServletRequest req) {

        Optional<String> phoneStr = getParameter("phone", req);
        if(!phoneStr.isPresent()) return Optional.empty();


        Integer phone = null;
        Pattern pattern = Pattern.compile("^\\+375-(29|33|44)-(\\d{3})-(\\d{2})-(\\d{2})$");
        Matcher matcher = pattern.matcher(phoneStr.get());
        if (matcher.find()) {
            StringBuilder num = new StringBuilder();
            num.append(matcher.group(1));
            num.append(matcher.group(2));
            num.append(matcher.group(3));
            num.append(matcher.group(4));

            phone = Integer.valueOf(num.toString());
        }

        return Optional.ofNullable(phone);

    }
    public static User getUser(HttpServletRequest req) {

        int phone = getPhone(req).orElseThrow(() -> new IllegalStateException("The user must have a phone"));
        String name = getParameter("name", req).orElse(null);
        String surname = getParameter("surname", req).orElse(null);
        String password = getParameter("password", req).orElse(null);
        String status = getParameter("status", req).orElse(null);

        return User.builder()
                .phone(phone)
                .name(name)
                .surname(surname)
                .password(password)
                .status(UserStatus.getInstance(UserStatusType.valueOf(status.toUpperCase())))
                .build();

    }

}
