package web.servlets.order;

import entity.car.Car;
import entity.order.Order;
import entity.user.User;
import service.interfaces.order.IOrderServing;
import web.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/orderService")
public class OrderServiceServlet extends HttpServlet {
    private static IOrderServing orderService = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User passenger = ServletUtils.getUserFromRequest(req);
        Car car = (Car) session.getAttribute("car");
        Order order = orderService.servePassenger(passenger, car);
        session.setAttribute("currentOrder", order);

    }

}
