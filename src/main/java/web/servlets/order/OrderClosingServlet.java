package web.servlets.order;

import entity.order.Order;
import service.interfaces.order.IOrderClosing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/orderClosing")
public class OrderClosingServlet extends HttpServlet {
    private static IOrderClosing orderService = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Order currentOrder = (Order) session.getAttribute("currentOrder");
        orderService.closeOrder(currentOrder);
        session.removeAttribute("currentOrder");

    }
}
