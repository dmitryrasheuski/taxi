package web;

import entity.user.User;
import entity.user.UserStatus;
import entity.user.UserStatusType;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServletUtils {
    public static Optional<String> getParameterFromRequest (String name, HttpServletRequest req){
        String value = req.getParameter(name);
        return value != null ? Optional.of(value.trim()) : Optional.empty();
    }
    public static Optional<Integer> getPhoneFromRequest (HttpServletRequest req) {

        Optional<String> phoneStr = getParameterFromRequest("phone", req);
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
    public static User getUserFromRequest(HttpServletRequest req) {

        Integer phone = getPhoneFromRequest(req).orElse(null);
        String name = getParameterFromRequest("name", req).orElse(null);
        String surname = getParameterFromRequest("surname", req).orElse(null);
        String password = getParameterFromRequest("password", req).orElse(null);
        String status = getParameterFromRequest("status", req).orElse(null);

        return User.builder()
                .phone(phone)
                .name(name)
                .surname(surname)
                .password(password)
                .status(UserStatus.getInstance(UserStatusType.valueOf(status.toUpperCase())))
                .build();
    }

}
