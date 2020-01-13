package web.servlets.order;

import entity.order.Address;
import entity.order.Order;
import entity.user.User;
import service.interfaces.address.IAddressProviding;
import service.interfaces.order.IOrderCreating;
import service.interfaces.user.IPassengerInfoProviding;
import web.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/orderCreation")
public class OrderCreationServlet extends HttpServlet {
    private static IOrderCreating orderService = null;
    private static IPassengerInfoProviding userService = null;
    private static IAddressProviding addressProvider = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Order order = getOrder(req);
        orderService.createOrder(order);
        session.setAttribute("order", order);

    }

    private Order getOrder(HttpServletRequest req) {

        User passenger = getUserForOrder(req);
        Address from = getAddress("from", req);
        Address where = getAddress("where", req);
        String comment = ServletUtils.getParameterFromRequest("comment", req).orElse(null);

        return Order.builder()
                .passenger(passenger)
                .from(from)
                .where(where)
                .comment(comment)
                .build();
    }
    private User getUserForOrder(HttpServletRequest req) {

        User passenger = (User) req.getSession().getAttribute("user");
        if (passenger == null) {
            passenger = ServletUtils.getUserFromRequest(req);
            passenger = userService.findPassengerInfo(passenger)
                    .orElse(userService.addPassengerInfo(passenger));
        }

        return passenger;

    }
    private Address getAddress(String description, HttpServletRequest req) {

        String addressTitle = ServletUtils.getParameterFromRequest(description, req)
                .orElseThrow(() -> new NullPointerException("Parameter with '" + description +"' title is absent"));

        return addressProvider.getAddress(addressTitle)
                .orElseThrow(() -> new NullPointerException("The info about address is absent"));

    }

}
