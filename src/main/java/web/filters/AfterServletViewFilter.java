package web.filters;

import web.JspPages;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AfterServletViewFilter extends HttpFilter {
    private static final String pagePatternPath = JspPages.PAGE_PATTERN.getPath();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(req, resp);

        HttpSession session = req.getSession();
        JspPages nextPage = Servlets.getNextJspPage(req.getServletPath());
        JspPages prevPage = (JspPages)session.getAttribute("nextPage");

        session.setAttribute("prevPage", prevPage);
        session.setAttribute("nextPage", nextPage);

        req.getRequestDispatcher(pagePatternPath).forward(req, resp);
    }
}
