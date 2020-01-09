package web.servlets;

import entity.user.User;
import entity.user.UserStatusType;
import service.interfaces.user.IAuthentication;
import web.ServletUtils;
import web.JspPages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "login")
public class LoginServlet extends HttpServlet {
    IAuthentication service = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = ServletUtils.getUserFromRequest(req);
        user = service.authentication(user)
                .orElseThrow(() -> new IllegalStateException("The user is not registered"));
        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        session.setAttribute("prevPage", session.getAttribute("nextPage"));
        session.setAttribute("nextPage" , getNextJspPageByUserStatus(user));

        req.getRequestDispatcher(JspPages.PAGE_PATTERN.getPath()).forward(req, resp);
    }

    private static JspPages getNextJspPageByUserStatus (User user) {
        UserStatusType userStatusType = UserStatusType.valueOf(user.getStatus().getTitle());

        switch (userStatusType){
            case ADMIN: return JspPages.ADD_DRIVER;
            case DRIVER: return JspPages.ORDER_LIST;
            default: return JspPages.CREATE_ORDER;
        }
    }
}
