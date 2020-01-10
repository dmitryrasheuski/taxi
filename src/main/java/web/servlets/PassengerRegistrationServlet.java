package web.servlets;

import entity.user.User;
import service.interfaces.user.IRegisterUser;
import web.ServletUtils;
import web.JspPages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/registryPassenger")
public class PassengerRegistrationServlet extends HttpServlet {
    private static final IRegisterUser service = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = ServletUtils.getUserFromRequest(req);
        user = service.register(user)
                .orElseThrow(() -> new IllegalStateException("The user is not registered"));
        session.setAttribute("user", user);

    }
}
