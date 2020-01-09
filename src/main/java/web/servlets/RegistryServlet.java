package web.servlets;

import entity.user.User;
import service.interfaces.user.IRegisterUser;
import web.HttpRequestParser;
import web.JspPages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "registry")
public class RegistryServlet extends HttpServlet {
    private static final JspPages nextPage = JspPages.CREATE_ORDER;
    private static final IRegisterUser service = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = HttpRequestParser.getUser(req);
        user = service.register(user).orElseThrow(() -> new IllegalStateException("The user is not registered"));
        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        session.setAttribute("prevPage", session.getAttribute("nextPage"));
        session.setAttribute("nextPage", nextPage);

        req.getRequestDispatcher(JspPages.PAGE_PATTERN.getPath()).forward(req, resp);
    }
}
