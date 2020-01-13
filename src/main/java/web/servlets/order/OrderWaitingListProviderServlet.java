package web.servlets.order;

import service.interfaces.order.IOrderWaitingListProviding;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/getOrderWaitingList")
public class OrderWaitingListProviderServlet extends HttpServlet {
    private static IOrderWaitingListProviding listProvider = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        session.setAttribute("orderWaitingList", listProvider.getOrderWaitingList() );

    }
}
