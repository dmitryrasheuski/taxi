package web.servlets;

import web.JspPages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "logout")
public class LogoutServlet extends HttpServlet {
    private static final JspPages nextPage = JspPages.CREATE_ORDER;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();

        session.setAttribute("prevPage", null);
        session.setAttribute("nextPage", nextPage);

        req.getRequestDispatcher(JspPages.PAGE_PATTERN.getPath()).forward(req, resp);
    }
}