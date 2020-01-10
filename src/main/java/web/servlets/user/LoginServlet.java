package web.servlets.user;

import entity.user.User;
import service.interfaces.user.IAuthentication;
import web.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    IAuthentication service = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = ServletUtils.getUserFromRequest(req);
        user = service.authentication(user)
                .orElseThrow(() -> new IllegalStateException("The user is not registered"));
        session.setAttribute("user", user);

    }
}